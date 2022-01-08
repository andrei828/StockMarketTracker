package com.unibuc.finalproject.controllers;

import com.unibuc.finalproject.models.portfolio.Portfolio;
import com.unibuc.finalproject.models.stock.Stock;
import com.unibuc.finalproject.models.user.User;
import com.unibuc.finalproject.repositories.UserRepository;
import com.unibuc.finalproject.services.portfolio.PortfolioService;
import com.unibuc.finalproject.services.stock.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StockService stockService;

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        Portfolio portfolio = new Portfolio();
        portfolio.setUser(user);
        user.setPortfolio(portfolio);
        portfolioService.save(portfolio);
        userRepository.save(user);

        return "register_success";
    }

    @GetMapping("/add/{symbol}")
    public String addStockToPortfolio(@PathVariable("symbol") String symbol, Principal principal) {
        Stock stock = stockService.findByStockSymbol(symbol);
        User currentUser = userRepository.findByEmail(principal.getName());
        Portfolio portfolio = currentUser.getPortfolio();
        Set<Stock> currentStocks = portfolio.getStocks();
        if (currentStocks == null) {
            currentStocks = new HashSet<>();
        }
        currentStocks.add(stock);
        portfolioService.save(portfolio);
        userRepository.save(currentUser);
        return "redirect:/users";
    }

    @GetMapping("/remove/{symbol}")
    public String showUpdateForm(@PathVariable("symbol") String symbol, Principal principal) {
        Stock stock = stockService.findByStockSymbol(symbol);
        User currentUser = userRepository.findByEmail(principal.getName());
        Portfolio portfolio = currentUser.getPortfolio();
        Set<Stock> currentStocks = portfolio.getStocks();
        if (currentStocks == null) {
            return "redirect:/users";
        }

        currentStocks.remove(stock);
        portfolioService.save(portfolio);
        userRepository.save(currentUser);
        return "redirect:/users";
    }


    @GetMapping("/users")
    public String listUsers(Model model, Principal principal) {
        List<User> listUsers = userRepository.findAll();
        List<Stock> listStocks = stockService.findAll();
        User currentUser = userRepository.findByEmail(principal.getName());
        Portfolio portfolio = portfolioService.findByUser(currentUser);
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("listStocks", listStocks);
        model.addAttribute("portfolio", portfolio);
        return "users";
    }

    @GetMapping("/stock/{symbol}")
    public String getStockInfo(@PathVariable("symbol") String symbol, Model model, Principal principal) {
        Stock stock = stockService.findByStockSymbol(symbol);
        User currentUser = userRepository.findByEmail(principal.getName());
        Portfolio portfolio = portfolioService.findByUser(currentUser);
        model.addAttribute("stock", stock);
        model.addAttribute("portfolio", portfolio);
        return "stock";
    }
}

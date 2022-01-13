package com.unibuc.finalproject.controllers;

import com.unibuc.finalproject.models.portfolio.Portfolio;
import com.unibuc.finalproject.models.stock.Stock;
import com.unibuc.finalproject.models.user.User;
import com.unibuc.finalproject.models.wishlist.Wishlist;
import com.unibuc.finalproject.repositories.UserRepository;
import com.unibuc.finalproject.services.portfolio.PortfolioService;
import com.unibuc.finalproject.services.stock.StockService;
import com.unibuc.finalproject.services.user.UserService;
import com.unibuc.finalproject.services.wishlist.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@Validated
public class UserController {

    private final UserService userService;

    private final StockService stockService;

    private final PortfolioService portfolioService;

    private final WishlistService wishlistService;

    @Autowired
    public UserController(UserService userService, StockService stockService, PortfolioService portfolioService, WishlistService wishlistService) {
        this.userService = userService;
        this.stockService = stockService;
        this.portfolioService = portfolioService;
        this.wishlistService = wishlistService;
    }

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

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        user.setWishlist(wishlist);

        //wishlistService.save(wishlist);
        //portfolioService.save(portfolio);
        userService.save(user);

        return "register_success";
    }

    @GetMapping("/add/{symbol}")
    public String addStockToPortfolio(@PathVariable("symbol") String symbol, Principal principal) {
        Stock stock = stockService.findByStockSymbol(symbol);
        User currentUser = userService.findByEmail(principal.getName());
        Portfolio portfolio = currentUser.getPortfolio();
        Set<Stock> currentStocks = portfolio.getStocks();
        if (currentStocks == null) {
            currentStocks = new HashSet<>();
        }
        currentStocks.add(stock);
        portfolioService.save(portfolio);
        userService.save(currentUser);
        return "redirect:/users";
    }

    @GetMapping("/remove/{symbol}")
    public String removeSymbolFromPortfolio(@PathVariable("symbol") String symbol, Principal principal) {
        Stock stock = stockService.findByStockSymbol(symbol);
        User currentUser = userService.findByEmail(principal.getName());
        Portfolio portfolio = currentUser.getPortfolio();
        Set<Stock> currentStocks = portfolio.getStocks();
        if (currentStocks == null) {
            return "redirect:/users";
        }

        currentStocks.remove(stock);
        portfolioService.save(portfolio);
        userService.save(currentUser);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String listUsers(Model model, Principal principal) {
        List<User> listUsers = userService.findAll();
        List<Stock> listStocks = stockService.findAll();
        User currentUser = userService.findByEmail(principal.getName());
        Portfolio portfolio = portfolioService.findByUser(currentUser);
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("listStocks", listStocks);
        model.addAttribute("portfolio", portfolio);
        return "users";
    }

    @GetMapping("/stock/{symbol}")
    public String getStockInfo(@PathVariable("symbol") String symbol, Model model, Principal principal) {
        Stock stock = stockService.findByStockSymbol(symbol);
        User currentUser = userService.findByEmail(principal.getName());
        Portfolio portfolio = portfolioService.findByUser(currentUser);
        model.addAttribute("stock", stock);
        model.addAttribute("portfolio", portfolio);
        return "stock";
    }
}

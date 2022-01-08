package com.unibuc.finalproject.controllers;

import com.unibuc.finalproject.models.analyzer.Analyzer;
import com.unibuc.finalproject.models.portfolio.Portfolio;
import com.unibuc.finalproject.models.stock.Stock;
import com.unibuc.finalproject.models.user.User;
import com.unibuc.finalproject.repositories.UserRepository;
import com.unibuc.finalproject.services.analyzer.AnalyzerService;
import com.unibuc.finalproject.services.portfolio.PortfolioService;
import com.unibuc.finalproject.services.stock.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AnalyzerController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StockService stockService;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private AnalyzerService analyzerService;

    @GetMapping("/analyzer_list")
    public String listUsers(Model model, Principal principal) {
        User currentUser = userRepository.findByEmail(principal.getName());
        List<Analyzer> analyzers = analyzerService.findByUser(currentUser);
        model.addAttribute("listAnalyzers", analyzers);
        return "analyzer_list";
    }

    @GetMapping("/add_analyzer")
    public String newAnalyzer(Principal principal) {
        User currentUser = userRepository.findByEmail(principal.getName());
        Set<Analyzer> analyzers = currentUser.getAnalyzers();
        Analyzer analyzer = new Analyzer();
        analyzer.setUser(currentUser);
        if (analyzers == null) {
            analyzers = new HashSet<>();
        }
        analyzers.add(analyzer);
        analyzerService.save(analyzer);
        return "redirect:/analyzer_list";
    }

    @GetMapping("/delete_analyzer/{id}")
    public String deleteAnalyzer(@PathVariable("id") String id, Principal principal) {
        Analyzer analyzer = analyzerService.findById(Long.parseLong(id));
        User currentUser = userRepository.findByEmail(principal.getName());
        Set<Analyzer> analyzers = currentUser.getAnalyzers();
        if (analyzers.contains(analyzer)) {
            analyzers.remove(analyzer);
        }
        
        userRepository.save(currentUser);
        analyzerService.removeAnalyzerById(Long.parseLong(id));
        return "redirect:/analyzer_list";
    }

    @GetMapping("/analyzer/{id}")
    public String getStockInfo(@PathVariable("id") String id, Model model, Principal principal) {
        List<Stock> stocks = stockService.findAll().stream().limit(100).toList();
        Analyzer analyzer = analyzerService.findById(Long.parseLong(id));
        model.addAttribute("analyzer", analyzer);
        model.addAttribute("stocks", stocks);
        return "analyzer";
    }

    @GetMapping("/analyzer/{id}/add_stock/{symbol}")
    public String addStockToAnalyzer(@PathVariable("id") String id, @PathVariable("symbol") String symbol, Principal principal) {
        Analyzer analyzer = analyzerService.findById(Long.parseLong(id));
        Stock stock = stockService.findByStockSymbol(symbol);
        Set<Stock> analyzerStocks = analyzer.getStocks();
        if (analyzerStocks == null) {
            analyzerStocks = new HashSet<>();
        }
        analyzerStocks.add(stock);
        analyzerService.save(analyzer);
        return "redirect:/analyzer/" + id;
    }

    @GetMapping("/analyzer/{id}/remove_stock/{symbol}")
    public String removeStockToAnalyzer(@PathVariable("id") String id, @PathVariable("symbol") String symbol, Principal principal) {
        Analyzer analyzer = analyzerService.findById(Long.parseLong(id));
        Stock stock = stockService.findByStockSymbol(symbol);
        Set<Stock> analyzerStocks = analyzer.getStocks();
        analyzerStocks.remove(stock);
        analyzerService.save(analyzer);
        return "redirect:/analyzer/" + id;
    }
}

package com.unibuc.finalproject.controllers;

import com.unibuc.finalproject.models.analyzer.Analyzer;
import com.unibuc.finalproject.models.analyzer.AnalyzerListRequest;
import com.unibuc.finalproject.models.analyzer.AnalyzerResponse;
import com.unibuc.finalproject.models.analyzer.AnalyzerResponseWithDetails;
import com.unibuc.finalproject.models.stock.Stock;
import com.unibuc.finalproject.models.user.User;
import com.unibuc.finalproject.repositories.UserRepository;
import com.unibuc.finalproject.services.analyzer.AnalyzerService;
import com.unibuc.finalproject.services.portfolio.PortfolioService;
import com.unibuc.finalproject.services.stock.StockService;
import com.unibuc.finalproject.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping(value = "/api/analyzers")
public class RestAnalyzerController {

    private final UserService userService;

    private final StockService stockService;

    private final PortfolioService portfolioService;

    private final AnalyzerService analyzerService;

    @Autowired
    public RestAnalyzerController(UserService userService, StockService stockService, PortfolioService portfolioService, AnalyzerService analyzerService) {
        this.userService = userService;
        this.stockService = stockService;
        this.portfolioService = portfolioService;
        this.analyzerService = analyzerService;
    }

    @GetMapping("/analyzer_list")
    public List<AnalyzerResponseWithDetails> listAnalyzers(@RequestBody AnalyzerListRequest listAnalyzerRequest) {
        User currentUser = userService.findByEmail(listAnalyzerRequest.email());
        List<Analyzer> analyzers = analyzerService.findByUser(currentUser);
        List<AnalyzerResponseWithDetails> response = analyzers.stream().map(analyzer ->
                new AnalyzerResponseWithDetails(
                        analyzer.getId(),
                        analyzer.getUser().getEmail(),
                        analyzer.getStocks().stream().map(stock -> stock.getSymbol()).toList())).toList();
        return response;
    }

    @PostMapping("/add_analyzer")
    public AnalyzerResponse newAnalyzer(@RequestBody AnalyzerListRequest listAnalyzerRequest) {
        User currentUser = userService.findByEmail(listAnalyzerRequest.email());
        addNewAnalyzer(currentUser, analyzerService);
        return new AnalyzerResponse(200, "success");
    }

    @PostMapping("/delete_analyzer/{id}")
    public AnalyzerResponse deleteAnalyzer(@PathVariable("id") String id) {
        Analyzer analyzer = analyzerService.findById(Long.parseLong(id));
        analyzerService.delete(analyzer);
        return new AnalyzerResponse(200, "success");
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

    static void addNewAnalyzer(User currentUser, AnalyzerService analyzerService) {
        Set<Analyzer> analyzers = currentUser.getAnalyzers();
        Analyzer analyzer = new Analyzer();
        analyzer.setUser(currentUser);
        if (analyzers == null) {
            analyzers = new HashSet<>();
        }
        analyzers.add(analyzer);
        analyzerService.save(analyzer);
    }
}

package com.unibuc.finalproject;

import com.unibuc.finalproject.controllers.*;
import com.unibuc.finalproject.models.analyzer.Analyzer;
import com.unibuc.finalproject.models.portfolio.Portfolio;
import com.unibuc.finalproject.models.stock.Stock;
import com.unibuc.finalproject.models.stock.StockRestResponse;
import com.unibuc.finalproject.models.stock.StockRestValueDatePairResponse;
import com.unibuc.finalproject.models.stock.StockValueDatePair;
import com.unibuc.finalproject.models.user.User;
import com.unibuc.finalproject.models.wishlist.Wishlist;
import com.unibuc.finalproject.models.wishlist.WishlistRequest;
import com.unibuc.finalproject.models.wishlist.WishlistResponse;
import com.unibuc.finalproject.repositories.WishlistRepository;
import com.unibuc.finalproject.services.analyzer.AnalyzerService;
import com.unibuc.finalproject.services.portfolio.PortfolioService;
import com.unibuc.finalproject.services.stock.StockService;
import com.unibuc.finalproject.services.stock.StockValueDatePairService;
import com.unibuc.finalproject.services.user.UserService;
import com.unibuc.finalproject.services.wishlist.WishlistService;
import com.unibuc.finalproject.services.wishlist.WishlistServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import javax.sound.sampled.Port;
import java.security.Principal;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ControllerTests {

    @Autowired
    private RestStockController restStockController;

    @Autowired
    private RestAnalyzerController restAnalyzerController;

    @Autowired
    private RestWishlistController wishlistController;

    @Mock
    private StockService stockService;

    @Mock
    private StockValueDatePairService stockValueDatePairService;

    @Mock
    private AnalyzerService analyzerService;

    @Mock
    private PortfolioService portfolioService;

    @Mock
    private WishlistService wishlistService;

    @Mock
    private UserService userService;

    @Test
    void getStocksReturnsAllStocks() {
        Stock stock1 = new Stock();
        stock1.setSymbol("AAPL");
        stock1.setName("Apple");

        List<Stock> stocks = new ArrayList<>();
        stocks.add(stock1);

        StockValueDatePair stockValueDatePair1 = new StockValueDatePair();
        stockValueDatePair1.setStock(stock1);
        stockValueDatePair1.setDate(new Date());
        stockValueDatePair1.setValue(0D);
        List<StockValueDatePair> stockValueDatePairList = new ArrayList<>();
        stock1.setClosingPrices(stockValueDatePairList);

        List<StockRestResponse> stockRestResponses = new ArrayList<>();
        stockRestResponses.add(new StockRestResponse(
            stock1.getSymbol(),
            stock1.getName(),
            stockValueDatePairList.stream().map(
                    stockValueDatePair -> new StockRestValueDatePairResponse(
                            stockValueDatePair.getDate(),
                            stockValueDatePair.getValue())
            ).toList()
        ));

        restStockController = new RestStockController(stockService, stockValueDatePairService);
        when(stockService.findAll()).thenAnswer((Answer<List<Stock>>) invocation -> stocks);

        assertThat(restStockController.getStocks()).isEqualTo(stockRestResponses);
    }

    @Test
    void AssertThatStockWasAddedToWishlist() {
        User user = new User();
        user.setEmail("email");

        Wishlist wishlist = new Wishlist();
        wishlist.setId(0L);

        Set<Stock> stocks = new HashSet<>();
        Stock stock = new Stock();
        stock.setSymbol("AAPL");
        stocks.add(stock);

        WishlistRequest wishlistRequest = new WishlistRequest(user.getEmail());
        RestWishlistController wishlistController = new RestWishlistController(userService, wishlistService, stockService);

        when(userService.findByEmail(wishlistRequest.userEmail())).thenAnswer((Answer<User>) invocation -> user);
        when(wishlistService.findByUser(user)).thenAnswer((Answer<Wishlist>) invocation -> wishlist);
        when(stockService.findByStockSymbol(stock.getSymbol())).thenAnswer((Answer<Stock>) invocation -> stock);

        assertThat(wishlistController.addStockToWishlist(stock.getSymbol(), wishlistRequest)).isEqualTo(
            new WishlistResponse(wishlist.getId(), user.getEmail(), stocks.stream().map(Stock::getSymbol).toList())
        );
    }

    @Test
    void checkGetStockInfo() {
        Stock stock = new Stock();
        User user = new User();
        Portfolio portfolio = new Portfolio();
        MockModel mockModel = new MockModel();
        MockPrincipal mockPrincipal = new MockPrincipal();
        UserController userController = new UserController(userService, stockService, portfolioService, wishlistService);

        when(userService.findByEmail(mockPrincipal.getName())).thenAnswer((Answer<User>) invocation -> user);
        when(stockService.findByStockSymbol(stock.getSymbol())).thenAnswer((Answer<Stock>) invocation -> stock);
        when(portfolioService.findByUser(user)).thenAnswer((Answer<Portfolio>) invocation -> portfolio);

        assertThat(userController.getStockInfo(stock.getSymbol(), mockModel, mockPrincipal)).isEqualTo("stock");

    }

    @Test
    void checkListUsers() {
        Stock stock = new Stock();
        User user = new User();
        Portfolio portfolio = new Portfolio();
        MockModel mockModel = new MockModel();
        MockPrincipal mockPrincipal = new MockPrincipal();
        List<Stock> stocks = new ArrayList<>();
        List<User> users = new ArrayList<>();
        stocks.add(stock);
        users.add(user);
        UserController userController = new UserController(userService, stockService, portfolioService, wishlistService);

        when(userService.findByEmail(mockPrincipal.getName())).thenAnswer((Answer<User>) invocation -> user);
        when(stockService.findAll()).thenAnswer((Answer<List<Stock>>) invocation -> stocks);
        when(userService.findAll()).thenAnswer((Answer<List<User>>) invocation -> users);
        when(portfolioService.findByUser(user)).thenAnswer((Answer<Portfolio>) invocation -> portfolio);

        assertThat(userController.listUsers(mockModel, mockPrincipal)).isEqualTo("users");

    }

    @Test
    void checkRemoveSymbolFromPortfolio() {
        Stock stock = new Stock();
        User user = new User();
        Portfolio portfolio = new Portfolio();
        MockPrincipal mockPrincipal = new MockPrincipal();
        user.setPortfolio(portfolio);

        UserController userController = new UserController(userService, stockService, portfolioService, wishlistService);

        when(userService.findByEmail(mockPrincipal.getName())).thenAnswer((Answer<User>) invocation -> user);
        when(stockService.findByStockSymbol(stock.getSymbol())).thenAnswer((Answer<Stock>) invocation -> stock);
        when(portfolioService.findByUser(user)).thenAnswer((Answer<Portfolio>) invocation -> portfolio);

        assertThat(userController.removeSymbolFromPortfolio(stock.getSymbol(), mockPrincipal)).isEqualTo("redirect:/users");
        assertThat(userController.addStockToPortfolio(stock.getSymbol(), mockPrincipal)).isEqualTo("redirect:/users");
    }

    @Test
    void checkListAnalyzers() {
        User user = new User();
        MockModel mockModel = new MockModel();
        List<Analyzer> analyzers = new ArrayList<>();
        MockPrincipal mockPrincipal = new MockPrincipal();
        AnalyzerController analyzerController = new AnalyzerController(userService, stockService, portfolioService, analyzerService);

        when(userService.findByEmail(mockPrincipal.getName())).thenAnswer((Answer<User>) invocation -> user);
        when(analyzerService.findByUser(user)).thenAnswer((Answer<List<Analyzer>>) invocation -> analyzers);

        assertThat(analyzerController.listUsers(mockModel, mockPrincipal)).isEqualTo("analyzer_list");
    }


    @Test
    void checkAddAnalyzer() {
        User user = new User();
        MockPrincipal mockPrincipal = new MockPrincipal();
        AnalyzerController analyzerController = new AnalyzerController(userService, stockService, portfolioService, analyzerService);

        when(userService.findByEmail(mockPrincipal.getName())).thenAnswer((Answer<User>) invocation -> user);

        assertThat(analyzerController.newAnalyzer(mockPrincipal)).isEqualTo("redirect:/analyzer_list");
    }

    @Test
    void checkDeleteAnalyzer() {
        User user = new User();
        Analyzer analyzer = new Analyzer();
        Set<Analyzer> analyzers = new HashSet<>();
        MockPrincipal mockPrincipal = new MockPrincipal();
        analyzer.setId(0L);
        user.setAnalyzers(analyzers);
        AnalyzerController analyzerController = new AnalyzerController(userService, stockService, portfolioService, analyzerService);

        when(userService.findByEmail(mockPrincipal.getName())).thenAnswer((Answer<User>) invocation -> user);
        when(analyzerService.findById(analyzer.getId())).thenAnswer((Answer<Analyzer>) invocation -> analyzer);

        assertThat(analyzerController.deleteAnalyzer(analyzer.getId().toString(), mockPrincipal)).isEqualTo("redirect:/analyzer_list");
    }
}
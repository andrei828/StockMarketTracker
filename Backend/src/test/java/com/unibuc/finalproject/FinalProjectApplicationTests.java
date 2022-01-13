package com.unibuc.finalproject;

import com.unibuc.finalproject.controllers.RestAnalyzerController;
import com.unibuc.finalproject.controllers.RestStockController;
import com.unibuc.finalproject.controllers.RestWishlistController;
import com.unibuc.finalproject.models.analyzer.Analyzer;
import com.unibuc.finalproject.models.portfolio.Portfolio;
import com.unibuc.finalproject.models.stock.Stock;
import com.unibuc.finalproject.models.stock.StockValueDatePair;
import com.unibuc.finalproject.models.user.CustomUserDetails;
import com.unibuc.finalproject.models.user.User;
import com.unibuc.finalproject.models.wishlist.Wishlist;
import com.unibuc.finalproject.repositories.*;
import com.unibuc.finalproject.services.analyzer.AnalyzerService;
import com.unibuc.finalproject.services.analyzer.AnalyzerServiceImpl;
import com.unibuc.finalproject.services.portfolio.PortfolioService;
import com.unibuc.finalproject.services.portfolio.PortfolioServiceImpl;
import com.unibuc.finalproject.services.stock.StockService;
import com.unibuc.finalproject.services.stock.StockServiceImpl;
import com.unibuc.finalproject.services.stock.StockValueDatePairService;
import com.unibuc.finalproject.services.stock.StockValueDatePairServiceImpl;
import com.unibuc.finalproject.services.user.CustomUserDetailsService;
import com.unibuc.finalproject.services.user.UserService;
import com.unibuc.finalproject.services.user.UserServiceImpl;
import com.unibuc.finalproject.services.wishlist.WishlistService;
import com.unibuc.finalproject.services.wishlist.WishlistServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class FinalProjectApplicationTests {

    @Autowired
    private RestStockController restStockController;

    @Autowired
    private RestAnalyzerController restAnalyzerController;

    @Autowired
    private RestWishlistController wishlistController;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private StockValueDatePairRepository stockValueDatePairRepository;

    @Mock
    private AnalyzerRepository analyzerRepository;

    @Mock
    private PortfolioRepository portfolioRepository;

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private UserRepository userRepository;

    @Autowired
    private StockService stockService;

    @Autowired
    private StockValueDatePairService stockValueDatePairService;

    @Autowired
    private AnalyzerService analyzerService;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private  UserService userService;

    @Test
    void contextLoads() {
        assertThat(restStockController).isNotNull();
        assertThat(restAnalyzerController).isNotNull();
        assertThat(wishlistController).isNotNull();
    }

    @Test
    void checkStockService() {
        String dummySymbol = "dummySymbol";
        Stock dummyStock = new Stock();
        stockService = new StockServiceImpl(stockRepository);
        stockService.findByStockSymbol(dummySymbol);
        stockService.findAll();
        stockService.saveStock(dummyStock);

        verify(stockRepository).findStockBySymbol(dummySymbol);
        verify(stockRepository).findAll();
        verify(stockRepository).save(dummyStock);
    }

    @Test
    void checkStockValueDatePairService() {
        stockValueDatePairService = new StockValueDatePairServiceImpl(stockValueDatePairRepository);
        StockValueDatePair dummyStockValueDatePair = new StockValueDatePair();
        List<StockValueDatePair> stockValueDatePairList = new ArrayList<>();
        stockValueDatePairList.add(dummyStockValueDatePair);

        stockValueDatePairService.saveStockValueDatePair(dummyStockValueDatePair);
        stockValueDatePairService.findByStockValueDatePairId(0L);
        stockValueDatePairService.saveAll(stockValueDatePairList);
        stockValueDatePairService.findBySymbol("dummySymbol");

        verify(stockValueDatePairRepository).save(dummyStockValueDatePair);
        verify(stockValueDatePairRepository).findStockValueDatePairById(0L);
        verify(stockValueDatePairRepository).saveAll(stockValueDatePairList);
        verify(stockValueDatePairRepository).findStockValueDatePairBySymbol("dummySymbol");
    }

    @Test
    void checkAnalyzerService() {
        Analyzer dummyAnalyzer = new Analyzer();
        User dummyUser = new User();
        analyzerService = new AnalyzerServiceImpl(analyzerRepository);
        analyzerService.save(dummyAnalyzer);
        analyzerService.removeAnalyzerById(0L);
        analyzerService.findById(0L);
        analyzerService.findByUser(dummyUser);

        verify(analyzerRepository).save(dummyAnalyzer);
        verify(analyzerRepository).findAnalyzerById(0L);
        verify(analyzerRepository).deleteById(0L);
        verify(analyzerRepository).findAnalyzersByUser(dummyUser);
    }

    @Test
    void checkPortfolioService() {
        User user = new User();
        Portfolio portfolio = new Portfolio();
        PortfolioService portfolioService = new PortfolioServiceImpl(portfolioRepository);
        portfolioService.save(portfolio);
        portfolioService.findByUser(user);

        verify(portfolioRepository).save(portfolio);
        verify(portfolioRepository).findPortfolioByUser(user);
    }

    @Test
    void checkWishlistService() {
        User user = new User();
        Wishlist wishlist = new Wishlist();
        wishlistService = new WishlistServiceImpl(wishlistRepository);
        wishlistService.save(wishlist);
        wishlistService.findByUser(user);

        verify(wishlistRepository).save(wishlist);
        verify(wishlistRepository).findWishlistByUser(user);
    }

    @Test
    void checkUserService() {
        User user = new User();
        user.setEmail("email");
        CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService(userRepository);
        userService = new UserServiceImpl(userRepository);
        userService.findById(0L);
        userService.findByEmail("dummyEmail");

        verify(userRepository).findById(0L);
        verify(userRepository).findByEmail("dummyEmail");

        when(userRepository.findByEmail(user.getEmail())).thenAnswer((Answer<User>) invocation -> user);
        CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(user.getEmail());
        assertThat(customUserDetails.getUsername()).isEqualTo(user.getEmail());
    }


}

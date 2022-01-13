package com.unibuc.finalproject.controllers;

import com.unibuc.finalproject.models.stock.Stock;
import com.unibuc.finalproject.models.user.User;
import com.unibuc.finalproject.models.wishlist.Wishlist;
import com.unibuc.finalproject.models.wishlist.WishlistRequest;
import com.unibuc.finalproject.models.wishlist.WishlistResponse;
import com.unibuc.finalproject.services.stock.StockService;
import com.unibuc.finalproject.services.user.UserService;
import com.unibuc.finalproject.services.wishlist.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@Validated
@RequestMapping(value = "/api/wishlists")
public class RestWishlistController {

    private final UserService userService;

    private final WishlistService wishlistService;

    private final StockService stockService;

    @Autowired
    public RestWishlistController(UserService userService, WishlistService wishlistService, StockService stockService) {
        this.userService = userService;
        this.wishlistService = wishlistService;
        this.stockService = stockService;
    }

    @RequestMapping(value = "/add_stock_to_wishlist/{symbol}", method = RequestMethod.POST)
    @ResponseBody
    public WishlistResponse addStockToWishlist(@PathVariable String symbol, @RequestBody WishlistRequest wishlistRequest) {
        User currentUser = userService.findByEmail(wishlistRequest.userEmail());
        Wishlist wishlist = wishlistService.findByUser(currentUser);
        Stock stock = stockService.findByStockSymbol(symbol);
        Set<Stock> stocks = wishlist.getStocks();
        if (stocks == null) {
            stocks = new HashSet<>();
        }
        stocks.add(stock);

        List<String> stocksResponse = stocks.stream().map(Stock::getSymbol).toList();
        return new WishlistResponse(wishlist.getId(), currentUser.getEmail(), stocksResponse);
    }

    @RequestMapping(value = "/get_stocks_from_wishlist", method = RequestMethod.GET)
    @ResponseBody
    public WishlistResponse getStocksFromWishlist(@RequestBody WishlistRequest wishlistRequest) {
        User currentUser = userService.findByEmail(wishlistRequest.userEmail());
        Wishlist wishlist = wishlistService.findByUser(currentUser);
        Set<Stock> stocks = wishlist.getStocks();
        List<String> stocksResponse = new ArrayList<>();
        if (stocks != null && stocks.size() > 0) {
            stocksResponse = stocks.stream().map(Stock::getSymbol).toList();
        }
        return new WishlistResponse(wishlist.getId(), currentUser.getEmail(), stocksResponse);
    }
}

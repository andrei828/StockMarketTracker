package com.unibuc.finalproject.controllers;

import com.unibuc.finalproject.models.stock.*;
import com.unibuc.finalproject.services.stock.StockService;
import com.unibuc.finalproject.services.stock.StockValueDatePairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@Validated
@RequestMapping(value = "/api/stocks")
public class RestStockController {

    private StockService stockService;

    private final StockValueDatePairService stockValueDatePairService;

    @Autowired
    public RestStockController(StockService stockService, StockValueDatePairService stockValueDatePairService) {
        this.stockService = stockService;
        this.stockValueDatePairService = stockValueDatePairService;
    }

    @RequestMapping(value = "/insert_dummies", method = RequestMethod.POST)
    @ResponseBody
    public Stock insertDummyStocks() {
        Stock stockResponse = stockService.saveDummyStocks();
        return stockResponse;
    }

    @RequestMapping(value = "/add_stock", method = RequestMethod.POST)
    @ResponseBody
    public Stock saveBook(@RequestBody Stock stock) {
        return stockService.saveStock(stock);
    }

    @RequestMapping(value = "/add_stock_closing_prices", method = RequestMethod.POST)
    @ResponseBody
    public StockValueDatePair addStockClosingPrices(@RequestBody List<StockRestValueDatePairRequest> stockClosingPrices) {

        // TODO: validate the input before saving
        List<StockValueDatePair> bulkPairs = new ArrayList<>();
        for (var valueDatePair : stockClosingPrices) {
            Stock correspondingStock = stockService.findByStockSymbol(valueDatePair.symbol());

            if (correspondingStock != null) {
                StockValueDatePair stockValueDatePair = new StockValueDatePair();
                stockValueDatePair.setStock(correspondingStock);
                stockValueDatePair.setDate(valueDatePair.date());
                stockValueDatePair.setValue(valueDatePair.value());
                bulkPairs.add(stockValueDatePair);
            } else {
                throw new NotFoundException("Stock symbol not found");
            }
        }

        stockValueDatePairService.saveAll(bulkPairs);
        return bulkPairs.get(0);
    }

    @RequestMapping(value = "/get_stocks", method = RequestMethod.GET)
    @ResponseBody
    public List<StockRestResponse> getStocks() {
        List<Stock> stocks = stockService.findAll();
        return stocks.stream().map(stock ->
            new StockRestResponse(
                stock.getSymbol(),
                stock.getName(),
                stock.getClosingPrices().stream().map(closingPrice ->
                    new StockRestValueDatePairResponse(closingPrice.getDate(), closingPrice.getValue())
                ).toList())
            ).toList();
    }
}

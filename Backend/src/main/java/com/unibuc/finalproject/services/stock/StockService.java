package com.unibuc.finalproject.services.stock;

import com.unibuc.finalproject.models.stock.Stock;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StockService {

    List<Stock> findAll();
    Stock saveDummyStocks();
    Stock saveStock(Stock stock);
    Stock findByStockSymbol(String symbol);

}

package com.unibuc.finalproject.services.stock;

import com.unibuc.finalproject.models.stock.Stock;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StockService {

    public List<Stock> findAll();
    public Stock saveDummyStocks();
    public Stock saveStock(Stock stock);
    public Stock findByStockSymbol(String symbol);

}

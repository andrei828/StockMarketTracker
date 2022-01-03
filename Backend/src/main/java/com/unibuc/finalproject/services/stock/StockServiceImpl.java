package com.unibuc.finalproject.services.stock;

import com.unibuc.finalproject.models.stock.Stock;
import com.unibuc.finalproject.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    StockRepository stockRepository;

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Override
    public Stock saveDummyStocks() {
        Stock stock1 = new Stock();
        stock1.setName("Apple");
        stock1.setSymbol("AAPL");

        Stock stock2 = new Stock();
        stock2.setName("Adidas");
        stock2.setSymbol("ADDYY");

        Stock stock3 = new Stock();
        stock3.setName("Delta Air Lines");
        stock3.setSymbol("DAL");

        Stock stock4 = new Stock();
        stock4.setName("American Tower Corporation");
        stock4.setSymbol("AMT");

        Stock stock5 = new Stock();
        stock5.setName("Abbott Laboratories");
        stock5.setSymbol("ABT");
        stockRepository.saveAll(new ArrayList<Stock>(){ { add(stock1); add(stock2); add(stock3); add(stock4); add(stock5); } });
        return stock1;
    }

    @Override
    public Stock saveStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock findByStockSymbol(String symbol) {
        return stockRepository.findStockBySymbol(symbol);
    }


}

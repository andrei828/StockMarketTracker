package com.unibuc.finalproject.services.stock;

import com.unibuc.finalproject.models.stock.StockValueDatePair;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface StockValueDatePairService {

    public StockValueDatePair saveStockValueDatePair(StockValueDatePair stockValueDatePair);
    public StockValueDatePair findByStockValueDatePairId(int id);
    public void saveAll(List<StockValueDatePair> stockValueDatePairList);
    public List<StockValueDatePair> findBySymbol(String symbol);
}
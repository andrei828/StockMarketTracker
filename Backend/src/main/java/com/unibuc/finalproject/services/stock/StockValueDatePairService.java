package com.unibuc.finalproject.services.stock;

import com.unibuc.finalproject.models.stock.StockValueDatePair;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StockValueDatePairService {

    StockValueDatePair saveStockValueDatePair(StockValueDatePair stockValueDatePair);
    StockValueDatePair findByStockValueDatePairId(Long id);
    void saveAll(List<StockValueDatePair> stockValueDatePairList);
    List<StockValueDatePair> findBySymbol(String symbol);
}
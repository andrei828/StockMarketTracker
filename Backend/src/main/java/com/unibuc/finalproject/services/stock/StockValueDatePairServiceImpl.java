package com.unibuc.finalproject.services.stock;

import com.unibuc.finalproject.models.stock.StockValueDatePair;
import com.unibuc.finalproject.repositories.StockValueDatePairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockValueDatePairServiceImpl implements StockValueDatePairService {

    private final StockValueDatePairRepository stockValueDatePairRepository;

    @Autowired
    public StockValueDatePairServiceImpl(StockValueDatePairRepository stockValueDatePairRepository) {
        this.stockValueDatePairRepository = stockValueDatePairRepository;
    }

    @Override
    public StockValueDatePair saveStockValueDatePair(StockValueDatePair stockValueDatePair) {
        return stockValueDatePairRepository.save(stockValueDatePair);
    }

    @Override
    public StockValueDatePair findByStockValueDatePairId(Long id) {
        return stockValueDatePairRepository.findStockValueDatePairById(id);
    }

    @Override
    public void saveAll(List<StockValueDatePair> stockValueDatePairList) {
        stockValueDatePairRepository.saveAll(stockValueDatePairList);
    }

    @Override
    public List<StockValueDatePair> findBySymbol(String symbol) {
        return stockValueDatePairRepository.findStockValueDatePairBySymbol(symbol);
    }
}

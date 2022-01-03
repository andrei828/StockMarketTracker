package com.unibuc.finalproject.repositories;

import com.unibuc.finalproject.models.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Object> {

    public Stock findStockBySymbol(String symbol);

    public List<Stock> findAll();
}

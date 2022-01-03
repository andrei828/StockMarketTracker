package com.unibuc.finalproject.repositories;

import com.unibuc.finalproject.models.stock.StockValueDatePair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockValueDatePairRepository extends JpaRepository<StockValueDatePair, Object> {

    public StockValueDatePair findStockValueDatePairById(Long id);

    @Query("SELECT s FROM StockValueDatePair s WHERE s.stock = ?1")
    public List<StockValueDatePair> findStockValueDatePairBySymbol(String symbol);
}

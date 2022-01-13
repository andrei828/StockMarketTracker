package com.unibuc.finalproject.repositories;

import com.unibuc.finalproject.models.stock.Stock;
import com.unibuc.finalproject.services.stock.StockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(true)
public class StockRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StockRepository stockRepository;

    @Test
    public void tesCreateStock() {
        Stock stock = new Stock();
        stock.setName("Apple");
        stock.setSymbol("AAPL");

        Stock savedStock = stockRepository.save(stock);
        Stock existStock = entityManager.find(Stock.class, savedStock.getSymbol());
        assertThat(stock.getSymbol()).isEqualTo(existStock.getSymbol());
    }
}

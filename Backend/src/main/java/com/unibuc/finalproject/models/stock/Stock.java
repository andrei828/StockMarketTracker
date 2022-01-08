package com.unibuc.finalproject.models.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "stocks")
public class Stock  implements Serializable {

    @Id
    @Column(nullable = false, unique = true, length = 10)
    private String symbol;

    @Column(nullable = false, length = 256)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "stock")
    @JsonIgnoreProperties("stock")
    List<StockValueDatePair> closingPrices;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return symbol.equals(stock.symbol) && name.equals(stock.name) && closingPrices.equals(stock.closingPrices);
    }

    public Double getCurrentPrice() {
        if (closingPrices == null || closingPrices.size() < 1) {
            return null;
        }

        Double result = null;
        Date lastDate = null;
        Iterator<StockValueDatePair> iterator = closingPrices.iterator();
        while (iterator.hasNext()) {
            StockValueDatePair stockValueDatePair = iterator.next();
            Double currentPrice = stockValueDatePair.getValue();
            Date currentDate = stockValueDatePair.getDate();

            if (lastDate == null || currentDate.compareTo(lastDate) > 0) {
                result = currentPrice;
                lastDate = currentDate;
            }
        }

        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, name, closingPrices);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", closingPrices=" + closingPrices +
                '}';
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StockValueDatePair> getClosingPrices() {
        return closingPrices;
    }

    public void setClosingPrices(List<StockValueDatePair> closingPrices) {
        this.closingPrices = closingPrices;
    }
}

package com.unibuc.finalproject.models.stock;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "stock_value_date_pair")
public class StockValueDatePair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "symbol")
    @JsonIgnoreProperties("closingPrices")
    private Stock stock;

    @Column(nullable = false)
    private Double value;

    @JsonFormat(pattern = "yyyy/MM/dd")
    @Column(nullable = false)
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "StockValueDatePair{" +
                "id=" + id +
                ", stock=" + stock +
                ", value=" + value +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockValueDatePair that = (StockValueDatePair) o;
        return id.equals(that.id) && stock.equals(that.stock) && value.equals(that.value) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stock, value, date);
    }
}

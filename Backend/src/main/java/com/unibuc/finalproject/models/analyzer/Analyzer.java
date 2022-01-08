package com.unibuc.finalproject.models.analyzer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unibuc.finalproject.models.stock.Stock;
import com.unibuc.finalproject.models.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "analyzer")
public class Analyzer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("analyzers")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    private Set<Stock> stocks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Analyzer analyzer = (Analyzer) o;
        return Objects.equals(id, analyzer.id) && Objects.equals(user, analyzer.user) && Objects.equals(stocks, analyzer.stocks);
    }

    @Override
    public String toString() {
        return "Analyzer{" +
                "id=" + id +
                ", user=" + user.getEmail() +
                '}';
    }
}


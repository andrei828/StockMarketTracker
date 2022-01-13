package com.unibuc.finalproject.models.wishlist;

import com.unibuc.finalproject.models.stock.Stock;
import com.unibuc.finalproject.models.user.User;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "wishlist")
public class Wishlist implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
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
        Wishlist wishlist = (Wishlist) o;
        return Objects.equals(id, wishlist.id) && Objects.equals(user, wishlist.user) && Objects.equals(stocks, wishlist.stocks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, stocks);
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "id=" + id +
                '}';
    }
}


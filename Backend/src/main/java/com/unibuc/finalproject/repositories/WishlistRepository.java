package com.unibuc.finalproject.repositories;

import com.unibuc.finalproject.models.user.User;
import com.unibuc.finalproject.models.wishlist.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WishlistRepository extends JpaRepository<Wishlist, Object> {

    public Wishlist findWishlistByUser(User user);
}
package com.unibuc.finalproject.services.wishlist;


import com.unibuc.finalproject.models.user.User;
import com.unibuc.finalproject.models.wishlist.Wishlist;
import org.springframework.stereotype.Component;

@Component
public interface WishlistService {
    Wishlist findByUser(User user);
    Wishlist save(Wishlist wishlist);
}

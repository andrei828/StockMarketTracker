package com.unibuc.finalproject.models.wishlist;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record WishlistRequest(@NotNull @Size(min = 3, max = 50) String userEmail) {
}

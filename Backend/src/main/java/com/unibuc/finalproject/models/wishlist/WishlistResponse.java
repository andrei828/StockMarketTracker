package com.unibuc.finalproject.models.wishlist;

import javax.validation.constraints.NotNull;
import java.util.List;

public record WishlistResponse(@NotNull Long id, @NotNull String userEmail, List<String> stocks) {
}

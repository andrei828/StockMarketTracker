package com.unibuc.finalproject.models.stock;

import java.util.List;

public record StockRestResponse(String symbol, String name, List<StockRestValueDatePairResponse> closingPriceValues) {
}

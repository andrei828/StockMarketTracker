package com.unibuc.finalproject.models.stock;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record StockRestValueDatePairRequest(String symbol, Double value, @JsonFormat(pattern = "yyyy/MM/dd") Date date) {
}

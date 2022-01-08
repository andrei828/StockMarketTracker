package com.unibuc.finalproject.models.stock;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class StockRestValueDatePairResponse implements Serializable {

    public StockRestValueDatePairResponse(@JsonFormat(pattern = "yyyy/MM/dd") Date date, Double value) {
        this.date = date;
        this.value = value;
    }

    @JsonFormat(pattern = "yyyy/MM/dd")
    public Date date;

    public Double value;
}

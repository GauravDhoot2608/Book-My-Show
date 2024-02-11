package com.backendproject.BookMyShow.Requests;

import lombok.Data;

@Data
public class AddShowSeatRequest {

    private int priceOfClassicSeats;
    private int priceOfPremiumSeats;
    private Integer showId;
}

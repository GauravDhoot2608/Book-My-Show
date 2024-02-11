package com.backendproject.BookMyShow.Requests;

import lombok.Data;

@Data
public class AddTheaterRequest {

    private String theaterName;
    private String address;
    private Integer noOfScreens;
}

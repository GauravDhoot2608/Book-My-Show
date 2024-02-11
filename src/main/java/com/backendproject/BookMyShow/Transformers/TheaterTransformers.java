package com.backendproject.BookMyShow.Transformers;

import com.backendproject.BookMyShow.Entities.Theater;
import com.backendproject.BookMyShow.Requests.AddTheaterRequest;

public class TheaterTransformers {

    public static Theater convertRequestToEntity(AddTheaterRequest addTheaterRequest){

        Theater theater = Theater.builder()
                .theaterName(addTheaterRequest.getTheaterName())
                .address(addTheaterRequest.getAddress())
                .noOfScreens(addTheaterRequest.getNoOfScreens())
                .build();
        return theater;
    }
}

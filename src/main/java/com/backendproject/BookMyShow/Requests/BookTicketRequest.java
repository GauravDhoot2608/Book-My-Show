package com.backendproject.BookMyShow.Requests;


import com.backendproject.BookMyShow.Enums.SeatType;
import lombok.Data;

import java.util.List;

@Data
public class BookTicketRequest {

    private int showId;
    private List<String> seatList;
    private SeatType seatType;
    private String emailId; //You can take the userId
}

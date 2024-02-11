package com.backendproject.BookMyShow.Requests;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ShowOnDateRequest {

    private LocalDate showDate;
    private Integer movieId;
}

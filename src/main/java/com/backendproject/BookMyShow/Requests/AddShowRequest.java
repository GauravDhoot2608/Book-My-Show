package com.backendproject.BookMyShow.Requests;

import com.backendproject.BookMyShow.Entities.Movie;
import com.backendproject.BookMyShow.Entities.Theater;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AddShowRequest {

    private LocalDate showDate;
    private LocalTime showTime;
    private String movieName;
    private Integer theaterId;
}

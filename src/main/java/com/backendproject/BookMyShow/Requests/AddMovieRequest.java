package com.backendproject.BookMyShow.Requests;

import com.backendproject.BookMyShow.Enums.Genre;
import com.backendproject.BookMyShow.Enums.Language;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AddMovieRequest {

    private String movieName;
    private Genre genre;
    private Language movieLanguage;
    private LocalDate releaseDate;
    private double duration;
    private double rating;
}

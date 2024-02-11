package com.backendproject.BookMyShow.Transformers;

import com.backendproject.BookMyShow.Entities.Movie;
import com.backendproject.BookMyShow.Requests.AddMovieRequest;

public class MovieTransformers {

    public static Movie convertRequestToEntity(AddMovieRequest addMovieRequest){

        Movie movie = Movie.builder()
                .movieName(addMovieRequest.getMovieName())
                .genre(addMovieRequest.getGenre())
                .movieLanguage(addMovieRequest.getMovieLanguage())
                .releaseDate(addMovieRequest.getReleaseDate())
                .duration(addMovieRequest.getDuration())
                .rating(addMovieRequest.getRating())
                .build();
        return movie;
    }
}

package com.backendproject.BookMyShow.Services;

import com.backendproject.BookMyShow.Entities.Movie;
import com.backendproject.BookMyShow.Repositories.MovieRepository;
import com.backendproject.BookMyShow.Requests.AddMovieRequest;
import com.backendproject.BookMyShow.Transformers.MovieTransformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public String addMovie(AddMovieRequest addMovieRequest){

        // old method using constructor
        // new method using @builder annotation
//        Movie movie = Movie.builder()
//                .movieName(addMovieRequest.getMovieName())
//                .genre(addMovieRequest.getGenre())
//                .movieLanguage(addMovieRequest.getMovieLanguage())
//                .releaseDate(addMovieRequest.getReleaseDate())
//                .duration(addMovieRequest.getDuration())
//                .build();

        // use transformers to dto -> entity
        Movie movie = MovieTransformers.convertRequestToEntity(addMovieRequest);

        movie = movieRepository.save(movie);

        return "Movie is added into db with movie id : " + movie.getMovieId();
    }

    public Movie getMovie(Integer movieId){

        Movie movie = movieRepository.findById(movieId).get();
        return movie;
    }
}

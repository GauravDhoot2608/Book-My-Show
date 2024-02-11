package com.backendproject.BookMyShow.Controllers;

import com.backendproject.BookMyShow.Entities.Movie;
import com.backendproject.BookMyShow.Requests.AddMovieRequest;
import com.backendproject.BookMyShow.Services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/addMovie")
    public ResponseEntity addMovie(@RequestBody AddMovieRequest addMovieRequest){

        String result = movieService.addMovie(addMovieRequest);
        return new ResponseEntity(result , HttpStatus.CREATED);
    }

    @GetMapping("/getMovieInfo")
    public ResponseEntity<Movie> getMovie(@RequestParam("movieId")Integer movieId){
        Movie movie = movieService.getMovie(movieId);
        return new ResponseEntity(movie , HttpStatus.FOUND);
    }

}

package com.backendproject.BookMyShow.Services;

import com.backendproject.BookMyShow.Entities.*;
import com.backendproject.BookMyShow.Enums.SeatType;
import com.backendproject.BookMyShow.Exceptions.InvalidMovieException;
import com.backendproject.BookMyShow.Exceptions.InvalidTheaterException;
import com.backendproject.BookMyShow.Repositories.MovieRepository;
import com.backendproject.BookMyShow.Repositories.ShowRepository;
import com.backendproject.BookMyShow.Repositories.ShowSeatRepository;
import com.backendproject.BookMyShow.Repositories.TheaterRepository;
import com.backendproject.BookMyShow.Requests.AddShowRequest;
import com.backendproject.BookMyShow.Requests.AddShowSeatRequest;
import com.backendproject.BookMyShow.Requests.ShowOnDateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    public String addShow(AddShowRequest addShowRequest) throws Exception{

        // get the f.k. variables
        Movie movie = movieRepository.findMovieByMovieName(addShowRequest.getMovieName());
        if(movie == null){
            throw new InvalidMovieException("Movie Name entered is not exist in DB");
        }
        Optional<Theater> optionalTheater = theaterRepository.findById(addShowRequest.getTheaterId());
        if (optionalTheater.isEmpty()){
            throw new InvalidTheaterException("theater id entered is invalid");
        }

        Theater theater = optionalTheater.get();

        Show show = Show.builder()
                .showDate(addShowRequest.getShowDate())
                .showTime(addShowRequest.getShowTime())
                .movie(movie)
                .theater(theater)
                .build();

        // set the parent class - bidirectional mapping
        movie.getShowList().add(show);
        theater.getShowList().add(show);

        // save the child
        show = showRepository.save(show);

        return "Show has been added into Db with show ID : " + show.getShowId();
    }


    public String addShowSeat(AddShowSeatRequest addShowSeatRequest){

        Show show = showRepository.findById(addShowSeatRequest.getShowId()).get();

        Theater theater = show.getTheater();

        List<TheaterSeat> theaterSeatList =  theater.getTheaterSeatList();

        List<ShowSeat> showSeatList = new ArrayList<>();

        for(TheaterSeat theaterSeat : theaterSeatList){

            String seatNo = theaterSeat.getSeatNo();
            SeatType seatType = theaterSeat.getSeatType();

            ShowSeat showSeat = ShowSeat.builder()
                    .isAvailable(false)
                    .foodAttached(false)
                    .seatNo(seatNo)
                    .seatType(seatType)
                    .show(show)
                    .build();

            if(seatType.equals(SeatType.CLASSIC)){
                showSeat.setPrice(addShowSeatRequest.getPriceOfClassicSeats());
            }else{
                showSeat.setPrice(addShowSeatRequest.getPriceOfPremiumSeats());
            }

            showSeatList.add(showSeat);
        }

        showSeatRepository.saveAll(showSeatList);

        return "Show seat has been added to db";
    }


    public List<Show>  getAllShowsOnDate(ShowOnDateRequest showOnDateRequest){

        LocalDate localDate = showOnDateRequest.getShowDate();
        Integer movieId = showOnDateRequest.getMovieId();

        Movie movie = movieRepository.findById(movieId).get();

        List<Show> result = new ArrayList<>();
        for(Show show : showRepository.findAll()){
            if(show.getShowDate().equals(localDate) && show.getMovie().equals(movie)){
                result.add(show);
            }
        }

        return result;
    }
}

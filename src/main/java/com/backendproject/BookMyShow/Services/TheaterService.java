package com.backendproject.BookMyShow.Services;

import com.backendproject.BookMyShow.Entities.Theater;
import com.backendproject.BookMyShow.Entities.TheaterSeat;
import com.backendproject.BookMyShow.Enums.SeatType;
import com.backendproject.BookMyShow.Repositories.TheaterRepository;
import com.backendproject.BookMyShow.Requests.AddTheaterRequest;
import com.backendproject.BookMyShow.Requests.AddTheaterSeatRequest;
import com.backendproject.BookMyShow.Transformers.TheaterTransformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;

    public String addTheater(AddTheaterRequest addTheaterRequest){

        // use transformers to convert dto to entity
        Theater theater = TheaterTransformers.convertRequestToEntity(addTheaterRequest);

        theater = theaterRepository.save(theater);
        return "Theater is added into db with theater id :" + theater.getTheaterId();
    }

    public String addTheaterSeats(AddTheaterSeatRequest addTheaterSeatRequest){

        int noOClassicSeats = addTheaterSeatRequest.getNoOfClassicSeats();
        int noOfPremiumSeats = addTheaterSeatRequest.getNoOfPremiumSeats();

        Theater theater = theaterRepository.findById(addTheaterSeatRequest.getTheaterId()).get();

        List<TheaterSeat> theaterSeatList = new ArrayList<>();

        int quoClassic = noOClassicSeats/5;
        int remClassic = noOClassicSeats%5;
        for(int row = 1 ; row <= quoClassic ; row++){
            for(int col = 1 ; col <= 5 ; col++){
                char ch = (char)('A' + (col-1));
                String seatNo = row + "" + ch;

                TheaterSeat theaterSeat = TheaterSeat.builder()
                                        .seatNo(seatNo)
                                        .seatType(SeatType.CLASSIC)
                                        .theater(theater)
                                        .build();

                theaterSeatList.add(theaterSeat);
            }
        }
        int rowRemainder = quoClassic + 1;
        for(int col = 1 ; col <= remClassic ; col++){
            char ch = (char)('A' + (col-1));
            String seatNo = rowRemainder + "" + ch;

            TheaterSeat theaterSeat = TheaterSeat.builder()
                    .seatNo(seatNo)
                    .seatType(SeatType.CLASSIC)
                    .theater(theater)
                    .build();

            theaterSeatList.add(theaterSeat);
        }

        // SAME AS FOR PREMIUM SEAT
        int quoPremium = noOfPremiumSeats/5;
        int remPremium = noOfPremiumSeats%5;
        for(int row = rowRemainder+1 ; row <= (rowRemainder + quoPremium) ; row++){
            for(int col = 1 ; col <= 5 ; col++){
                char ch = (char)('A' + (col-1));
                String seatNo = row + "" + ch;

                TheaterSeat theaterSeat = TheaterSeat.builder()
                        .seatNo(seatNo)
                        .seatType(SeatType.PREMIUM)
                        .theater(theater)
                        .build();

                theaterSeatList.add(theaterSeat);
            }
        }
        int newRowRemainder = rowRemainder + quoPremium + 1;
        for(int col = 1 ; col <= remPremium ; col++){
            char ch = (char)('A' + (col-1));
            String seatNo = newRowRemainder + "" + ch;

            TheaterSeat theaterSeat = TheaterSeat.builder()
                    .seatNo(seatNo)
                    .seatType(SeatType.PREMIUM)
                    .theater(theater)
                    .build();

            theaterSeatList.add(theaterSeat);
        }

        // we already set the F.K. variable in the child class

        // Setting the F.K. variable in the parent class
        theater.setTheaterSeatList(theaterSeatList);

        // save the parent
        theaterRepository.save(theater);

        return "Theater seats have been added into DB";
    }
}

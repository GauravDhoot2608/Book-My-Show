package com.backendproject.BookMyShow.Controllers;

import com.backendproject.BookMyShow.Entities.Show;
import com.backendproject.BookMyShow.Requests.AddShowRequest;
import com.backendproject.BookMyShow.Requests.AddShowSeatRequest;
import com.backendproject.BookMyShow.Requests.ShowOnDateRequest;
import com.backendproject.BookMyShow.Services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shows")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping("/addShow")
    public ResponseEntity addShow(@RequestBody AddShowRequest addShowRequest){

        try {
            String res = showService.addShow(addShowRequest);
            return new ResponseEntity(res , HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addShowSeats")
    public ResponseEntity addShowSeat(@RequestBody AddShowSeatRequest addShowSeatRequest){

        String res = showService.addShowSeat(addShowSeatRequest);
        return new ResponseEntity(res , HttpStatus.CREATED);
    }

    @GetMapping("/getAllShowsOnDate")
    public ResponseEntity<List<Show>> showsOnDate(@RequestBody ShowOnDateRequest showOnDateRequest){

        List<Show> shows = showService.getAllShowsOnDate(showOnDateRequest);
        return new ResponseEntity<>(shows , HttpStatus.FOUND);
    }
}

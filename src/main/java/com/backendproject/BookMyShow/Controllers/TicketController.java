package com.backendproject.BookMyShow.Controllers;

import com.backendproject.BookMyShow.Requests.BookTicketRequest;
import com.backendproject.BookMyShow.Responses.ShowTicketResponse;
import com.backendproject.BookMyShow.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/bookTicket")
    public ResponseEntity bookTicket(@RequestBody BookTicketRequest bookTicketRequest){

        try {
            String res = ticketService.bookTicket(bookTicketRequest);
            return new ResponseEntity(res , HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/viewTicket")
    public ResponseEntity viewTicket(@RequestParam("ticketId") Integer ticketId){
        ShowTicketResponse showTicketResponse = ticketService.viewTicket(ticketId);
        return new ResponseEntity(showTicketResponse , HttpStatus.OK);
    }
}

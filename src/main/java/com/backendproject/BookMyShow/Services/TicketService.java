package com.backendproject.BookMyShow.Services;

import com.backendproject.BookMyShow.Entities.Show;
import com.backendproject.BookMyShow.Entities.ShowSeat;
import com.backendproject.BookMyShow.Entities.Ticket;
import com.backendproject.BookMyShow.Entities.User;
import com.backendproject.BookMyShow.Repositories.ShowRepository;
import com.backendproject.BookMyShow.Repositories.TicketRepository;
import com.backendproject.BookMyShow.Repositories.UserRepository;
import com.backendproject.BookMyShow.Requests.BookTicketRequest;
import com.backendproject.BookMyShow.Responses.ShowTicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    public String bookTicket(BookTicketRequest bookTicketRequest) throws Exception{

        Show show = showRepository.findById(bookTicketRequest.getShowId()).get();


        //Check for seat available or not
        List<ShowSeat> showSeatList = show.getShowSeatList();
        int totalBookingAmount = 0;

        for(String seatNoToBeBooked : bookTicketRequest.getSeatList()){

            for(ShowSeat showSeat:showSeatList){

                if(showSeat.getSeatNo().equals(seatNoToBeBooked)&&
                        (bookTicketRequest.getSeatType().equals(showSeat.getSeatType()))){

                    if(showSeat.getIsAvailable()){
                        showSeat.setIsAvailable(Boolean.FALSE);
                        totalBookingAmount = totalBookingAmount+showSeat.getPrice();
                    }else{
                        throw new Exception("Seat No "+showSeat.getSeatNo()+" is already booked.");
                    }
                }
            }
        }

        User user = userRepository.findByEmailId(bookTicketRequest.getEmailId());

        //All the seats were available

        Ticket ticket = Ticket.builder()
                .seatNosBooked(bookTicketRequest.getSeatList().toString())
                .totalAmountPaid(totalBookingAmount)
                .show(show) //FK being set
                .user(user)
                .build();


        show.getTicketList().add(ticket); //Bidirectional mapping
        user.getTicketList().add(ticket); //Bidirectional mapping
        ticket = ticketRepository.save(ticket);

        return "This is the ticket with the ticketId " + ticket.getTicketId();
    }

    public ShowTicketResponse viewTicket(Integer ticketId){

        Ticket ticket = ticketRepository.findById(ticketId).get();

        Show show = ticket.getShow();
        String movieName = show.getMovie().getMovieName();
        String theaterInfo = show.getTheater().getTheaterName()+" "+show.getTheater().getAddress();
        String bookedSeats = ticket.getSeatNosBooked();

        ShowTicketResponse showTicketResponse = ShowTicketResponse.builder()
                .movieName(movieName)
                .theaterInfo(theaterInfo)
                .showDate(show.getShowDate())
                .showTime(show.getShowTime())
                .seatNos(bookedSeats)
                .totalAmt(ticket.getTotalAmountPaid())
                .build();

        // email integration
        String emailId = ticket.getUser().getEmailId();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("sample@gmail.com");
        simpleMailMessage.setTo(emailId);
        simpleMailMessage.setSubject("Movie Ticket Confirmation");
        simpleMailMessage.setText(showTicketResponse.toString());
        javaMailSender.send(simpleMailMessage);

        return showTicketResponse;
    }
}

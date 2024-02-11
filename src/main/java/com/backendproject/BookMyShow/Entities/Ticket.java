package com.backendproject.BookMyShow.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tickets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticketId;
    private String seatNosBooked;
    private Integer totalAmountPaid;

    @JoinColumn
    @ManyToOne
    @JsonIgnore
    private Show show;

    @JoinColumn
    @ManyToOne
    private User user;
}

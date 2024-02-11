package com.backendproject.BookMyShow.Entities;

import com.backendproject.BookMyShow.Enums.SeatType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "show_seats")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer showSeatId;

    private Integer price;
    private Boolean isAvailable;
    private Boolean foodAttached;

    // these values will come from theater seats based on mapping
    private String seatNo;
    private SeatType seatType;

    @JsonIgnore
    @JoinColumn
    @ManyToOne
    private Show show;
}

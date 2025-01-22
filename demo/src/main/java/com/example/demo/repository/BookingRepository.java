package com.example.demo.repository;

import com.example.demo.model.Booking;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Repository
public class BookingRepository {
    private final List<Booking> bookings = new ArrayList<>();

    public void save(Booking booking) {
        bookings.add(booking);
    }

    public List<Booking> findAll() {
        return bookings;
    }

    @PostConstruct
    public void init() {
        save(Booking.builder()
                .memberName("Gukesh D")
                .className("Yoga")
                .participationDate(LocalDate.of(2025, 1, 21))
                .build());

        save(Booking.builder()
                .memberName("Lionel Messi")
                .className("Zumba")
                .participationDate(LocalDate.of(2025, 1, 22))
                .build());

        save(Booking.builder()
                .memberName("Cristiano Ronaldo")
                .className("Pilates")
                .participationDate(LocalDate.of(2025, 1, 23))
                .build());

        save(Booking.builder()
                .memberName("Gareth Bale")
                .className("Yoga")
                .participationDate(LocalDate.of(2025, 1, 24))
                .build());

        save(Booking.builder()
                .memberName("Virat Kohli")
                .className("Zumba")
                .participationDate(LocalDate.of(2025, 1, 25))
                .build());
    }
}

package com.example.konul.bookingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.konul.bookingservice.request.BookingRequest;
import com.example.konul.bookingservice.response.BookingResponse;
import com.example.konul.bookingservice.service.BookingService;

@RestController
@RequestMapping("/api/v1")
public class BookingController {

    private final BookingService bookingService;
    
    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(path = "/booking", consumes = "application/json", produces = "application/json")
    public BookingResponse createBooking(@RequestBody BookingRequest bookingRequest) {
        return bookingService.createBooking(bookingRequest);

    }
}

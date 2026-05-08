package com.example.konul.bookingservice.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.konul.bookingservice.client.InventoryServiceClient;
import com.example.konul.bookingservice.entity.Customer;
import com.example.konul.bookingservice.event.BookingEvent;
import com.example.konul.bookingservice.repository.CustomerRepository;
import com.example.konul.bookingservice.request.BookingRequest;
import com.example.konul.bookingservice.response.BookingResponse;
import com.example.konul.bookingservice.response.InventoryResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookingService {

    private final CustomerRepository customerRepository;
    private final InventoryServiceClient inventoryServiceClient;
    private final KafkaTemplate<String, BookingEvent> kafkaTemplate;

    @Autowired
    public BookingService(final CustomerRepository customerRepository, final InventoryServiceClient inventoryServiceClient,
            final KafkaTemplate<String, BookingEvent> kafkaTemplate
    ) {
        this.customerRepository = customerRepository;
        this.inventoryServiceClient = inventoryServiceClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    public BookingResponse createBooking(final BookingRequest bookingRequest) {
        // check if user exists
        final Customer customer = customerRepository.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        //check if there is enough inventory
        final InventoryResponse inventoryResponse = inventoryServiceClient.getInventory(bookingRequest.getEventId());
        log.info("Inventory Service Response: {}", inventoryResponse);
        if (inventoryResponse.getCapacity() < bookingRequest.getTicketCount()) {
            throw new RuntimeException("Not enough tickets available");
        }        
        // create booking
        final BookingEvent bookingEvent = createBookingEvent(bookingRequest, customer, inventoryResponse);
        // send booking to Order service on a Kafka topic 
        kafkaTemplate.send("booking", bookingEvent);
        log.info("Booking created: {}", bookingEvent);
        return BookingResponse.builder()
                .userId(bookingEvent.getUserId())
                .eventId(bookingEvent.getEventId())
                .ticketCount(bookingEvent.getTicketCount())
                .totalPrice(bookingEvent.getTotalPrice())
                .build();
    }

    private BookingEvent createBookingEvent(BookingRequest bookingRequest, Customer customer,
            InventoryResponse inventoryResponse) {
        return BookingEvent.builder()
                .userId(customer.getId())
                .eventId(bookingRequest.getEventId())
                .ticketCount(bookingRequest.getTicketCount())
                .totalPrice(inventoryResponse.getTicket_price().multiply(BigDecimal.valueOf(bookingRequest.getTicketCount())))
                .build();
    }


}

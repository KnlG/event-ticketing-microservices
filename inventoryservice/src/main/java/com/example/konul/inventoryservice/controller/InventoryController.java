package com.example.konul.inventoryservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.konul.inventoryservice.response.EventInventoryResponse;
import com.example.konul.inventoryservice.response.VenueInventoryResponse;
import com.example.konul.inventoryservice.service.InventoryService;


@RestController
@RequestMapping("api/v1/")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
    
    @GetMapping("inventory/events")
    public @ResponseBody List<EventInventoryResponse> getAllEvents() {
       return inventoryService.getAllEvents();
    }

    @GetMapping("inventory/venue/{venueId}")
    public @ResponseBody VenueInventoryResponse getVenueInformation(@PathVariable("venueId") Long venueId) {
        return inventoryService.getVenueInformation(venueId);
    }

    @GetMapping("inventory/event/{eventId}")
    public @ResponseBody EventInventoryResponse getInventoryForEvent(@PathVariable("eventId") Long eventId) {
        return inventoryService.getEventInventory(eventId);
    }
}

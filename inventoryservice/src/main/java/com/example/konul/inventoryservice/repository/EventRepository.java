package com.example.konul.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.konul.inventoryservice.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}

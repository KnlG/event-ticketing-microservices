package com.example.konul.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.konul.bookingservice.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}

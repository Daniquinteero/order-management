package com.daniel.ordermanagement;

import com.daniel.ordermanagement.dto.CustomerRequestDto;
import com.daniel.ordermanagement.entity.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import com.daniel.ordermanagement.repository.CustomerRepository;

import java.time.LocalDateTime;


@SpringBootApplication
public class OrderManagementApplication {

    public static void main(String[] args) {

        SpringApplication.run(OrderManagementApplication.class, args);

    }


}

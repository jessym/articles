package com.jessym;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final EmailService emailService;
    private final CustomerRepository repository;

    void register(Customer customer) {
        emailService.sendWelcomeEmail(customer.getEmail());
        repository.save(customer);
    }

}

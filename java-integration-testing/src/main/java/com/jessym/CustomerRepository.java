package com.jessym;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, String> {

    Optional<Customer> findByEmail(String email);

}

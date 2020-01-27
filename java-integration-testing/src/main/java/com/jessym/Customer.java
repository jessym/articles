package com.jessym;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Entity(name = "customer")
public class Customer {

    @Id
    @Column(name = "email")
    @JsonProperty("email")
    @NotEmpty(message = "The 'email' field is required")
    @Email(message = "The 'email' field is invalid")
    private String email;

    @Column(name = "name")
    @JsonProperty("name")
    @NotEmpty(message = "The 'name' field is required")
    private String name;

}

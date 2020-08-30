package com.jessym.store.resources.dto;

import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RegisterAccountRequest {

    @Email
    @NotBlank
    @JsonbProperty("email")
    private String email;

    @NotBlank
    @JsonbProperty("name")
    private String name;

}

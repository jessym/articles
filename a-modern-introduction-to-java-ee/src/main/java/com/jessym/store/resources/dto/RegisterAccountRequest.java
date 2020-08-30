package com.jessym.store.resources.dto;

import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RegisterAccountRequest {

    @NotBlank
    @JsonbProperty("name")
    private String name;

    @Email
    @NotBlank
    @JsonbProperty("email")
    private String email;

}

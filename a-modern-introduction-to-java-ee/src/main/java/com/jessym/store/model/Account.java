package com.jessym.store.model;

import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;

@Data
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonbProperty("id")
    private Long id;

    @Column(name = "name")
    @JsonbProperty("name")
    private String name;

    @Column(name = "email")
    @JsonbProperty("email")
    private String email;

}

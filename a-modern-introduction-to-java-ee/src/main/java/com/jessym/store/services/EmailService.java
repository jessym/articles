package com.jessym.store.services;

import com.jessym.store.model.Account;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.Singleton;

@Slf4j
@Singleton
public class EmailService {

    public void sendEmail(Account account) {
        log.info("Thanks for signing up {} ({})", account.getName(), account.getEmail());
    }

}

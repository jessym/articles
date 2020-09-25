package com.jessym.store.services;

import com.jessym.store.model.Account;
import com.jessym.store.persistence.AccountRepository;

import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.List;

@Singleton
public class AccountService {

    @Inject
    AccountRepository accountRepository;

    @Inject
    EmailService emailService;

    public Account register(String name, String email) {
        Account account = accountRepository.register(name, email);
        emailService.sendEmail(account);
        return account;
    }

    public List<Account> list() {
        return accountRepository.list();
    }

    public Account findById(Long id) {
        return accountRepository.findById(id);
    }

}

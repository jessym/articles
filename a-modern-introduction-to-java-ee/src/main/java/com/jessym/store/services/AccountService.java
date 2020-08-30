package com.jessym.store.services;

import com.jessym.store.model.Account;
import com.jessym.store.persistence.AccountRepository;

import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.List;

/**
 * To disable container managed transactions throughout this class, add the following annotation:
 * <code>@TransactionManagement(TransactionManagementType.BEAN)</code>
 */
@Singleton
public class AccountService {

    @Inject
    AccountRepository accountRepository;

    @Inject
    EmailService emailService;

    public Account register(String email, String name) {
        Account account = accountRepository.register(email, name);
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

package com.jessym.store.persistence;

import com.jessym.store.model.Account;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Singleton
public class AccountRepository {

    @PersistenceContext(unitName = "PostgresPU")
    EntityManager em;

    public Account register(String name, String email) {
        Account account = new Account();
        account.setName(name);
        account.setEmail(email);
        em.persist(account);
        return account;
    }

    public List<Account> list() {
        CriteriaQuery<Account> criteria = em.getCriteriaBuilder().createQuery(Account.class);
        Root<Account> root = criteria.from(Account.class);
        criteria.select(root);
        return em.createQuery(criteria).getResultList();
    }

    public Account findById(Long id) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
        Root<Account> root = criteria.from(Account.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("id"), id));
        return em.createQuery(criteria)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

}

package com.jessym.store.resources;

import com.jessym.store.model.Account;
import com.jessym.store.resources.dto.RegisterAccountRequest;
import com.jessym.store.services.AccountService;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class AccountResource {

    @Inject
    AccountService accountService;

    @POST
    public Account register(@Valid RegisterAccountRequest request) {
        return accountService.register(request.getName(), request.getEmail());
    }

    @GET
    public List<Account> list() {
        return accountService.list();
    }

    @GET
    @Path("/{id}")
    public Account findById(@PathParam("id") Long id) {
        return accountService.findById(id);
    }

}

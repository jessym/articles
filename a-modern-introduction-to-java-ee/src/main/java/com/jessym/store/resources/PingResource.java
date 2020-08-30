package com.jessym.store.resources;

import javax.ejb.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/ping")
@Singleton
public class PingResource {

    @GET
    public Response ping() {
        return Response.ok(hashCode()).build();
    }

}

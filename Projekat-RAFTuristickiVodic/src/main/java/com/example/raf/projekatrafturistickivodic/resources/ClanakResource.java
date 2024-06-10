package com.example.raf.projekatrafturistickivodic.resources;

import com.example.raf.projekatrafturistickivodic.entities.Clanak;
import com.example.raf.projekatrafturistickivodic.services.ClanakService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/clanak")
public class ClanakResource {

    @Inject
    ClanakService clanakService;

    @GET
    @Path("sve/{filter}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAllClanak(@PathParam("filter") String filter, @QueryParam("page") int page,
                                  @QueryParam("limit") int limit){
        Map<String, Object> response = new HashMap<>();
        int br = this.clanakService.countClanakByFilter(filter);
        response.put("clancii", this.clanakService.findAllClanak(filter,limit,page));
        response.put("stranice", Math.ceil( (double) br / limit));
        return Response.ok(response).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    public Clanak updateClanak(@Valid Clanak clanak){
        return this.clanakService.updateClanak(clanak);
    }
    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public void deleteClanak(@PathParam("id") Integer id){
        this.clanakService.deleteClanak(id);
    }

    @POST
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public void incrementbrposetioca(@PathParam("id") Integer id){
        this.clanakService.incrementbrposetioca(id);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Clanak addClanak(@Valid Clanak clanak){
        return this.clanakService.addClanak(clanak);
    }

    @GET
    @Path("/aktivnost/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAllClanakByAktivnost(@PathParam("id") Integer id,
                                                 @QueryParam("page") int page,
                                                 @QueryParam("limit") int limit){
        Map<String, Object> response = new HashMap<>();
        int br = this.clanakService.countClanakByAktivnost(id);
        response.put("clancii", this.clanakService.findAllClanakByAktivnost(id,limit,page));
        response.put("stranice", Math.ceil( (double) br / limit));
        return Response.ok(response).build();
    }

    @GET
    @Path("/byid/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Clanak findClanakById(@PathParam("id") Integer id){
        return this.clanakService.findClanakById(id);
    }
}

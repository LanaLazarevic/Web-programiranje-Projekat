package com.example.raf.projekatrafturistickivodic.resources;

import com.example.raf.projekatrafturistickivodic.entities.Clanak;
import com.example.raf.projekatrafturistickivodic.services.ClanakService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/clanak")
public class ClanakResource {

    @Inject
    ClanakService clanakService;

    @GET
    @Path("/{filter}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAllClanak(@PathParam("filter") String filter){
        return Response.ok(this.clanakService.findAllClanak(filter)).build();
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
    public List<Clanak> findAllClanakByAktivnost(@PathParam("id") Integer id){
        return this.clanakService.findAllClanakByAktivnost(id);
    }
}

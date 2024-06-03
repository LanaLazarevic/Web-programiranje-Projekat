package com.example.raf.projekatrafturistickivodic.resources;

import com.example.raf.projekatrafturistickivodic.entities.Komentar;
import com.example.raf.projekatrafturistickivodic.services.KomentarService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
@Path("/kom")
public class KomentarResource {

    @Inject
    KomentarService komentarService;

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getKomentar(@PathParam("id") Integer id)
    {
        return Response.ok(this.komentarService.allKomentar(id)).build();
    }

    @POST
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Komentar addKomentar(@PathParam("id") Integer id, @Valid Komentar komentar)
    {
        return this.komentarService.addKomentar(komentar,id);
    }
}

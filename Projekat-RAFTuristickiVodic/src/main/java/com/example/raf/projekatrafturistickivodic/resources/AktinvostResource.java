package com.example.raf.projekatrafturistickivodic.resources;

import com.example.raf.projekatrafturistickivodic.entities.Aktivnost;
import com.example.raf.projekatrafturistickivodic.entities.Destinacija;
import com.example.raf.projekatrafturistickivodic.services.AktivnostService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/akt")
public class AktinvostResource {

    @Inject
    AktivnostService aktivnostService;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAktivnost()
    {
        return Response.ok(this.aktivnostService.findAllAktivnost()).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Aktivnost addAktivnsot(@Valid Aktivnost aktivnost)
    {
        return this.aktivnostService.addAktivnost(aktivnost);
    }
}

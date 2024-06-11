package com.example.raf.projekatrafturistickivodic.resources;

import com.example.raf.projekatrafturistickivodic.entities.Aktivnost;
import com.example.raf.projekatrafturistickivodic.entities.Destinacija;
import com.example.raf.projekatrafturistickivodic.services.AktivnostService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Path("/akt")
public class AktinvostResource {

    @Inject
    AktivnostService aktivnostService;

    @GET
    @Path("/sve")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAktivnost(@QueryParam("ids") String ids)
    {
        List<Integer> idss = Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        return Response.ok(this.aktivnostService.findAllAktivnost(idss)).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Aktivnost addAktivnsot(@Valid Aktivnost aktivnost)
    {
        return this.aktivnostService.addAktivnost(aktivnost);
    }
}

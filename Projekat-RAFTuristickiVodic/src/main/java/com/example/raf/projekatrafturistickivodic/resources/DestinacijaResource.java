package com.example.raf.projekatrafturistickivodic.resources;

import com.example.raf.projekatrafturistickivodic.entities.Destinacija;
import com.example.raf.projekatrafturistickivodic.services.DestinacijaService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/dest")
public class DestinacijaResource {

    @Inject
    DestinacijaService destinacijaService;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getDestinacija(@QueryParam("page")int page,
                                   @QueryParam("limit") int limit)
    {
        return Response.ok(this.destinacijaService.allDestinacije(limit,page)).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Destinacija addDestinacija(@Valid Destinacija destinacija)
    {
        return this.destinacijaService.addDestinacija(destinacija);
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    public Destinacija updateDestinacija(@Valid Destinacija destinacija)
    {
        return this.destinacijaService.updateDestinacija(destinacija);
    }

    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String deleteDestinacija(@PathParam("id") Integer id)
    {
        return this.destinacijaService.deleteDestinacija(id);
    }
}

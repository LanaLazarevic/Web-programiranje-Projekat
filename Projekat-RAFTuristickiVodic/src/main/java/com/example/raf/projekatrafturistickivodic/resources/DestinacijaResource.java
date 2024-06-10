package com.example.raf.projekatrafturistickivodic.resources;

import com.example.raf.projekatrafturistickivodic.entities.Destinacija;
import com.example.raf.projekatrafturistickivodic.services.DestinacijaService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/dest")
public class DestinacijaResource {

    @Inject
    DestinacijaService destinacijaService;

    @GET
    @Path("/sve")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getDestinacija(@QueryParam("page")int page,
                                   @QueryParam("limit") int limit)
    {
        Map<String, Object> response = new HashMap<>();
        int br = this.destinacijaService.countDestinacija();
        response.put("destinacijee", this.destinacijaService.allDestinacije(limit,page));
        response.put("stranice", Math.ceil( (double) br / limit));
        return Response.ok(response).build();
    }

    @GET
    @Path("/ids")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getDestinacije(@QueryParam("page")int page,
                                   @QueryParam("limit") int limit,
                                   @QueryParam("ids") String ids)
    {
        Map<String, Object> response = new HashMap<>();
        List<Integer> idList = Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        if (idList.isEmpty()) {
            response.put("message", "No IDs provided");
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
        List<Destinacija> destinacije = destinacijaService.allDestinacijeByIds(limit, page, idList);
        response.put("destinacije", destinacije);

        return Response.ok(response).build();
    }

    @GET
    @Path("/ime/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getDestinacije(@PathParam("id") int id)
    {
        Map<String, Object> response = new HashMap<>();


       String ime = destinacijaService.getDestinacija(id);
        response.put("ime", ime);

        return Response.ok(response).build();
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
    public Response deleteDestinacija(@PathParam("id") Integer id)
    {
        Map<String, Object> response = new HashMap<>();
        response.put("poruka", this.destinacijaService.deleteDestinacija(id));
        return Response.ok(response).build();
    }
}

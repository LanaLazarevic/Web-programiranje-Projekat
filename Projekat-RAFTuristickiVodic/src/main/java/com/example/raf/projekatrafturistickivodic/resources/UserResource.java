package com.example.raf.projekatrafturistickivodic.resources;



import com.example.raf.projekatrafturistickivodic.entities.Korisnik;
import com.example.raf.projekatrafturistickivodic.requests.LoginRequest;
import com.example.raf.projekatrafturistickivodic.services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;

    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid LoginRequest loginRequest)
    {
        Map<String, String> response = new HashMap<>();

        String jwt = this.userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (jwt.startsWith("Taj") || jwt.startsWith("Korisnik")) {
            response.put("message", jwt);
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

        response.put("jwt", jwt);
        response.put("ime", userService.findKorisnik(loginRequest.getEmail()).getIme());
        response.put("uloga", userService.findKorisnik(loginRequest.getEmail()).getTip());
        return Response.ok(response).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Korisnik addKorisnik(@Valid Korisnik korisnik)
    {
        return this.userService.addKorisnik(korisnik);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getKorisnik()
    {
        return Response.ok(this.userService.findAllKorisnik()).build();
    }

    @POST
    @Path("status/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public void changeStatus(@PathParam("id") Integer id)
    {
        this.userService.changeStatus(id);
    }
    //dal put ili post
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    public Korisnik updateKorisnik(@Valid Korisnik korisnik)
    {
        return this.userService.updateKorisnik(korisnik);
    }

}

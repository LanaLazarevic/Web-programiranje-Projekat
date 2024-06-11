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
            response.put("jwt", jwt);
            return Response.ok(response).build();
        }
        response.put("jwt", jwt);
        return Response.ok(response).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Response addKorisnik(@Valid Korisnik korisnik)
    {
        Map<String, Object> response = new HashMap<>();
        if(this.userService.addKorisnik(korisnik).getKorisnik_id() != null)
            response.put("poruka","Uspesno dodat korisnik");
        else
            response.put("poruka","Korisnik mora da ima jedinstveni email.");
        return Response.ok(response).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getKorisnik(@QueryParam("page")int page,
                                @QueryParam("limit") int limit)
    {   Map<String, Object> response = new HashMap<>();
        int br = this.userService.countKorisnik();
        response.put("korisnicii", this.userService.findAllKorisnik(limit,page));
        response.put("stranice", Math.ceil( (double) br / limit));
        return Response.ok(response).build();
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
    public Response updateKorisnik(@Valid Korisnik korisnik)
    {
        Map<String, Object> response = new HashMap<>();
        response.put("poruka", this.userService.updateKorisnik(korisnik) );
        return Response.ok(response).build();
    }

}

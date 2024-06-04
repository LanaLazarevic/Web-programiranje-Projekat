package com.example.raf.projekatrafturistickivodic.services;


import com.auth0.jwt.algorithms.Algorithm;
import com.example.raf.projekatrafturistickivodic.entities.Korisnik;
import com.example.raf.projekatrafturistickivodic.repositories.korisnik.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public class UserService {

    @Inject
    UserRepository userRepository;

    public String login(String email, String password)
    {
        String hashedPassword = DigestUtils.sha256Hex(password);

        Korisnik korisnik = userRepository.findKorisnik(email);

        if (korisnik == null || !korisnik.getLozinka().equals(hashedPassword)) {
            return "Taj korisnik ne postoji!";
        }
        if(korisnik.getStatus().equalsIgnoreCase("neaktivan"))
            return "Korisnik nije aktivan";

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000); // One day

        Algorithm algorithm = Algorithm.HMAC256("secret");


        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(email)
                .withClaim("uloga", korisnik.getTip())
                .sign(algorithm);
    }

    public Korisnik addKorisnik(Korisnik korisnik){
        return this.userRepository.addKorisnik(korisnik);
    }

    public List<Korisnik> findAllKorisnik(){
        return this.userRepository.findAllKorisnik();
    }

    public void changeStatus(Integer id){
        this.userRepository.changeStatus(id);
    }

    public Korisnik updateKorisnik(Korisnik korisnik){
        return this.userRepository.updateKorisnik(korisnik);
    }

    public Korisnik findKorisnik(String email){
        return this.userRepository.findKorisnik(email);
    }
    public boolean isAuthorized(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        String uloga = jwt.getClaim("uloga").asString();



        if (uloga.equalsIgnoreCase("admin")){
            return true;
        }

        return false;
    }
}

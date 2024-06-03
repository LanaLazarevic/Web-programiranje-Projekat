package com.example.raf.projekatrafturistickivodic.repositories.korisnik;


import com.example.raf.projekatrafturistickivodic.entities.Korisnik;

import java.util.List;

public interface UserRepository {
     Korisnik findKorisnik(String email);
     Korisnik addKorisnik(Korisnik korisnik);
     void changeStatus(Integer id);
     Korisnik updateKorisnik(Korisnik korisnik);
     List<Korisnik> findAllKorisnik();
}

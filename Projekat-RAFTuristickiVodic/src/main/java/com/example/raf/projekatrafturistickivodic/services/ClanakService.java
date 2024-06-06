package com.example.raf.projekatrafturistickivodic.services;

import com.example.raf.projekatrafturistickivodic.entities.Clanak;
import com.example.raf.projekatrafturistickivodic.repositories.clanak.ClanakRepository;

import javax.inject.Inject;
import java.util.List;

public class ClanakService {

    @Inject
    ClanakRepository clanakRepository;

    public List<Clanak> findAllClanak(String filter, int limit, int current){
        int offset = (current - 1) * limit;
        return this.clanakRepository.findAllClanak(filter, limit, offset);
    }

    public Clanak updateClanak(Clanak clanak){
        return this.clanakRepository.updateClanak(clanak);
    }

    public void deleteClanak(Integer id){
         this.clanakRepository.deleteClanak(id);
    }

    public void incrementbrposetioca(Integer id){
        this.clanakRepository.incrementbrposetioca(id);
    }

    public Clanak addClanak(Clanak clanak){
        return this.clanakRepository.addClanak(clanak);
    }

    public List<Clanak> findAllClanakByAktivnost(Integer id){
        return this.clanakRepository.findAllClanakByAktivnost(id);
    }

    public Clanak findClanakById(Integer id){
        return this.clanakRepository.findClanakById(id);
    }
}

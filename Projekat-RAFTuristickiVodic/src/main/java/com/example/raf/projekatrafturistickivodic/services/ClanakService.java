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

    public String updateClanak(Clanak clanak){
        return this.clanakRepository.updateClanak(clanak);
    }

    public String deleteClanak(Integer id){
         return this.clanakRepository.deleteClanak(id);
    }

    public void incrementbrposetioca(Integer id){
        this.clanakRepository.incrementbrposetioca(id);
    }

    public Clanak addClanak(Clanak clanak){
        return this.clanakRepository.addClanak(clanak);
    }

    public List<Clanak> findAllClanakByAktivnost(Integer id, int limit, int current){
        int offset = (current - 1) * limit;
        return this.clanakRepository.findAllClanakByAktivnost(id, limit, offset);
    }

    public Clanak findClanakById(Integer id){
        return this.clanakRepository.findClanakById(id);
    }
    public int countClanakByFilter(String filter) {
        return this.clanakRepository.countClanakByFilter(filter);
    }
    public int countClanakByAktivnost(Integer id) {
        return this.clanakRepository.countClanakByAktivnsot(id);
    }
}

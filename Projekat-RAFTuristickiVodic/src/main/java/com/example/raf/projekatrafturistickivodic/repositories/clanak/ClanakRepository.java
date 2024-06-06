package com.example.raf.projekatrafturistickivodic.repositories.clanak;

import com.example.raf.projekatrafturistickivodic.entities.Clanak;

import java.util.List;

public interface ClanakRepository {
    List<Clanak> findAllClanak(String filter, int limit, int offset);
    Clanak updateClanak(Clanak clanak);
    void deleteClanak(Integer id);
    void incrementbrposetioca(Integer id);
    Clanak addClanak(Clanak clanak);
    List<Clanak> findAllClanakByAktivnost(Integer id);
    Clanak findClanakById(Integer id);
}

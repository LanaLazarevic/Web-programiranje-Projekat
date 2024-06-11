package com.example.raf.projekatrafturistickivodic.repositories.destinacija;


import com.example.raf.projekatrafturistickivodic.entities.Destinacija;

import java.util.List;

public interface DestinacijeRepository {
    List<Destinacija> findAllDestinacije(int limit, int offset);
    String updateDestinacija(Destinacija destinacija);
    String deleteDestinacija(Integer id);
    Destinacija addDestinacija(Destinacija destinacija);
    String findDestinacijaById(Integer id);
    List<Destinacija> findAllDestinacijeByIds(int limit, int offset, List<Integer> ids);
    int countdestinacije();
}

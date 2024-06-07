package com.example.raf.projekatrafturistickivodic.repositories.destinacija;


import com.example.raf.projekatrafturistickivodic.entities.Destinacija;

import java.util.List;

public interface DestinacijeRepository {
    List<Destinacija> findAllDestinacije(int limit, int offset);
    Destinacija updateDestinacija(Destinacija destinacija);
    String deleteDestinacija(Integer id);
    Destinacija addDestinacija(Destinacija destinacija);
    Integer findDestinacijaIdByName(String destinacijaName);
    int countdestinacije();
}

package com.example.raf.projekatrafturistickivodic.services;

import com.example.raf.projekatrafturistickivodic.entities.Destinacija;
import com.example.raf.projekatrafturistickivodic.repositories.destinacija.DestinacijeRepository;

import javax.inject.Inject;
import java.util.List;

public class DestinacijaService {
    @Inject
    DestinacijeRepository destinacijeRepository;

    public List<Destinacija> allDestinacije() {
        return this.destinacijeRepository.findAllDestinacije();
    }

    public Destinacija updateDestinacija(Destinacija destinacija) {
        return this.destinacijeRepository.updateDestinacija(destinacija);
    }

    public String deleteDestinacija(Integer id) {
        return this.destinacijeRepository.deleteDestinacija(id);
    }

    public Destinacija addDestinacija(Destinacija destinacija) {
        return this.destinacijeRepository.addDestinacija(destinacija);
    }
}

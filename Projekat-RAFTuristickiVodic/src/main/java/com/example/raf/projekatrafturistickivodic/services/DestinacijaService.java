package com.example.raf.projekatrafturistickivodic.services;

import com.example.raf.projekatrafturistickivodic.entities.Destinacija;
import com.example.raf.projekatrafturistickivodic.repositories.destinacija.DestinacijeRepository;

import javax.inject.Inject;
import java.util.List;

public class DestinacijaService {
    @Inject
    DestinacijeRepository destinacijeRepository;

    public List<Destinacija> allDestinacije(int limit, int page) {
        int offset = (page - 1) * limit;
        return this.destinacijeRepository.findAllDestinacije(limit,offset);
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

    public int countDestinacija() {
        return this.destinacijeRepository.countdestinacije();
    }

    public List<Destinacija> allDestinacijeByIds(int limit, int page, List<Integer> ids){
        int offset = (page - 1) * limit;
        return this.destinacijeRepository.findAllDestinacijeByIds(limit,offset, ids);
    }

    public String getDestinacija(Integer id) {
        return this.destinacijeRepository.findDestinacijaById(id);
    }

}

package com.example.raf.projekatrafturistickivodic.services;

import com.example.raf.projekatrafturistickivodic.entities.Aktivnost;
import com.example.raf.projekatrafturistickivodic.repositories.aktivnost.AktivnostRepository;

import javax.inject.Inject;
import java.util.List;

public class AktivnostService {
    @Inject
    AktivnostRepository aktivnostRepository;

    public Aktivnost addAktivnost(Aktivnost aktivnost) {
        return this.aktivnostRepository.addAktivnost(aktivnost);
    }

    public List<Aktivnost> findAllAktivnost(List<Integer> ids) {
        return this.aktivnostRepository.findAllAktivnost(ids);
    }
}

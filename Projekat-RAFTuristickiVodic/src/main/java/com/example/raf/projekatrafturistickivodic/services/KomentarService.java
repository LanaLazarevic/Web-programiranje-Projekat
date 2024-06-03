package com.example.raf.projekatrafturistickivodic.services;

import com.example.raf.projekatrafturistickivodic.entities.Komentar;
import com.example.raf.projekatrafturistickivodic.repositories.komentar.KomentarRepository;

import javax.inject.Inject;
import java.util.List;

public class KomentarService {

    @Inject
    KomentarRepository komentarRepository;

    public List<Komentar> allKomentar(Integer id) {
        return this.komentarRepository.findAllKomentar(id);
    }

    public Komentar addKomentar(Komentar komentar, Integer id) {
        return this.komentarRepository.addKomentar(komentar,id);
    }
}

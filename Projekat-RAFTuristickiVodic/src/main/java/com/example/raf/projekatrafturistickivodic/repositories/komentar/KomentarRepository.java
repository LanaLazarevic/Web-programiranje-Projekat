package com.example.raf.projekatrafturistickivodic.repositories.komentar;

import com.example.raf.projekatrafturistickivodic.entities.Komentar;

import java.util.List;

public interface KomentarRepository {
    List<Komentar> findAllKomentar(Integer id);
    Komentar addKomentar (Komentar komentar, Integer id);
}

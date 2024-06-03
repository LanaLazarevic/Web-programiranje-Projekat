package com.example.raf.projekatrafturistickivodic.repositories.aktivnost;

import com.example.raf.projekatrafturistickivodic.entities.Aktivnost;

import java.util.List;

public interface AktivnostRepository {
    List<Aktivnost> findAllAktivnost();
    Aktivnost addAktivnost(Aktivnost aktivnost);
    Integer findAktivnostByName(String aktivnostName);
}

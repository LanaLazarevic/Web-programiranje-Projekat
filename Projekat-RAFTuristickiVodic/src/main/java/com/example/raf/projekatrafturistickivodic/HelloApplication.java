package com.example.raf.projekatrafturistickivodic;

import com.example.raf.projekatrafturistickivodic.repositories.aktivnost.AktivnostRepository;
import com.example.raf.projekatrafturistickivodic.repositories.aktivnost.MySqlAktivnosrtRepository;
import com.example.raf.projekatrafturistickivodic.repositories.clanak.ClanakRepository;
import com.example.raf.projekatrafturistickivodic.repositories.clanak.MySqlClanakRepository;
import com.example.raf.projekatrafturistickivodic.repositories.destinacija.DestinacijeRepository;
import com.example.raf.projekatrafturistickivodic.repositories.destinacija.MySqlDestinacijeRepository;
import com.example.raf.projekatrafturistickivodic.repositories.komentar.KomentarRepository;
import com.example.raf.projekatrafturistickivodic.repositories.komentar.MySqlKomentarRepository;
import com.example.raf.projekatrafturistickivodic.repositories.korisnik.MySqlUserRepository;
import com.example.raf.projekatrafturistickivodic.repositories.korisnik.UserRepository;
import com.example.raf.projekatrafturistickivodic.services.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {
    public HelloApplication() {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySqlAktivnosrtRepository.class).to(AktivnostRepository.class).in(Singleton.class);
                this.bind(MySqlClanakRepository.class).to(ClanakRepository.class).in(Singleton.class);
                this.bind(MySqlDestinacijeRepository.class).to(DestinacijeRepository.class).in(Singleton.class);
                this.bind(MySqlKomentarRepository.class).to(KomentarRepository.class).in(Singleton.class);
                this.bind(MySqlUserRepository.class).to(UserRepository.class).in(Singleton.class);

                this.bindAsContract(UserService.class);
                this.bindAsContract(DestinacijaService.class);
                this.bindAsContract(KomentarService.class);
                this.bindAsContract(AktivnostService.class);
                this.bindAsContract(ClanakService.class);
            }
        };
        register(binder);

        packages("com.example.raf.projekatrafturistickivodic");
    }
}
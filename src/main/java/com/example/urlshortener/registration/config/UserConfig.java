package com.example.urlshortener.registration.config;

import com.example.urlshortener.registration.entity.User;
import com.example.urlshortener.registration.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {


    // korišteno za popunavanje UserRepository radi lakšeg testiranja u Postmanu
    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository) {
        return args -> {
            User user1 = new User("Tin2002", "abc123");
            User user2 = new User("Pero123", "password");
            User user3 = new User("strgi_2", "sifra321");
            repository.saveAll(
                    List.of(user1, user2, user3)
            );
        };

    }
}

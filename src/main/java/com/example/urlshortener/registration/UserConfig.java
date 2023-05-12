package com.example.urlshortener.registration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository) {
        return args -> {
            User tin = new User("Tin2002", "abc123");
            User pero = new User("Pero123");

            repository.saveAll(
                    List.of(tin, pero)
            );
        };

    }
}

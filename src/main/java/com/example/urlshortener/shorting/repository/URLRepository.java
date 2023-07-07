package com.example.urlshortener.shorting.repository;

import com.example.urlshortener.shorting.entity.URL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface URLRepository extends JpaRepository<URL, Long> {

    Optional<URL> findByUrl(String url);
    Optional<URL> findURLById(Long id);
}

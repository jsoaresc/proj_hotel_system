package com.jcidade.sistema_hoteis.person.repository;

import com.jcidade.sistema_hoteis.person.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    boolean existsByDocument(String document);

    Optional<Guest> findByDocument(String document);
}
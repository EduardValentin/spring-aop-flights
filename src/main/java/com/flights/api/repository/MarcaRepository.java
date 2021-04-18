package com.flights.api.repository;

import com.flights.api.model.MarcaAvion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends JpaRepository<MarcaAvion, Integer> {
}

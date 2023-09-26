package com.barberia.api.persistence.repository;

import com.barberia.api.persistence.entities.Barbero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberoRepository extends JpaRepository<Barbero, Long> {
}

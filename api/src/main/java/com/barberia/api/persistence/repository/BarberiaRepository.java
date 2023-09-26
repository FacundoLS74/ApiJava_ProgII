package com.barberia.api.persistence.repository;

import com.barberia.api.persistence.entities.Barberia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberiaRepository extends JpaRepository<Barberia, Long> {
}

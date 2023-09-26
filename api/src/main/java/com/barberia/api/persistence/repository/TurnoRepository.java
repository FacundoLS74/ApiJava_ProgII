package com.barberia.api.persistence.repository;

import com.barberia.api.persistence.entities.Barberia;
import com.barberia.api.persistence.entities.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurnoRepository extends JpaRepository<Turno, Long> {
}

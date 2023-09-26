package com.barberia.api.service;

import com.barberia.api.persistence.entities.Barberia;
import com.barberia.api.persistence.repository.BarberiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarberiaService {
    @Autowired
    private BarberiaRepository barberiaRepository;

    public Barberia crearBarberia(Barberia barberia) {
        return barberiaRepository.save(barberia);
    }

    public List<Barberia> obtenerTodasLasBarberias() {
        return barberiaRepository.findAll();
    }

    public Barberia obtenerBarberiaPorId(Long id) {return barberiaRepository.findById(id).orElse(null);}

    public Barberia actualizarBarberia(Barberia barberia) {return barberiaRepository.save(barberia);}

    public void eliminarBarberiaPorId(Long id) {barberiaRepository.deleteById(id);}
}

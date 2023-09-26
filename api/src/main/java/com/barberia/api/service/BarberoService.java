package com.barberia.api.service;

import com.barberia.api.persistence.entities.Barbero;
import com.barberia.api.persistence.repository.BarberoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarberoService {

    @Autowired
    BarberoRepository barberoRepository;

    public void guardarBarbero(Barbero barbero) {barberoRepository.save(barbero);}

    public List<Barbero> retornarTodosLosBarberos(){return barberoRepository.findAll();}

    public Barbero obtenerBarberoPorId(Long id) {return barberoRepository.findById(id).orElse(null);}

    public Barbero actualizarBarbero(Barbero barbero) {return barberoRepository.save(barbero);}
    public void eliminarBarbero(Long id) {barberoRepository.deleteById(id);}
}

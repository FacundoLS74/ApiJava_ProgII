package com.barberia.api.service;

import com.barberia.api.persistence.entities.Barberia;
import com.barberia.api.persistence.entities.Barbero;
import com.barberia.api.persistence.entities.Cliente;
import com.barberia.api.persistence.entities.Turno;
import com.barberia.api.persistence.repository.BarberoRepository;
import com.barberia.api.persistence.repository.ClienteRepository;
import com.barberia.api.persistence.repository.TurnoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService {
    @Autowired
    private TurnoRepository turnoRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BarberoRepository barberoRepository;

    public Turno guardarTurno(Turno turno) {

        // Verificamos si el cliente y el barbero existen en la base de datos
        if (turno.getCliente() != null && turno.getCliente().getId() != null) {
            Cliente cliente = clienteRepository.findById(turno.getCliente().getId()).orElse(null);
            turno.setCliente(cliente);
        }

        if (turno.getBarbero() != null && turno.getBarbero().getId() != null) {
            Barbero barbero = barberoRepository.findById(turno.getBarbero().getId()).orElse(null);
            turno.setBarbero(barbero);
        }

        // Guardamos el turno con los objetos cliente y barbero actualizados
        return turnoRepository.save(turno);
    }

    public List<Turno> obtenerTodosLosTurnos() {
        return turnoRepository.findAll();
    }

    public Turno obtenerTurnoPorId(Long id) {
        return turnoRepository.findById(id).orElse(null);
    }


    // Otros métodos según las operaciones que necesites
    public Turno actualizarTurno(Turno turno) {
        // Verificar si el turno existe en la base de datos
        if (turno.getId() == null) {
            throw new IllegalArgumentException("ID del turno no proporcionado");
        }

        // Obtener el turno existente de la base de datos
        Turno turnoExistente = turnoRepository.findById(turno.getId()).orElseThrow(
                () -> new EntityNotFoundException("Turno con ID " + turno.getId() + " no encontrado")
        );

        turnoExistente.setCliente(turno.getCliente());
        turnoExistente.setBarbero(turno.getBarbero());
        turnoExistente.setBarberia(turno.getBarberia());
        turnoExistente.setFechaHora(turno.getFechaHora());


        // Guardar los cambios en la base de datos
        return turnoRepository.save(turnoExistente);
    }

    public void eliminarTurnoPorId(Long id) {
        // Verificar si el turno existe en la base de datos
        if (!turnoRepository.existsById(id)) {
            throw new EntityNotFoundException("Turno con ID " + id + " no encontrado");
        }

        // Eliminar el turno por ID
        turnoRepository.deleteById(id);
    }
}
package com.barberia.api.controller;

import com.barberia.api.persistence.entities.Barberia;
import com.barberia.api.persistence.entities.Turno;
import com.barberia.api.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turno")
public class TurnoController {
    @Autowired
    private TurnoService turnoService;

    @PostMapping("/crear")
    public ResponseEntity<Turno> crearTurno(@RequestBody Turno turno) {
        // Aquí puedes agregar validaciones y lógica antes de guardar el turno
        Turno nuevoTurno = turnoService.guardarTurno(turno);
        return ResponseEntity.created(null).body(nuevoTurno);
    }

    @GetMapping("/obtener")
    public ResponseEntity<List<Turno>> obtenerTurnosPorBarberia() {
        return ResponseEntity.ok(turnoService.obtenerTodosLosTurnos());
    }


    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Turno> actualizarTurno(@PathVariable Long id, @RequestBody Turno turno) {
        Turno turnoExistente = turnoService.obtenerTurnoPorId(id);

        if (turnoExistente == null) {
            return ResponseEntity.notFound().build();
        }

        // Actualizar los campos relevantes del turno existente con los datos del turno recibido en el cuerpo
        turnoExistente.setFechaHora(turno.getFechaHora());

        // Actualizar el turno en la base de datos
        Turno turnoActualizado = turnoService.guardarTurno(turnoExistente);

        return ResponseEntity.ok(turnoActualizado);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurnoPorId(@PathVariable Long id) {
        turnoService.eliminarTurnoPorId(id);
        return ResponseEntity.ok("Turno con ID " + id + " eliminado correctamente");
    }

    @GetMapping("/buscarID/{id}")
    public ResponseEntity<Turno> obtenerTurnoPorId(@PathVariable Long id) {
        Turno turno = turnoService.obtenerTurnoPorId(id);

        if (turno == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(turno);
    }

}
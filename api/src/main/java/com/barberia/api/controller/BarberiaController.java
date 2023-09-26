package com.barberia.api.controller;

import com.barberia.api.persistence.entities.Barberia;
import com.barberia.api.service.BarberiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/barberia")
public class BarberiaController {
    @Autowired
    private BarberiaService barberiaService;

    @PostMapping("/crear")
    public ResponseEntity<Barberia> crearBarberia(@RequestBody Barberia barberia) {
        Barberia nuevaBarberia = barberiaService.crearBarberia(barberia);
        return new ResponseEntity<>(nuevaBarberia, HttpStatus.CREATED);
    }

    @GetMapping("/obtenerTodas")
    public ResponseEntity<List<Barberia>> obtenerTodasLasBarberias() {
        List<Barberia> barberias = barberiaService.obtenerTodasLasBarberias();
        return new ResponseEntity<>(barberias, HttpStatus.OK);
    }

    @GetMapping("/obtenerID/{id}")
    public ResponseEntity<Barberia> obtenerBarberiaPorId(@PathVariable Long id) {
        Barberia barberia = barberiaService.obtenerBarberiaPorId(id);
        if (barberia != null) {
            return new ResponseEntity<>(barberia, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Barberia> actualizarBarberia(@PathVariable Long id, @RequestBody Barberia barberia) {
        Barberia barberiaExistente = barberiaService.obtenerBarberiaPorId(id);

        if (barberiaExistente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        barberiaExistente.setDireccion(barberia.getDireccion()); // Actualiza los campos necesarios

        Barberia barberiaActualizada = barberiaService.actualizarBarberia(barberiaExistente);
        return new ResponseEntity<>(barberiaActualizada, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarBarberiaPorId(@PathVariable Long id) {
        barberiaService.eliminarBarberiaPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

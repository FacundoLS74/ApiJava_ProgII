package com.barberia.api.controller;

import com.barberia.api.persistence.entities.Barbero;
import com.barberia.api.persistence.entities.Cliente;
import com.barberia.api.service.BarberoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/barbero")
public class BarberoController {
    @Autowired
    BarberoService barberoService;

    @PostMapping("/crear")
    public void crear(@RequestBody Barbero barbero) {
        barberoService.guardarBarbero(barbero);
    }

    @GetMapping("/obtenerTodos")
    public ResponseEntity<List<Barbero>> traertodos() {
        List<Barbero> barberos = barberoService.retornarTodosLosBarberos();
        return ResponseEntity.ok(barberos);
    }

    @GetMapping("/obtenerID/{id}")
    public ResponseEntity<Barbero> obtenerBarberoPorId(@PathVariable Long id) {
        Barbero barbero = barberoService.obtenerBarberoPorId(id);
        if (barbero != null) {
            return ResponseEntity.ok(barbero);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Barbero> actualizarBarbero(@PathVariable Long id, @RequestBody Barbero barbero) {
        Barbero barberoExistente = barberoService.obtenerBarberoPorId(id);

        if (barberoExistente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        barberoExistente.setNombre(barbero.getNombre());
        barberoExistente.setApellido(barbero.getApellido());
        barberoExistente.setCorreo(barbero.getCorreo());
        barberoExistente.setDni(barbero.getDni());

        Barbero barberoActualizado = barberoService.actualizarBarbero(barberoExistente);
        return new ResponseEntity<>(barberoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarBarberoPorId(@PathVariable Long id) {
        Barbero barberoExistente = barberoService.obtenerBarberoPorId(id);

        if (barberoExistente != null) {
            barberoService.eliminarBarbero(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


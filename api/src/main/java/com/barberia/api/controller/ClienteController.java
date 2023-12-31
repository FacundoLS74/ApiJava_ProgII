package com.barberia.api.controller;

import com.barberia.api.exceptions.BadRequestException;
import com.barberia.api.persistence.entities.Cliente;
import com.barberia.api.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    ClienteService clienteService;
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en la solicitud: " + e.getMessage());
    }

    @PostMapping("/crear")
    public ResponseEntity<String> crear(@RequestBody Cliente cliente) throws BadRequestException {
        clienteService.guardarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente creado con éxito");
    }


    @GetMapping("/obtenerTodos")
    public ResponseEntity<List<Cliente>> traertodos(){
        return ResponseEntity.ok(clienteService.retornarTodosLosClientes());
    }

    @GetMapping("/obtenerPorId/{id}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable Long id) {
        Cliente cliente = clienteService.obtenerClientePorId(id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarClientePorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteNuevo) {
        Cliente clienteExistente = clienteService.obtenerClientePorId(id);
        if (clienteExistente != null) {
            clienteExistente.setNombre(clienteNuevo.getNombre());
            clienteExistente.setApellido(clienteNuevo.getApellido());
            clienteExistente.setCorreo(clienteNuevo.getCorreo());
            clienteExistente.setDni(clienteNuevo.getDni());

            Cliente clienteActualizado = clienteService.actualizarCliente(clienteExistente);
            return ResponseEntity.ok(clienteActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

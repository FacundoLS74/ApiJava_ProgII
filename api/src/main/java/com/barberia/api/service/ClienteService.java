package com.barberia.api.service;

import com.barberia.api.persistence.entities.Cliente;
import com.barberia.api.persistence.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public void guardarCliente(Cliente cliente) {clienteRepository.save(cliente);}

    public Cliente obtenerClientePorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public List<Cliente> retornarTodosLosClientes(){return clienteRepository.findAll();}

    public Cliente actualizarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void eliminarClientePorId(Long id) {clienteRepository.deleteById(id);}


}

package com.example.mercaditolibre.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.example.mercaditolibre.repository.ClienteRepository;
import com.example.mercaditolibre.models.ClienteEntity;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    // Listar todos los clientes
    public List<ClienteEntity> getAllClientes() {
        return clienteRepository.findAll();
    }

    // Todos los clientes activos
    public List<ClienteEntity> getAllClientesActivos() {
        return clienteRepository.findAll().stream().filter(ClienteEntity::isActivo).toList();
    }
    // Obtener un cliente por su ID
    public ClienteEntity getClienteById(long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + id));
    }
    // Crear un nuevo cliente
    public ClienteEntity createCliente(ClienteEntity cliente) {
        //Validación para los campos del cliente
        if (cliente.getNombre() == null || cliente.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre del cliente es obligatorio");
        }
        if (cliente.getApellido() == null || cliente.getApellido().isEmpty()) {
            throw new RuntimeException("El apellido del cliente es obligatorio");
        }
        if (cliente.getEmail() == null || cliente.getEmail().isEmpty()) {
            throw new RuntimeException("El email del cliente es obligatorio");
        }
        if (cliente.getTelefono() == null || cliente.getTelefono().isEmpty()) {
            throw new RuntimeException("El teléfono del cliente es obligatorio");
        }
        return clienteRepository.save(cliente);
    }
    //Editar un cliente existente
    public ClienteEntity updateCliente(long id, ClienteEntity cliente) {
        ClienteEntity existingCliente = clienteRepository.findById(id).orElseThrow(() -> new
    RuntimeException("Cliente no encontrado: " + id));
        existingCliente.setNombre(cliente.getNombre());
        existingCliente.setApellido(cliente.getApellido());
        existingCliente.setEmail(cliente.getEmail());
        existingCliente.setTelefono(cliente.getTelefono());
        existingCliente.setActivo(cliente.isActivo());
        return clienteRepository.save(existingCliente);
    }

    //Eliminar cliente (Borrado lógico
    public void deleteCliente(long id) {
        ClienteEntity existingCliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + id));
        existingCliente.setActivo(false);
        clienteRepository.save(existingCliente);
    }

}

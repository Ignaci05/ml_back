package com.example.mercaditolibre.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.mercaditolibre.dto.RegistroRequest;
import com.example.mercaditolibre.models.ClienteEntity;
import com.example.mercaditolibre.models.Rol;
import com.example.mercaditolibre.models.UsuarioEntity;
import com.example.mercaditolibre.repository.ClienteRepository;
import com.example.mercaditolibre.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, ClienteRepository clienteRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Almacenar los datos de cada cliente
    @Transactional
    public UsuarioEntity saveUsuario(RegistroRequest request) {
        // Verificar si el nombre de usuario ya existe
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsername(request.getUsername());
        usuario.setEmail(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setNombre(request.getNombre());

        // Asignacion de Rol
        Rol rol = Rol.ROLE_CLIENTE; // Asignar el rol de cliente por defecto
        if (request.getRole() != null && request.getRole().equalsIgnoreCase("ROLE_ADMIN")) {
            rol = Rol.ROLE_ADMIN; // Asignar el rol de administrador si se especifica
        }
        usuario.setRole(rol);
        UsuarioEntity savedUsuario = usuarioRepository.save(usuario);

        //Validación en caso de que el usuario sea un cliente, se guarda en la tabla cliente
        if (rol == Rol.ROLE_CLIENTE) {
            ClienteEntity cliente = new ClienteEntity();
            cliente.setNombre(request.getNombre());
            cliente.setApellido(request.getApellido() != null ? request.getApellido() : "");
            cliente.setEmail(request.getUsername());
            cliente.setTelefono(request.getTelefono() != null ? request.getTelefono() : "");
            cliente.setDireccion(request.getDireccion() != null ? request.getDireccion() : "");
            cliente.setActivo(true);
            clienteRepository.save(cliente);
        }
        
        return savedUsuario;
    }
}

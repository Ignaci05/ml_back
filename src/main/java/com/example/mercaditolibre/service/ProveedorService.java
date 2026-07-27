package com.example.mercaditolibre.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mercaditolibre.models.ProveedorEntity;
import com.example.mercaditolibre.repository.ProveedorRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ProveedorService {
    private final ProveedorRepository proveedorRepository;

    //Método para obtener todos los proveedores
    @Transactional(readOnly = true)
    public List<ProveedorEntity> getAllProveedores() {
        return proveedorRepository.findAll();
    }
    //Método para obtener todos los prooveedores activos
    @Transactional(readOnly = true)
    public List<ProveedorEntity> getAllProveedoresActivos() {
        return proveedorRepository.findAll().stream().filter(ProveedorEntity::isActivo).toList();
    }
    //Método para obtener un proveedor por su ID
    @Transactional(readOnly = true)
    public ProveedorEntity getProveedorById(long id) {
        return proveedorRepository.findById(id).orElseThrow(() -> new RuntimeException("Proveedor no encontrado: " + id));
    }
    //Método para crear un nuevo proveedor
    @Transactional
    public ProveedorEntity createProveedor(ProveedorEntity proveedor) {
        //Validación para los campos del proveedor
        if (proveedor.getNombre() == null || proveedor.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre del proveedor es obligatorio");       
        }
        if (proveedor.getEmail() == null || proveedor.getEmail().isEmpty()) {
            throw new RuntimeException("El email del proveedor es obligatorio");
        }
        if (proveedor.getTelefono() == null || proveedor.getTelefono().isEmpty()) {
            throw new RuntimeException("El teléfono del proveedor es obligatorio");
        }
        if (proveedor.getDireccion() == null || proveedor.getDireccion().isEmpty()) {
            throw new RuntimeException("El contacto del proveedor es obligatorio");
        }
        return proveedorRepository.save(proveedor);
    }

    //Método para actualizar un proveedor existente
    @Transactional
    public ProveedorEntity updateProveedor(long id, ProveedorEntity proveedor) {
        ProveedorEntity existingProveedor = proveedorRepository.findById(id).orElseThrow(() ->new RuntimeException("Proveedor no encontrado: " + id));
        existingProveedor.setNombre(proveedor.getNombre());
        existingProveedor.setEmail(proveedor.getEmail());
        existingProveedor.setTelefono(proveedor.getTelefono());
        existingProveedor.setDireccion(proveedor.getDireccion());
        return proveedorRepository.save(existingProveedor);
    }

    //Método para eliminar un proveedor por su ID (cambio a eliminación lógica)
    @Transactional
    public void deleteProveedor(long id) {
        ProveedorEntity existingProveedor = proveedorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Proveedor no encontrado: " + id));
        existingProveedor.setActivo(false);
        proveedorRepository.save(existingProveedor);    
    }




}

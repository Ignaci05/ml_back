package com.example.mercaditolibre.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;

import com.example.mercaditolibre.models.ProveedorEntity;
import com.example.mercaditolibre.service.ProveedorService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {
    private final ProveedorService proveedorService;

    // Obtener todos los proovedores
    @GetMapping
    public ResponseEntity<List<ProveedorEntity>> getAllProveedores() {
        return ResponseEntity.ok(proveedorService.getAllProveedores());
    }

    // Obtener un proveedor por su ID
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorEntity> getProveedorById(@PathVariable Long id) {
        try {
            ProveedorEntity proveedor = proveedorService.getProveedorById(id);
            return ResponseEntity.ok(proveedor);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Eliminar un proveedor por su id (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductoById(@PathVariable Long id) {
        try {
            proveedorService.deleteProveedor(id);
            return ResponseEntity.noContent().build(); // 204
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Agregar un producto
    @PostMapping("/crear")
    public ResponseEntity<ProveedorEntity> createProveedor(@RequestBody ProveedorEntity proveedor) {
        try {
            ProveedorEntity createdProveedor = proveedorService.createProveedor(proveedor);
            return new ResponseEntity<>(createdProveedor, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //Actualizar un producto existente
    @PutMapping("/{id}")
    public ResponseEntity <?> actualizarProveedor (@PathVariable Long id, @RequestBody ProveedorEntity proveedor){
        try {
            ProveedorEntity updatedProveedor = proveedorService.updateProveedor(id, proveedor);
            return ResponseEntity.ok(updatedProveedor);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}

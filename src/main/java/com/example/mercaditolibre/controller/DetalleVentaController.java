package com.example.mercaditolibre.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mercaditolibre.models.DetalleVentaEntity;
import com.example.mercaditolibre.service.DetalleVentaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/detalles-venta")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class DetalleVentaController {
    
    private final DetalleVentaService detalleVentaService;

    // Obtener todos los detalles
    @GetMapping
    public ResponseEntity<List<DetalleVentaEntity>> getAllDetalles() {
        return ResponseEntity.ok(detalleVentaService.getAllDetalles());
    }

    // Obtener un detalle por su ID
    @GetMapping("/{id}")
    public ResponseEntity<DetalleVentaEntity> getDetalleById(@PathVariable Long id) {
        try {
            DetalleVentaEntity detalle = detalleVentaService.getDetalleById(id);
            return ResponseEntity.ok(detalle);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Eliminar un detalle por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetalleById(@PathVariable Long id) {
        try {
            detalleVentaService.deleteDetalle(id);
            return ResponseEntity.noContent().build(); // 204
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Agregar un detalle
    @PostMapping("/crear")
    public ResponseEntity<DetalleVentaEntity> createDetalle(@RequestBody DetalleVentaEntity detalle) {
        try {
            DetalleVentaEntity createdDetalle = detalleVentaService.createDetalle(detalle);
            return new ResponseEntity<>(createdDetalle, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Actualizar un detalle
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDetalle(@PathVariable Long id, @RequestBody DetalleVentaEntity detalle) {
        try {
            DetalleVentaEntity updatedDetalle = detalleVentaService.updateDetalle(id, detalle);
            return ResponseEntity.ok(updatedDetalle);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
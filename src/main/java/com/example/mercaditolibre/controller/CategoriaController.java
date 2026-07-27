package com.example.mercaditolibre.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mercaditolibre.models.CategoriaEntity;
import com.example.mercaditolibre.service.CategoriaService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class CategoriaController {
    
    private final CategoriaService categoriaService;

    // Obtener todas las categorias
    @GetMapping
    public ResponseEntity<List<CategoriaEntity>> getAllCategorias() {
        return ResponseEntity.ok(categoriaService.getAllCategorias());
    }

    // Obtener una categoria por su ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaEntity> getCategoriaById(@PathVariable Long id) {
        try {
            CategoriaEntity categoria = categoriaService.getCategoriaById(id);
            return ResponseEntity.ok(categoria);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Eliminar una categoria por su id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoriaById(@PathVariable Long id) {
        try {
            categoriaService.deleteCategoria(id);
            return ResponseEntity.noContent().build(); // 204
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Agregar Categoria
    @PostMapping("/crear")
    public ResponseEntity<CategoriaEntity> createCategoria(@RequestBody CategoriaEntity categoria) {
        try {
            CategoriaEntity createdCategoria = categoriaService.createCategoria(categoria);
            return new ResponseEntity<>(createdCategoria, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Actualizar Categoria
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCategoria(@PathVariable Long id, @RequestBody CategoriaEntity categoria) {
        try {
            CategoriaEntity updatedCategoria = categoriaService.updateCategoria(id, categoria);
            return ResponseEntity.ok(updatedCategoria);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    

}
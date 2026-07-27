package com.example.mercaditolibre.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.example.mercaditolibre.repository.CategoriaRepository;
import com.example.mercaditolibre.models.CategoriaEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    //Método para obtener todas las categorías
    public List<CategoriaEntity> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    //Método para obtener todas las categorías activas
    public List<CategoriaEntity> getAllCategoriasActivas() {
        return categoriaRepository.findAll().stream().filter(CategoriaEntity::isActivo).toList();
    }

    //Método para obtener una categoría por su ID
    public CategoriaEntity getCategoriaById(long id) {
        return categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + id));
    }
    //Método para crear una nueva categoría
    public CategoriaEntity createCategoria(CategoriaEntity categoria) {
        //Validación para los campos de la categoría
        if (categoria.getNombre() == null || categoria.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre de la categoría es obligatorio");
        }
        return categoriaRepository.save(categoria);
    }
    //Método para eliminar categoria
    public void deleteCategoria(long id) {
        CategoriaEntity existingCategoria = categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + id));
        existingCategoria.setActivo(false);
        categoriaRepository.save(existingCategoria);
    }
    //Método para actualizar una categoría existente
    public CategoriaEntity updateCategoria(long id, CategoriaEntity categoria) {
        CategoriaEntity existingCategoria = categoriaRepository.findById(id).orElseThrow(() -> new
    RuntimeException("Cliente no encontrado: " + id));
        existingCategoria.setNombre(categoria.getNombre());
        existingCategoria.setActivo(categoria.isActivo());
        return categoriaRepository.save(existingCategoria);
        
    }
}

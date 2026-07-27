package com.example.mercaditolibre.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mercaditolibre.models.ProductoEntity;
import com.example.mercaditolibre.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository productoRepository;

    // Obtener todos los productos
    @Transactional(readOnly = true)
    public List<ProductoEntity> getAllProductos() {
        return productoRepository.findAll();
    }

    // Obtener todos los productos activos
    @Transactional(readOnly = true)
    public List<ProductoEntity> getAllProductosActivos() {
        return productoRepository.findAll().stream().filter(ProductoEntity::isActivo).toList();
    }

    // Obtener un producto por su ID
    @Transactional(readOnly = true)
    public ProductoEntity getProductoById(long id) {
        return productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id));
    }

    // Metodo para crear un nuevo producto
    @Transactional
    public ProductoEntity createProducto(ProductoEntity producto) {
        //Validación para los compos del producto
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre del producto es obligatorio");
        }
        if (producto.getPrecio() <= 0) {
            throw new RuntimeException("El precio del producto debe ser mayor a cero");
        }
        if (producto.getStock() < 0) {
            throw new RuntimeException("El stock del producto no puede ser negativo");
        }
        if (producto.getDescripcion() == null) {
            producto.setDescripcion("");
        }
        return productoRepository.save(producto);
    }

    /* Metodo para actualizar un producto existente
    @Transactional
    public ProductoEntity updateProducto(long id, ProductoEntity producto) {
        ProductoEntity existingProducto = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado: "+ id));
        existingProducto.setNombre(producto.getNombre());
        existingProducto.setDescripcion(producto.getDescripcion());
        existingProducto.setPrecio(producto.getPrecio());
        existingProducto.setStock(producto.getStock());
        existingProducto.setImagenUrl(producto.getImagenUrl());
        return productoRepository.save(existingProducto);
    
    }
    */
    //Método No.2 Para actualizar producto
    @Transactional
    public ProductoEntity updateProducto (@NonNull Long id, @NonNull ProductoEntity producto){
        //Validación si existe el producto
        ProductoEntity productoExistente = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id));
        //Validar los campos del producto
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre del producto es obligatorio");
        }
        if (producto.getPrecio() <= 0) {
            throw new RuntimeException("El precio del producto debe ser mayor a cero");
        }
        if (producto.getStock() < 0) {
            throw new RuntimeException("El stock del producto no puede ser negativo");
        }
        if (producto.getDescripcion() == null) {
            producto.setDescripcion("");
        }
        
        BeanUtils.copyProperties(producto, productoExistente, "id");
        return productoRepository.save(Objects.requireNonNull(productoExistente));
    }


    // Metodo para eliminar un producto por su ID (cambio a eliminación lógica)
    @Transactional
    public void deleteProducto(long id) {
        ProductoEntity existingProducto = productoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id));
        existingProducto.setActivo(false);
        productoRepository.save(existingProducto);      
    }

}

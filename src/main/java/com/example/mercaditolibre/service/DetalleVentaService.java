package com.example.mercaditolibre.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.mercaditolibre.models.DetalleVentaEntity;
import com.example.mercaditolibre.repository.DetalleVentaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetalleVentaService {
    
    private final DetalleVentaRepository detalleVentaRepository;

    // Obtener todos los detalles de venta
    public List<DetalleVentaEntity> getAllDetalles() {
        return detalleVentaRepository.findAll();
    }

    // Obtener un detalle por su ID
    public DetalleVentaEntity getDetalleById(Long id) {
        return detalleVentaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Detalle de venta no encontrado con ID: " + id));
    }

    // Crear un nuevo detalle de venta
    public DetalleVentaEntity createDetalle(DetalleVentaEntity detalle) {
        // Validaciones básicas similares a las de VentasService
        if (detalle.getCantidad() <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }
        if (detalle.getPrecio_unitario() == null || detalle.getPrecio_unitario() <= 0) {
            throw new RuntimeException("El precio unitario es obligatorio y debe ser mayor a 0");
        }
        // El subtotal idealmente debería calcularse aquí o venir validado desde el frontend
        if (detalle.getSubtotal() == null || detalle.getSubtotal() <= 0) {
            throw new RuntimeException("El subtotal es obligatorio y debe ser mayor a 0");
        }
        
        return detalleVentaRepository.save(detalle);
    }

    // Actualizar un detalle de venta existente
    public DetalleVentaEntity updateDetalle(Long id, DetalleVentaEntity detalle) {
        DetalleVentaEntity existingDetalle = getDetalleById(id);
        
        existingDetalle.setCantidad(detalle.getCantidad());
        existingDetalle.setPrecio_unitario(detalle.getPrecio_unitario());
        existingDetalle.setSubtotal(detalle.getSubtotal());
        
        // Actualizar relaciones (asegúrate de que vengan en el JSON si las vas a actualizar)
        if (detalle.getProducto() != null) {
            existingDetalle.setProducto(detalle.getProducto());
        }
        if (detalle.getVenta() != null) {
            existingDetalle.setVenta(detalle.getVenta());
        }

        return detalleVentaRepository.save(existingDetalle);
    }

    // Eliminar un detalle de venta
    public void deleteDetalle(Long id) {
        DetalleVentaEntity existingDetalle = getDetalleById(id);
        detalleVentaRepository.delete(existingDetalle);
    }
}
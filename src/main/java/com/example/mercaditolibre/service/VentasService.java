package com.example.mercaditolibre.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.example.mercaditolibre.repository.ClienteRepository;
import com.example.mercaditolibre.repository.ProductoRepository;
import com.example.mercaditolibre.repository.VentasRepository;

import jakarta.transaction.Transactional;

import com.example.mercaditolibre.models.DetalleVentaEntity;
import com.example.mercaditolibre.models.ProductoEntity;
import com.example.mercaditolibre.models.VentasEntity;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentasService {
    private final VentasRepository ventasRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    public List<VentasEntity> getAllVentas() {
        return ventasRepository.findAll();
    }

    public VentasEntity getVentaById(long id) {
        return ventasRepository.findById(id).orElseThrow(() -> new RuntimeException("Venta no encontrada: " + id));
    }

    public List<VentasEntity> getVentaByEmail(String email) {
        return ventasRepository.findAll();
    }

    @Transactional
    public VentasEntity createVenta(VentasEntity ventaRequest, String email) {
        ventaRequest.setFechaVenta(LocalDateTime.now());
        ventaRequest.setEstado("PENDIENTE");

        double total = 0.0;

        if (ventaRequest.getDetallesVenta() != null && !ventaRequest.getDetallesVenta().isEmpty()) {
            for (DetalleVentaEntity detalle : ventaRequest.getDetallesVenta()) {
                ProductoEntity producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalle.getProducto().getId()));

                if (producto.getStock() < detalle.getCantidad()) {
                    throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
                }

                producto.setStock(producto.getStock() - detalle.getCantidad());
                productoRepository.save(producto);

                detalle.setPrecio_unitario(producto.getPrecio());
                detalle.setSubtotal(producto.getPrecio() * detalle.getCantidad());
                detalle.setVenta(ventaRequest);

                total += detalle.getSubtotal();
            }
        }

        ventaRequest.setTotal(total);
        return ventasRepository.save(ventaRequest);
    }

    @Transactional
    public VentasEntity confirmarPago(Long idVenta) {
        VentasEntity venta = ventasRepository.findById(idVenta)
            .orElseThrow(() -> new RuntimeException("Venta no encontrada: " + idVenta));

        if (!"PENDIENTE".equals(venta.getEstado())) {
            throw new RuntimeException("La venta ya fue procesada");
        }

        venta.setEstado("COMPLETADO");
        return ventasRepository.save(venta);
    }

    @Transactional
    public VentasEntity updateVenta(long id, VentasEntity venta) {
        VentasEntity existingVenta = ventasRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Venta no encontrada: " + id));

        if (venta.getFechaVenta() != null) {
            existingVenta.setFechaVenta(venta.getFechaVenta());
        }
        if (venta.getTotal() > 0) {
            existingVenta.setTotal(venta.getTotal());
        }
        if (venta.getTipoPago() != null) {
            existingVenta.setTipoPago(venta.getTipoPago());
        }
        if (venta.getEstado() != null) {
            existingVenta.setEstado(venta.getEstado());
        }

        return ventasRepository.save(existingVenta);
    }
}

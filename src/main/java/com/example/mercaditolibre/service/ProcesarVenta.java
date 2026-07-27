package com.example.mercaditolibre.service;

import org.springframework.stereotype.Service;

import com.example.mercaditolibre.models.DetalleVentaEntity;
import com.example.mercaditolibre.models.ProductoEntity;
import com.example.mercaditolibre.models.VentasEntity;
import com.example.mercaditolibre.repository.ProductoRepository;
import com.example.mercaditolibre.repository.VentasRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcesarVenta {
    //Esta clase sirve para procesar venta y detalleventa

    private final VentasRepository ventaRepository;
    private final ProductoRepository productoRepository;

    //Método combinado para procesar uuna venta 
    @Transactional
    public  VentasEntity ProcessVenta  (VentasEntity ventaRequest){
        ventaRequest.setFechaVenta(java.time.LocalDateTime.now());
        ventaRequest.setTipoPago("Tarjeta");

        //Calcular totales y descontar Stock
        double  total = 0.0;
        for (DetalleVentaEntity detalle : ventaRequest.getDetallesVenta()){
            ProductoEntity p = productoRepository.findById(detalle.getProducto().getId())
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalle.getProducto().getId()));
            p.setStock(p.getStock() - detalle.getCantidad());

            detalle.setPrecio_unitario(p.getPrecio());
            detalle.setSubtotal(p.getPrecio()*detalle.getCantidad());
            detalle.setVenta(ventaRequest);
            total += detalle.getSubtotal();
            
        }
        ventaRequest.setTotal(total);
        return  ventaRepository.save(ventaRequest);

    }
}

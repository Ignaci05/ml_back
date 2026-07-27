package com.example.mercaditolibre.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mercaditolibre.models.VentasEntity;
import com.example.mercaditolibre.service.ProcesarVenta;
import com.example.mercaditolibre.service.VentasService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class VentasController {
    
    private final VentasService ventasService;
    private final ProcesarVenta procesarVenta;

    // Obtener todas las ventas
    @GetMapping
    public ResponseEntity<List<VentasEntity>> getAllVentas() {
        return ResponseEntity.ok(ventasService.getAllVentas());
    }

    // Obtener una venta por su ID
    @GetMapping("/{id}")
    public ResponseEntity<VentasEntity> getVentaById(@PathVariable Long id) {
        try {
            VentasEntity venta = ventasService.getVentaById(id);
            return ResponseEntity.ok(venta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Agregar Venta con email del usuario autenticado
    @PostMapping("/crear")
    public ResponseEntity<?> createVenta(@RequestBody VentasEntity venta, Principal principal) {
        try {
            String email = principal.getName();
            VentasEntity createdVenta = ventasService.createVenta(venta, email);
            return new ResponseEntity<>(createdVenta, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //Método para consultar venta por id (Mis Ventas)
    @GetMapping("/mis-compras")
    public ResponseEntity<List<VentasEntity>> listarMisCompras(Principal principal) {
        String email = principal.getName();
        return ResponseEntity.ok(ventasService.getVentaByEmail(email));
    }

    // Actualizar Venta
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarVenta(@PathVariable Long id, @RequestBody VentasEntity venta) {
        try {
            VentasEntity updatedVenta = ventasService.updateVenta(id, venta);
            return ResponseEntity.ok(updatedVenta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Procesar una Venta
    @PostMapping("/")
    public ResponseEntity crearVenta (@RequestBody VentasEntity venta){
        return  ResponseEntity.ok(procesarVenta.ProcessVenta(venta));
    }

    //Confirmar pago de una venta
    @PostMapping("/confirmar-pago/{id}")
    public ResponseEntity<?> confirmarPago(@PathVariable Long id) {
        try {
            VentasEntity venta = ventasService.confirmarPago(id);
            return ResponseEntity.ok(venta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
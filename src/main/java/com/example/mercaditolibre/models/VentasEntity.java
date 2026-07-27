package com.example.mercaditolibre.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "ventas")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class VentasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "producto_id", nullable = false)
    private long productoId;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "fecha_venta", nullable = false)
    private LocalDateTime fechaVenta;

    @Column (name = "total", nullable = false)
    private double total;

    @Column(name= "tipo_pago", nullable = false)
    private String tipoPago;

    @Column(name = "estado", nullable = false)
    private String estado = "PENDIENTE";


    // Relación FK
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "producto_id")
    private List<DetalleVentaEntity> detallesVenta = new ArrayList<>();
}

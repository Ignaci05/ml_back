package com.example.mercaditolibre.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Table(name = "clientes")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder

public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "telefono", nullable = true, length = 20)
    private String telefono;

    @Column (name = "activo", nullable = false)
    private boolean activo;

    @Column(name = "direccion", nullable = true, length = 255)
    private String direccion;
}

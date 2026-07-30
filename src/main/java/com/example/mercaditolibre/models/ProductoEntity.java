package com.example.mercaditolibre.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "productos")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder

public class ProductoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", nullable = true)
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private double precio;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "imagen_url", nullable = true, columnDefinition = "TEXT")
    private String imagenUrl;

    @Column (name = "activo", nullable = false) 
    private boolean activo;

    //Relaciones de FK
    //Categoría
    @ManyToOne (fetch=FetchType.EAGER)
    @JoinColumn (name = "categoria_id")
    private CategoriaEntity categoria;
    //Productos
    @ManyToOne (fetch=FetchType.EAGER)
    @JoinColumn (name = "proveedor_id")
    private ProveedorEntity proveedor;
    




    

}

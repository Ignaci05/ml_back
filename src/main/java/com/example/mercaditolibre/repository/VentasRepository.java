package com.example.mercaditolibre.repository;

import org.springframework.stereotype.Repository;
import com.example.mercaditolibre.models.VentasEntity;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface VentasRepository extends JpaRepository<VentasEntity, Long> {

}

package com.example.cuotaservice.repository;

import com.example.cuotaservice.entity.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Integer> {

    // cuotas asociadas a un estudiante

}

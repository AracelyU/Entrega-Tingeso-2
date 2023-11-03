package com.example.cuotaservice.repository;

import com.example.cuotaservice.entity.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Integer> {

    // cuotas asociadas a un estudiante sin importar el tipo
    @Query("Select c from Cuota c where c.estudiante_id = :id")
    List<Cuota> findByEstudiante_id(@Param("id") int id);

}

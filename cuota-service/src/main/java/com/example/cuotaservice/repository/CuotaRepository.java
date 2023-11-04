package com.example.cuotaservice.repository;

import com.example.cuotaservice.entity.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Integer> {

    // encontrar cuota por su id
    @Query("Select c from Cuota c where c.id = :id")
    Cuota findById(@Param("id") int id);

    // cuotas asociadas a un estudiante sin importar el tipo
    @Query("Select c from Cuota c where c.estudiante_id = :id")
    List<Cuota> findByEstudiante_id(@Param("id") int id);

    // cuotas pendientes asociadas a un estudiante
    @Query("Select c from Cuota c where c.estudiante_id = :id and c.estado_pago = 'pendiente'")
    List<Cuota> findByEstudiante_idPendiente(@Param("id") int id);

}

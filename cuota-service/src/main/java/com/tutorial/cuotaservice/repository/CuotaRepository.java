package com.tutorial.cuotaservice.repository;

import com.tutorial.cuotaservice.entity.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Integer> {

    // cuotas asociadas a un estudiante
    List<Cuota> findByStudentId(int studentId);
}

package com.tutorial.examservice.repository;

import com.tutorial.examservice.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

    // obtener el promedio de los ultimos examenes de un estudiante
    @Query(value = "SELECT avg(e.puntaje_examen) FROM Exam e WHERE e.rut = :rut AND e.fecha_examen = (SELECT MAX(e.fecha_examen) FROM Exam e WHERE e.rut = :rut)")
    Float findPuntajePromedio(@Param("rut") String rut);

    // obtener numero de pruebas que dio un estudiante durante todo el preuniversitario
    @Query("SELECT COUNT(*) FROM Exam e WHERE e.rut = :rut")
    Integer findNumeroPruebas(@Param("rut") String rut);


}

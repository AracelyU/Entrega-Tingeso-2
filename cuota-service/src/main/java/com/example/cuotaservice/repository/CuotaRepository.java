package com.example.cuotaservice.repository;

import com.example.cuotaservice.entity.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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

    // numero de cuotas pagadas
    @Query("SELECT COUNT(*) FROM Cuota c WHERE c.estado_pago = 'pagado' AND c.estudiante_id = :id_estudiante")
    Integer findCuotasPagadas(@Param("id_estudiante") int id_estudiante);

    // numero de cuotas que aun no se pagan
    @Query("SELECT COUNT(*) FROM Cuota c WHERE c.estado_pago = 'pendiente' AND c.estudiante_id = :id_estudiante")
    Integer findCuotasPorPagar(@Param("id_estudiante") int id_estudiante);


    // monto pagado
    @Query("SELECT SUM(c.monto) FROM Cuota c WHERE c.estado_pago = 'pagado' AND c.estudiante_id = :id_estudiante")
    Float findSaldoPagado(@Param("id_estudiante") int id_estudiante);

    // monto total que aún queda por pagar
    @Query("SELECT SUM(c.monto) FROM Cuota c WHERE c.estado_pago = 'pendiente' AND c.estudiante_id = :id_estudiante")
    Float findSaldoPorPagar(@Param("id_estudiante") int id_estudiante);

    // obtener cuotas pagas en orden descendente
    @Query("SELECT c FROM Cuota c WHERE c.estudiante_id = :id_estudiante AND c.estado_pago = 'pagado' ORDER BY c.fecha_pago DESC ")
    List<Cuota> findUltimoPago(@Param("id_estudiante") int id_estudiante);


}

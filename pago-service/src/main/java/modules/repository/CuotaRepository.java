package modules.repository;

import modules.entity.CuotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuotaRepository extends JpaRepository<CuotaEntity, Long> {


    // encontrar cuotas por id_estudiante
    @Query("SELECT c FROM CuotaEntity c WHERE c.estudiante_id = :id_estudiante")
    List<CuotaEntity> findCuotaEntitiesByEstudiante_id(@Param("id_estudiante") Long id_estudiante);



}

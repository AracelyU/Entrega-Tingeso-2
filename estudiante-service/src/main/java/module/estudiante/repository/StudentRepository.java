package module.estudiante.repository;

import module.estudiante.entity.StudentEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {


    @Query("select s from StudentEntity s where s.rut = :rut")
    StudentEntity findByRut(@Param("rut") String rut);


}

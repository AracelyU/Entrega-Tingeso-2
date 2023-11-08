package com.tutorial.studentservice.repository;

import com.tutorial.studentservice.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("Select s from Student s where s.rut = :rut")
    Student findByRut(@Param("rut") String rut);

    @Query("Select s from Student s where s.id = :id")
    Student findById(@Param("id") int id);

}

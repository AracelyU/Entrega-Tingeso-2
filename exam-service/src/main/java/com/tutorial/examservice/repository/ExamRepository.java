package com.tutorial.examservice.repository;

import com.tutorial.examservice.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

    List<Exam> findByStudentId(int studentId);
}

package com.tutorial.examservice.repository;

import com.tutorial.examservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByStudentId(int studentId);
}

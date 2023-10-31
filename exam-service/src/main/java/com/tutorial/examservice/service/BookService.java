package com.tutorial.examservice.service;

import com.tutorial.examservice.entity.Book;
import com.tutorial.examservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getBookById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book save(Book book) {
        Book bookNew = bookRepository.save(book);
        return bookNew;
    }

    public List<Book> byStudentId(int studentId) {
        return bookRepository.findByStudentId(studentId);
    }
}

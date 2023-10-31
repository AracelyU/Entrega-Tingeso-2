package com.tutorial.studentservice.service;

import com.tutorial.studentservice.entity.Student;
import com.tutorial.studentservice.model.Exam;
import com.tutorial.studentservice.model.Cuota;
import com.tutorial.studentservice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    RestTemplate restTemplate;

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student getStudentById(int id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student save(Student student) {
        Student studentNew = studentRepository.save(student);
        return studentNew;
    }

    public List<Cuota> getBooks(int studentId) {
        List<Cuota> books = restTemplate.getForObject("http://book-service/book/bystudent/" + studentId, List.class);
        return books;
    }

    public List<Exam> getPets(int studentId) {
        List<Exam> pets = restTemplate.getForObject("http://pet-service/pet/bystudent/" + studentId, List.class);
        return pets;
    }

    public Cuota saveBook(int studentId, Cuota book) {
        book.setStudentId(studentId);
        HttpEntity<Cuota> request = new HttpEntity<Cuota>(book);
        Cuota bookNew = restTemplate.postForObject("http://book-service/book", request, Cuota.class);
        return bookNew;
    }

    public Exam savePet(int studentId, Exam pet) {
        pet.setStudentId(studentId);
        HttpEntity<Exam> request = new HttpEntity<Exam>(pet);
        Exam petNew = restTemplate.postForObject("http://pet-service/pet", request, Exam.class);
        return petNew;
    }
}

package com.tutorial.studentservice.service;

import com.tutorial.studentservice.entity.Student;
import com.tutorial.studentservice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    // obtener lista de estudiantes
    public List<Student> obtenerEstudiantes() {
        return studentRepository.findAll();
    }

    // guardar estudiante
    public Student guardarEstudiante(Student student) {
        return studentRepository.save(student);
    }

    // obtener al estudiante por su rut
    public Student obtenerEstudiantePorRut(String rut){ return studentRepository.findByRut(rut); }


    // obtener al estudiante por id
    public Student obtenerEstudiantePorId(int id) {
        return studentRepository.findById(id);
    }

    // obtener al estudiante por su rut


    /*
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

     */
}

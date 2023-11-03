package com.tutorial.studentservice.controller;

import com.tutorial.studentservice.entity.Student;
import com.tutorial.studentservice.model.Exam;
import com.tutorial.studentservice.model.Cuota;
import com.tutorial.studentservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;


    // listar estudiantes
    @GetMapping
    public ResponseEntity<List<Student>> getAll() {
        List<Student> students = studentService.obtenerEstudiantes();
        if(students.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(students);
    }

    // guardar estudiante
    @PostMapping()
    public ResponseEntity<Student> save(@RequestBody Student student) {
        Student studentNew = studentService.guardarEstudiante(student);
        return ResponseEntity.ok(studentNew);
    }

    // verificar si un rut no esta repetido
    @PostMapping("/rut")
    public ResponseEntity<Student> findRut(@RequestBody String rut){
        Student s = studentService.obtenerEstudiantePorRut(rut);
        if(s == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(s);
    }

    // encontrar estudiante por su id
    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable("id") int id) {
        Student student = studentService.obtenerEstudiantePorId(id);
        if(student == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(student);
    }



/*

    @GetMapping("/books/{studentId}")
    public ResponseEntity<List<Cuota>> getBooks(@PathVariable("studentId") int studentId) {
        Student student = studentService.getStudentById(studentId);
        if(student == null)
            return ResponseEntity.notFound().build();
        List<Cuota> books = studentService.getBooks(studentId);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/pets/{studentId}")
    public ResponseEntity<List<Exam>> getPets(@PathVariable("studentId") int studentId) {
        Student student = studentService.getStudentById(studentId);
        if(student == null)
            return ResponseEntity.notFound().build();
        List<Exam> pets = studentService.getPets(studentId);
        return ResponseEntity.ok(pets);
    }

 */

    /*
    @PostMapping("/savebook/{studentId}")
    public ResponseEntity<Cuota> saveBook(@PathVariable("studentId") int studentId, @RequestBody Cuota book) {
        if(studentService.getStudentById(studentId) == null)
            return ResponseEntity.notFound().build();
        Cuota bookNew = studentService.saveBook(studentId, book);
        return ResponseEntity.ok(book);
    }

    @PostMapping("/savepet/{studentId}")
    public ResponseEntity<Exam> savePet(@PathVariable("studentId") int studentId, @RequestBody Exam pet) {
        if(studentService.getStudentById(studentId) == null)
            return ResponseEntity.notFound().build();
        Exam petNew = studentService.savePet(studentId, pet);
        return ResponseEntity.ok(pet);
    }

     */

}

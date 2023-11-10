package com.tutorial.studentservice.controller;

import com.tutorial.studentservice.entity.Student;
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
    @GetMapping("/rut/{rut}")
    public ResponseEntity<Student> findRut(@PathVariable("rut") String rut){
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




}

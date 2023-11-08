package module.estudiante.controller;

import module.estudiante.entity.StudentEntity;
import module.estudiante.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;


    // muestra los estudiantes
    @GetMapping
    public ResponseEntity<List<StudentEntity>> getAll(){
        List<StudentEntity> students = studentService.obtenerEstudiantes();
        if(students.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(students);
    }

    // guarda un estudiante
    @PostMapping()
    public ResponseEntity<StudentEntity> save(@RequestBody StudentEntity student) {
        StudentEntity studentNew = studentService.guardarEstudiante(student);
        return ResponseEntity.ok(studentNew);
    }




}


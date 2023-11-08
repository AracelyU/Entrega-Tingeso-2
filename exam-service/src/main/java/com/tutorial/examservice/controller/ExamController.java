package com.tutorial.examservice.controller;

import com.tutorial.examservice.entity.Exam;
import com.tutorial.examservice.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/examen")
public class ExamController {

    @Autowired
    ExamService examService;


    // obtener todos los examenes
    @GetMapping
    public ResponseEntity<List<Exam>> obtenerExamenes(){
        List<Exam> examenes = examService.obtenerExamenes();
        if(examenes == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(examenes);
    }


    // guarda un examen
    @PostMapping()
    public ResponseEntity<Exam> save(@RequestBody Exam examen) {
        Exam examenNew = examService.guardarExamen(examen);
        return ResponseEntity.ok(examenNew);
    }


    @PostMapping("/cargar")
    public ResponseEntity<String> cargarArchivo(@RequestParam("file") MultipartFile file) {
        examService.guardar(file);
        String filename = file.getOriginalFilename();
        examService.leerContenido(filename);
        return ResponseEntity.ok(filename);
    }



}

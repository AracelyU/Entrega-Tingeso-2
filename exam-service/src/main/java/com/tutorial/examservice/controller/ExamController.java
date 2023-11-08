package com.tutorial.examservice.controller;

import com.tutorial.examservice.entity.Exam;
import com.tutorial.examservice.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/examen")
public class ExamController {

    @Autowired
    ExamService examService;

    @GetMapping
    public ResponseEntity<List<Exam>> obtenerExamenes(){
        List<Exam> examenes = examService.obtenerExamenes();
        if(examenes == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(examenes);
    }

    @PostMapping("cargar")
    public ResponseEntity<List<Exam>> cargarArchivo(@RequestParam("file") MultipartFile file) {
        String ruta = examService.guardar(file);
        String filename = file.getOriginalFilename();
        System.out.println(ruta);
        List<Exam> examenes = examService.leerCsv(filename);
        if(examenes == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(examenes);
    }



}

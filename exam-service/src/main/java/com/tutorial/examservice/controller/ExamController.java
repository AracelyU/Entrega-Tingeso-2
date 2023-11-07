package com.tutorial.examservice.controller;

import com.tutorial.examservice.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/examen")
public class ExamController {

    @Autowired
    ExamService examService;

    @PostMapping
    public ResponseEntity<String> cargarArchivo(@RequestParam("file") MultipartFile file) {
        examService.guardar(file);
        String filename = file.getOriginalFilename();
        //examService.leerCsv(filename);
        return ResponseEntity.ok("Archivo cargado correctamente");

    }



}

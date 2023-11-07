package com.tutorial.examservice.controller;

import com.tutorial.examservice.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/examen")
public class ExamController {

    @Autowired
    ExamService examService;

    @PostMapping
    public void guardarData(@RequestParam("file") MultipartFile file) {
        examService.guardar(file);
        examService.leerCsv(file.getName());
    }


}

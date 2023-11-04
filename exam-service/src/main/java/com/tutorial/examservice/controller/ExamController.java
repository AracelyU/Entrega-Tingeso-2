package com.tutorial.examservice.controller;
import org.springframework.web.multipart.MultipartFile;
import com.tutorial.examservice.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    ExamService examService;


    @PostMapping
    public void guardarData(@RequestParam("file") MultipartFile file, RedirectAttributes ms) throws FileNotFoundException, ParseException {
        examService.guardar(file);
        examService.leerCsv(file.getName());
    }


}

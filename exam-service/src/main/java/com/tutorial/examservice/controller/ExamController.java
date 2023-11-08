package com.tutorial.examservice.controller;

import com.tutorial.examservice.entity.Exam;
import com.tutorial.examservice.entity.GenerarReporte;
import com.tutorial.examservice.model.Student;
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

    // importar archivo csv
    @PostMapping("/cargar")
    public ResponseEntity<String> cargarArchivo(@RequestParam("file") MultipartFile file) {
        examService.guardar(file);
        String filename = file.getOriginalFilename();
        examService.leerContenido(filename);
        return ResponseEntity.ok(filename);
    }

    // obtener número de examenes rendidos
    @GetMapping("/rendidos/{rut}")
    public ResponseEntity<Integer> saldoPagado(@PathVariable String rut){
        Integer nro_examenes = examService.numeroPruebas(rut);
        return ResponseEntity.ok(nro_examenes);
    }

    // obtener promedio de examenes
    @GetMapping("/promedio/{rut}")
    public ResponseEntity<Float> promedioExamenes(@PathVariable String rut){
        Float promedio = examService.obtenerPromedio(rut);
        return ResponseEntity.ok(promedio);
    }


    // obtiene toda la información para generar un reporte
    @GetMapping("/generarReporte/{id}")
    public ResponseEntity<GenerarReporte> generarReporte(@PathVariable int id){
        GenerarReporte g = new GenerarReporte();
        Student s = examService.getStudentById(id).getBody();
        g.setRut(s.getRut());
        g.setNombre(s.getNombre_estudiante() + " " + s.getApellido_estudiante());
        Integer nro_examenes_rendidos = examService.numeroPruebas(s.getRut());
        g.setNro_examenes(nro_examenes_rendidos);
        Float promedio = examService.obtenerPromedio(s.getRut());
        g.setPromedio(promedio);
        Float monto_pagado = examService.getMontoPagado(s.getId()).getBody();
        Float monto_por_pagar = examService.getMontoPagado(s.getId()).getBody();
        Float monto_total = monto_pagado + monto_por_pagar;
        g.setMonto_total(monto_total);
        Integer nro_cuotas = examService.getNumeroCuotas(id).getBody();
        g.setNro_cuotas(nro_cuotas);
        Integer nro_cuotas_pagadas = examService.getNumeroCuotasPagadas(id).getBody();
        g.setNro_cuotas_pagadas(nro_cuotas_pagadas);
        g.setPagado(monto_pagado);
        g.setPor_pagar(monto_por_pagar);

        // fecha último pago
        // cuotas atrasadas






        return ResponseEntity.ok(g);

    }





}

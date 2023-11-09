package com.tutorial.examservice.controller;

import com.tutorial.examservice.entity.Exam;
import com.tutorial.examservice.entity.GenerarReporte;
import com.tutorial.examservice.model.Student;
import com.tutorial.examservice.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
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

    // obtener estudiante
    @GetMapping("student/{id}")
    public ResponseEntity<Student> obtenerEstudiante(@PathVariable int id){
        Student s = examService.getStudentById(id);
        if(s == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(s);
    }


    // obtiene toda la información para generar un reporte
    @GetMapping("/generarReporte/{id}")
    public ResponseEntity<GenerarReporte> generarReporte(@PathVariable int id){
        GenerarReporte g = new GenerarReporte();
        Student s = examService.getStudentById(id);
        g.setRut(s.getRut());
        System.out.println("rut: " + g.getRut());
        String nombre = s.getNombre_estudiante();
        String apellido = s.getApellido_estudiante();
        g.setNombre(nombre);
        g.setApellido(apellido);
        System.out.println("nombre: " + g.getNombre());
        Integer nro_examenes_rendidos = examService.numeroPruebas(s.getRut());
        g.setNro_examenes(nro_examenes_rendidos);
        System.out.println("nro examenes: " + g.getNro_examenes());
        Float promedio = examService.obtenerPromedio(s.getRut());
        g.setPromedio(promedio);
        System.out.println("promedio: " + g.getPromedio());
        Float monto_pagado = examService.getMontoPagado(s.getId());
        System.out.println("monto_pagado: " + monto_pagado);
        Float monto_por_pagar = examService.getMontoPorPagar(s.getId());
        System.out.println("monto_por_pagar: " + monto_por_pagar);
        Float monto_total = monto_pagado + monto_por_pagar;
        g.setMonto_total(monto_total);
        System.out.println("monto_total: " + monto_total);
        Integer nro_cuotas = examService.getNumeroCuotasPorPagar(id) + examService.getNumeroCuotasPagadas(id);
        g.setNro_cuotas(nro_cuotas);
        System.out.println("nro_cuotas" + g.getNro_cuotas());
        Integer nro_cuotas_pagadas = examService.getNumeroCuotasPagadas(id);
        g.setNro_cuotas_pagadas(nro_cuotas_pagadas);
        System.out.println("nro_cuotas_pagadas: " + g.getNro_cuotas_pagadas());
        g.setPagado(monto_pagado);
        g.setPor_pagar(monto_por_pagar);

        if(nro_cuotas != 0) {
            String tipo_pago = examService.getTipoPago(s.getId());
            g.setTipo_pago(tipo_pago);
            System.out.println("tipo_pago: " + g.getTipo_pago());
            Integer nro_cuotas_atrasadas = examService.getNumeroCuotasAtrasadas(s.getId());
            g.setNro_cuotas_atraso(nro_cuotas_atrasadas);
            System.out.println("numero_cuotas_atrasadas: " + g.getNro_cuotas_atraso());

            if (nro_cuotas_pagadas != 0) {
                LocalDateTime ultimo_pago = examService.getUltimoPago(s.getId());
                g.setUltimo_pago(ultimo_pago);
            } else {
                g.setUltimo_pago(null);
            }
            System.out.println("ultimo_pago: " + g.getUltimo_pago());
        }else{
            g.setTipo_pago(null);
            g.setNro_cuotas_atraso(null);
            g.setUltimo_pago(null);
        }


            return ResponseEntity.ok(g);

    }


    // aplicar descuento promedio
    @GetMapping("/descuentoPromedio")
    public ResponseEntity<String> aplicarDescuento(){
        ResponseEntity<String> response = examService.aplicarDescuentoPromedio();
        return response;
    }

    // aplicar intereses por atraso de cuotas
    @GetMapping("/interesAtraso")
    public ResponseEntity<String> aplicarInteres(){
        ResponseEntity<String> response = examService.aplicarInteresAtrasoCuotas();
        return response;
    }




}

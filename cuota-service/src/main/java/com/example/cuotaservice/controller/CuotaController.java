package com.example.cuotaservice.controller;

import com.example.cuotaservice.entity.Cuota;
import com.example.cuotaservice.entity.GenerarCuota;
import com.example.cuotaservice.model.Student;
import com.example.cuotaservice.service.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuota")
public class CuotaController {

    @Autowired
    CuotaService cuotaService;

    // mostrar todas las cuotas
    @GetMapping
    public ResponseEntity<List<Cuota>> getAll() {
        List<Cuota> cuotas = cuotaService.obtenerCuotas();
        if(cuotas.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(cuotas);
    }

    /*

    // obtener cuota por estudiante
    @GetMapping("/bystudent/{studentId}")
    public ResponseEntity<List<Cuota>> getByStudentId(@PathVariable("studentId") int studentId) {
        List<Cuota> books = cuotaService.obtenerCuotasPorEstudiante_Id(studentId);
        return ResponseEntity.ok(books);
    }

     */

    /* obtener estudiante
    */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<Student> getByStudentId(@PathVariable("studentId") int studentId) {
        Student s = cuotaService.obtenerEstudiantePorId(studentId);
        return ResponseEntity.ok(s);
    }

    // generar pago
    @PostMapping("/generatepago")
    public ResponseEntity<GenerarCuota> createdPayment(@RequestBody GenerarCuota generarCuota){
        String mensaje = cuotaService.verificarGuardarPago(generarCuota.getId_estudiante(), generarCuota.getNum_cuotas(), generarCuota.getTipo_pago());
        if (!mensaje.equals("El pago se generó con éxito.")) {
            // Devuelve una respuesta de error con un código de estado 400 Bad Request
            return ResponseEntity.noContent().build();
        }
        System.out.println(mensaje);

        // generar las cuotas
        //cuotaService.guardarPago(student_id, num_cuotas, tipo_pago);
        return ResponseEntity.ok(generarCuota);
    }


    // crear pago

    /*
    @GetMapping("/{id}")
    public ResponseEntity<Cuota> getById(@PathVariable("id") int id) {
        Cuota book = bookService.getBookById(id);
        if(book == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(book);
    }

    @PostMapping()
    public ResponseEntity<Cuota> save(@RequestBody Cuota book) {
        Cuota bookNew = bookService.save(book);
        return ResponseEntity.ok(bookNew);
    }

    @GetMapping("/bystudent/{studentId}")
    public ResponseEntity<List<Cuota>> getByStudentId(@PathVariable("studentId") int studentId) {
        List<Cuota> books = bookService.byStudentId(studentId);
        return ResponseEntity.ok(books);
    }

     */

}

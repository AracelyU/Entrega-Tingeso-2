package com.tutorial.cuotaservice.controller;

import com.tutorial.cuotaservice.entity.Cuota;
import com.tutorial.cuotaservice.service.CuotaService;
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

    // obtener cuota por estudiante
    @GetMapping("/bystudent/{studentId}")
    public ResponseEntity<List<Cuota>> getByStudentId(@PathVariable("studentId") int studentId) {
        List<Cuota> books = cuotaService.obtenerCuotasPorEstudiante_Id(studentId);
        return ResponseEntity.ok(books);
    }

    // generar pago
    @GetMapping("/generatepago/{num_cuotas}/{monto_cuotas}/{tipo_pago}/{student_id}")
    public ResponseEntity<String> createdPayment(@PathVariable("num_cuotas") int num_cuotas,
                                                 @PathVariable("tipo_pago") String tipo_pago,
                                                 @PathVariable("student_id") int student_id){
        String mensaje = cuotaService.verificarGuardarPago(student_id, num_cuotas, tipo_pago);
        if (!mensaje.equals("El pago se generó con éxito.")) {
            // Devuelve una respuesta de error con un código de estado 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
        }

        // generar las cuotas
        cuotaService.guardarPago(student_id, num_cuotas, tipo_pago);
        return ResponseEntity.ok(mensaje);
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

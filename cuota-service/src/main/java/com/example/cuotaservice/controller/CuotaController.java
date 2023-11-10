package com.example.cuotaservice.controller;

import com.example.cuotaservice.entity.Cuota;
import com.example.cuotaservice.entity.GenerarCuota;
import com.example.cuotaservice.model.Student;
import com.example.cuotaservice.service.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDateTime;
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

    // obtener el promedio
    @GetMapping("promedio/{rut}")
    public ResponseEntity<Float> promedio(@PathVariable("rut") String rut){
        Float promedio = cuotaService.obtenerPromedio(rut);
        return ResponseEntity.ok(promedio);
    }

    // probar obtener estudiante
    @GetMapping("/estudiantes")
    public ResponseEntity<List<Student>> getAllStudent(){
        List<Student> estudiantes = cuotaService.obtenerEstudiantes();
        if(estudiantes == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estudiantes);
    }

    // obtener cuotas por estudiante
    @GetMapping("/bystudent/{studentId}")
    public ResponseEntity<List<Cuota>> getByStudentId(@PathVariable("studentId") int studentId) {
        List<Cuota> cuotas = cuotaService.obtenerCuotasPorEstudiante_Id(studentId);
        if(cuotas == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cuotas);
    }

    // obtener cuotas pendientes por estudiante
    @GetMapping("/bystudent/pendiente/{studentId}")
    public ResponseEntity<List<Cuota>> getByStudentIdPendiente(@PathVariable("studentId") int studentId) {
        List<Cuota> cuotas = cuotaService.obtenerCuotasPendientesPorEstudiante_id(studentId);
        if(cuotas == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cuotas);
    }

    // obtener estudiante según su id
    @GetMapping("/student/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") int id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Student> response = restTemplate.getForEntity("http://localhost:8080/student/" + id, Student.class);
        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            return ResponseEntity.noContent().build();
        }
        return response;
    }

    // generar pago
    @PostMapping("/generatepago")
    public ResponseEntity<GenerarCuota> createdPayment(@RequestBody GenerarCuota generarCuota){
        // generar las cuotas
        cuotaService.guardarPago(generarCuota.getId_estudiante(), generarCuota.getNum_cuotas(), generarCuota.getTipo_pago());
        return ResponseEntity.ok(generarCuota);
    }

    // cambiar el estado de la cuota
    @PutMapping("/pagar/{id}")
    public ResponseEntity<?> pagarCuota(@PathVariable int id) {
        Cuota cuota = cuotaService.pagarCuota(id);
        return ResponseEntity.ok(cuota);
    }

    // obtener cuotas pagadas
    @GetMapping("/cuotapagada/{id}")
    public ResponseEntity<Integer> cuotasPagadas(@PathVariable int id){
        Integer nro_cuotas = cuotaService.cuotasPagadasPorIdEstudiante(id);
        return ResponseEntity.ok(nro_cuotas);
    }

    // obtener cuotas por pagar
    @GetMapping("/cuotaporpagar/{id}")
    public ResponseEntity<Integer> cuotasPorPagar(@PathVariable int id){
        Integer nro_cuotas = cuotaService.cuotasPorPagarPorIdEstudiante(id);
        return ResponseEntity.ok(nro_cuotas);
    }


    // obtener saldo por pagar
    @GetMapping("/saldoporpagar/{id}")
    public ResponseEntity<Float> saldoPorPagar(@PathVariable int id){
        Float saldo = cuotaService.saldoPorPagar(id);
        return ResponseEntity.ok(saldo);
    }

    // obtener saldo pagado
    @GetMapping("/saldopagado/{id}")
    public ResponseEntity<Float> saldoPagado(@PathVariable int id){
        Float saldo = cuotaService.saldoPagado(id);
        return ResponseEntity.ok(saldo);
    }

    // obtener tipo de pago de la cuota
    @GetMapping("/tipoPago/{id}")
    public ResponseEntity<String> tipoPago(@PathVariable("id") int id){
        String tipo = cuotaService.tipoPago(id);
        if(tipo.equals("")){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tipo);
    }

    // numero de cuotas atrasadas
    @GetMapping("/nroAtrasos/{id}")
    public ResponseEntity<Integer> nroAtrasos(@PathVariable("id") int id){
        Integer nro_cuotas = cuotaService.numeroCuotasAtrasadas(id);
        return ResponseEntity.ok(nro_cuotas);
    }

    // fecha ultimo pago
    @GetMapping("/ultimoPago/{id}")
    public ResponseEntity<LocalDateTime> ultimoPago(@PathVariable("id") int id){
        LocalDateTime fecha = cuotaService.ultimoPago(id);
        if(fecha != null){
            return ResponseEntity.ok(fecha);
        }
        return ResponseEntity.noContent().build();
    }


    // aplicar descuento
    @PutMapping("/descuento")
    public ResponseEntity<String> descuento(){
        System.out.println("ESTOY EN EL CONTROLADOR DE CUOTA");
        cuotaService.aplicarDescuentoPromedio();
        return ResponseEntity.ok("se ha aplicado promedio");
    }

    // aplicar intereses por atraso de cuotas
    @PutMapping("/interes")
    public ResponseEntity<String> interes(){
        List<Student> estudiantes = cuotaService.obtenerEstudiantes();
        for(Student s : estudiantes){
            cuotaService.aplicarInteresAtrasoCuotas(s.getId());
        }
        return ResponseEntity.ok("Aplicado interes");
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

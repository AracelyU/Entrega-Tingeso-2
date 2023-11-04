package com.example.cuotaservice.service;

import com.example.cuotaservice.entity.Cuota;
import com.example.cuotaservice.model.Student;
import com.example.cuotaservice.repository.CuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class CuotaService {

    @Autowired
    CuotaRepository cuotaRepository;

    @Autowired
    RestTemplate restTemplate;

    // obtener todas las cuotas
    public List<Cuota> obtenerCuotas() {
        return cuotaRepository.findAll();
    }

    // obtener las cuotas de un estudiante según su id
    public List<Cuota> obtenerCuotasPorEstudiante_Id(int studentId) {
        return cuotaRepository.findByEstudiante_id(studentId);
    }

    // obtener estudiante por su id
    public Student obtenerEstudiantePorId(int id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Student> response = restTemplate.getForEntity("http://localhost:8080/student/" + id, Student.class);
        return response.getBody();
    }

    // genera un pago
    public void guardarPago(int id_estudiante, int numeroCuotas, String tipoPago){
        Student s = obtenerEstudiantePorId(id_estudiante);

        float monto;
        if(tipoPago.equals("contado")){
            monto = 750000F;
        }else{
            monto = 1500000 * (1 - descuentoAnioEgreso(s) - descuentoTipoColegio(s)) / numeroCuotas;
        }

        LocalDateTime fechaActual = LocalDateTime.now();
        if(tipoPago.equals("cuota")) {
            for (int i = 1; i < numeroCuotas+1; i++) {
                Cuota c = new Cuota();
                c.setEstado_pago("pendiente");
                c.setTipo_pago("cuota");
                c.setNum_cuota(i);
                c.setMonto(monto);
                c.setEstudiante_id(id_estudiante);
                c.setFecha_vencimiento(LocalDate.from(fechaActual.plusMonths(i).withDayOfMonth(10).truncatedTo(ChronoUnit.DAYS))); // trunca a días
                cuotaRepository.save(c);
            }

        }else{  // para contado
            Cuota p = new Cuota();
            p.setMonto(monto);
            p.setNum_cuota(1);
            p.setTipo_pago("contado");
            p.setFecha_vencimiento(LocalDate.from(fechaActual.plusMonths(1).withDayOfMonth(10).truncatedTo(ChronoUnit.DAYS)));
            p.setEstado_pago("pendiente");
            p.setEstudiante_id(id_estudiante);
            cuotaRepository.save(p);
        }
    }

    // los descuentos por tipo de escuela
    public float descuentoTipoColegio(Student s){
        switch (s.getTipo_colegio()){
            case "municipal": return 0.2F;
            case "subvencionado": return 0.1F;
            case "privado": return 0F;
            default: return -1F;
        }
    }

    // los descuentos por año de egreso
    public float descuentoAnioEgreso(Student s){
        LocalDateTime fecha_Actual = LocalDateTime.now();
        int diferenciaAnios = fecha_Actual.getYear() - s.getAnio_egreso();
        if(diferenciaAnios < 1){
            return (float) 0.15;
        }else if(diferenciaAnios <= 2){
            return (float) 0.08;
        }else if(diferenciaAnios <= 4){
            return (float) 0.04;
        }else{
            return 0;
        }
    }

    // obtener cuotas pendientes por un estudiante
    public List<Cuota> obtenerCuotasPendientesPorEstudiante_id(int id){
        return cuotaRepository.findByEstudiante_idPendiente(id);
    }


    // cambia el estado de la cuota a un estado ya pagado
    public Cuota pagarCuota(int id){
        LocalDateTime fechaActual = LocalDateTime.now();
        Cuota c = cuotaRepository.findById(id);
        c.setEstado_pago("pagado");
        c.setFecha_pago(LocalDate.from(fechaActual));
        cuotaRepository.save(c);
        return c;
    }


    // verifica si se cumplen las condiciones para generar un pago MEJOR HACERLO EN FRONTEND
    public String verificarGuardarPago(int id_estudiante, int numeroCuotas, String tipoPago){
        Student s = obtenerEstudiantePorId(id_estudiante);

        if(s == null){
            return "el id del estudiante ingresado no existe.";
        }

        int dia_actual = LocalDateTime.now().getDayOfMonth();
        if(dia_actual >= 5 && dia_actual <= 10){
            return "No se pueden generar pagos en la fecha de pago";
        }

        if(s.getPago() != 0){
            return "Este estudiante ya tiene un pago registrado.";
        }

        if(numeroCuotas <= 0){
            return "El número de cuotas debe de ser positivo.";
        }

        if(tipoPago.equals("contado") && numeroCuotas != 1){
            return "El pago al contado no deben de hacer más de 1 cuota.";
        }

        switch (s.getTipo_colegio()){
            case "municipal":
                if(numeroCuotas > 10){
                    return "Para tipo de escuela municipal se ingreso más de 10 cuotas.";
                }
                break;

            case "subvencionado":
                if(numeroCuotas > 7){
                    return "Para tipo de escuela subvencionado se ingreso más de 7 cuotas.";
                }
                break;

            case "privado":
                if(numeroCuotas > 4){
                    return "Para tipo de escuela privado se ingreso más de 4 cuotas.";
                }
                break;

            default:
                return "Error en tipo de escuela.";
        }

        return "El pago se generó con éxito.";
    }

}




    /*

    public Cuota getBookById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Cuota save(Cuota book) {
        Cuota bookNew = bookRepository.save(book);
        return bookNew;
    }

    public List<Cuota> byStudentId(int studentId) {
        return bookRepository.findByStudentId(studentId);
    }

     */




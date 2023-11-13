package com.example.cuotaservice.service;

import com.example.cuotaservice.entity.Cuota;
import com.example.cuotaservice.model.Student;
import com.example.cuotaservice.repository.CuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
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
        ResponseEntity<Student> response = restTemplate.getForEntity("http://gateway-service/student/" + id, Student.class);
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
                c.setFecha_vencimiento(fechaActual.plusMonths(i).withDayOfMonth(10).truncatedTo(ChronoUnit.DAYS)); // trunca a días
                cuotaRepository.save(c);
            }

        }else{  // para contado
            Cuota p = new Cuota();
            p.setMonto(monto);
            p.setNum_cuota(1);
            p.setTipo_pago("contado");
            p.setFecha_vencimiento(fechaActual.plusMonths(1).withDayOfMonth(10).truncatedTo(ChronoUnit.DAYS));
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
        c.setFecha_pago(fechaActual);
        cuotaRepository.save(c);
        return c;
    }


    // numero de cuotas pagadas
    public Integer cuotasPagadasPorIdEstudiante(int id_estudiante){
        return cuotaRepository.findCuotasPagadas(id_estudiante);
    }

    // numero de cuotas por pagar
    public Integer cuotasPorPagarPorIdEstudiante(int id_estudiante){
        return cuotaRepository.findCuotasPorPagar(id_estudiante);
    }

    // saldo que aún queda por pagar
    public Float saldoPorPagar(int id_estudiante){
        if(cuotaRepository.findSaldoPorPagar(id_estudiante) != null){
            return cuotaRepository.findSaldoPorPagar(id_estudiante);
        }else{
            return 0F;
        }
    }

    // saldo pagado
    public Float saldoPagado(int id_estudiante){
        if(cuotaRepository.findSaldoPagado(id_estudiante) != null){
            return cuotaRepository.findSaldoPagado(id_estudiante);
        }else{
            return 0F;
        }
    }

    // obtener tipo de cuota
    public String tipoPago(int id_estudiante){
        List<Cuota> cuotas = obtenerCuotasPorEstudiante_Id(id_estudiante);
        if(cuotas != null){
            return cuotas.get(0).getTipo_pago();
        }
        return "";
    }

    // obtener ultima fecha de pago
    public LocalDateTime ultimoPago(int id_estudiante){
        List<Cuota> cuotas = cuotaRepository.findUltimoPago(id_estudiante);
        if(cuotas != null) {
            LocalDateTime fecha = cuotas.get(0).getFecha_pago();
            if(fecha != null){
                return fecha;
            }
            return null;
        }
        return null;
    }



    // descuento por atraso en cuotas
    public Float descuentoAtrasoCuotas(Cuota c){
        LocalDateTime fecha_actual = LocalDateTime.now();
        int diferencia_anio = c.getFecha_vencimiento().getYear()- fecha_actual.getYear();

        if(diferencia_anio < 0){ // no se esta pagando dentro del año
            return 0.15F;
        }
        if(fecha_actual.isAfter(c.getFecha_vencimiento())){// la cuota esta vencida
            // obtener la diferencia de tiempo entre las fechas
            Period meses_diferencia = Period.between(c.getFecha_vencimiento().toLocalDate(), fecha_actual.toLocalDate());
            int meses = meses_diferencia.getMonths();
            if(meses == 0){
                return 0F;

            }else if(meses == 1){
                return 0.03F;

            } else if (meses == 2) {
                return 0.06F;

            } else if (meses == 3){
                return 0.09F;

            }else{
                return 0.15F;
            }
        }
        // sino esta vencida no hay porque aplicar descuento
        return 0F;
    }

    public void aplicarInteresAtrasoCuotas(int id_estudiante){
        List<Cuota> cuotas = cuotaRepository.findByEstudiante_idPendiente(id_estudiante);
        for(Cuota c : cuotas){
            Float monto = c.getMonto() * (1 + descuentoAtrasoCuotas(c));
            c.setMonto(monto);
            cuotaRepository.save(c);
        }
    }

    public Integer numeroCuotasAtrasadas(int id_estudiante){
        List<Cuota> cuotas = cuotaRepository.findByEstudiante_idPendiente(id_estudiante);
        Integer atrasos = 0;
        for(Cuota c : cuotas){
            if(descuentoAtrasoCuotas(c) != 0F){ // si el valor era cero no tenía atrasos
                atrasos++;
            }
        }
        return atrasos;

    }


    // obtener estudiantes
    public List<Student> obtenerEstudiantes() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Student[]> response = restTemplate.getForEntity("http://gateway-service/student", Student[].class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return Arrays.asList(response.getBody());
        } else {
            throw new RuntimeException(response.getBody().toString());
        }
    }

    // obtener promedio
    public Float obtenerPromedio(String rut){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Float> response = restTemplate.getForEntity("http://gateway-service/examen/promedio/" + rut, Float.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException(response.getBody().toString());
        }
    }

    // descuento por promedio de los ultimos examenes
    public Float descuentoPuntajePromedio(Float puntaje){
        if(puntaje < 850){
            return 0F;
        }else if (puntaje < 899){
            return 0.02F;
        }else if (puntaje < 949){
            return 0.05F;
        }else{
            return 0.1F;
        }
    }

    // aplicar descuento a todas las cuotas de los estudiantes
    public void aplicarDescuentoPromedio(){
        List<Student> estudiantes = obtenerEstudiantes();
        for(int i=0; i < estudiantes.size(); i++){
            Student s = estudiantes.get(i);
            List<Cuota> cuotas = obtenerCuotasPendientesPorEstudiante_id(s.getId());
            if(cuotas != null){ // esto si el estudiante tiene cuotas
                // calcular promedio
                Float promedio = obtenerPromedio(s.getRut());
                // si el estudiante tiene pago al contado aún no pagado se le añade lo obtenido al monto devuelto
                if (cuotas.get(0).getTipo_pago().equals("contado") && cuotas.get(0).getEstado_pago().equals("pendiente")) {
                    Cuota c_contado = cuotas.get(0);
                    Float monto_nuevo = c_contado.getMonto() * (1 - descuentoPuntajePromedio(promedio)); // cuando hay que devolverle?
                    c_contado.setSaldo_devuelto(monto_nuevo);
                    cuotaRepository.save(c_contado);
                }

                // si es en cuotas se aplica a todas las cuotas pendientes
                for (Cuota c : cuotas) {
                    // aplicar descuento  a las cuotas
                    Float monto_nuevo = c.getMonto() * (1 - descuentoPuntajePromedio(promedio));
                    c.setMonto(monto_nuevo);
                    cuotaRepository.save(c);
                }
            }
        }
    }





}







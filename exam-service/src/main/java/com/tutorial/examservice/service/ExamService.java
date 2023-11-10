package com.tutorial.examservice.service;

import com.tutorial.examservice.entity.Exam;
import com.tutorial.examservice.model.Cuota;
import com.tutorial.examservice.model.Student;
import com.tutorial.examservice.repository.ExamRepository;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ExamService {

    @Autowired
    ExamRepository examRepository;

    @Autowired
    RestTemplate restTemplate;

    // guardar examen
    public Exam guardarExamen(Exam e){
        return examRepository.save(e);
    }


    // la función guardar csv
    public String guardar(MultipartFile file){
        String filename = file.getOriginalFilename();
        assert filename != null;
        if(!filename.toLowerCase().contains(".csv")){
            System.out.println("No ingreso un archivo csv");
            return "No ingreso un archivo csv";
        }
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Archivo guardado con éxito");
            return "Archivo guardado con exito!";
        }
        else{
            System.out.println("No se pudo guardar el archivo");
            return "No se pudo guardar el archivo";
        }
    }

    // guardar en BD lo obtenido del csv
    public Exam guardarDataDB(String rut, String fechaExamen, String puntajeObtenido, String direccion) {
        Exam newData = new Exam();
        newData.setRut(rut);
        newData.setFecha_examen(LocalDate.parse(fechaExamen));
        newData.setPuntaje_examen(Float.valueOf(puntajeObtenido));
        newData.setNombre_examen(direccion.replaceAll("\\.csv$", "")); // se quita la extensión del string
        return examRepository.save(newData);
    }

    // lee el csv
    public void leerContenido(String filename){
        BufferedReader br = null;
        String line = "";
        //Se define separador ","
        String cvsSplitBy = ";";
        try {
            br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(cvsSplitBy);
                //Imprime datos.
                if(!(datos[0] + ";" + datos[1] + ";" + datos[2]).equals("rut;fecha_examen;puntaje_obtenido")){
                    System.out.println(datos[0] + ";" + datos[1] + ";" + datos[2]);
                    guardarDataDB(datos[0], datos[1], datos[2], filename);

                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // obtener estudiante según su id
    public Student getStudentById(@PathVariable("id") int id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Student> response = restTemplate.getForEntity("http://localhost:8080/student/" + id, Student.class);
        return response.getBody();
    }


    // obtener monto por pagar
    public Float getMontoPorPagar(@PathVariable("id") int id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Float> response = restTemplate.getForEntity("http://localhost:8080/cuota/saldoporpagar/" + id, Float.class);
        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            return 0F;
        }
        return response.getBody();
    }

    // obtener monto pagado
    public Float getMontoPagado(@PathVariable("id") int id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Float> response = restTemplate.getForEntity("http://localhost:8080/cuota/saldopagado/" + id, Float.class);
        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            return 0F;
        }
        return response.getBody();
    }

    // obtener número de cuotas
    public Integer getNumeroCuotasPagadas(@PathVariable("id") int id){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Integer> pagadas = restTemplate.getForEntity("http://localhost:8080/cuota/cuotapagada/" + id, Integer.class);
        return pagadas.getBody();
    }

    // obtener número de cuotas pagadas
    public Integer getNumeroCuotasPorPagar(@PathVariable("id") int id){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Integer> pendientes = restTemplate.getForEntity("http://localhost:8080/cuota/cuotaporpagar/" + id, Integer.class);
        return pendientes.getBody();
    }

    // obtener tipo de pago
    public String getTipoPago(@PathVariable("id") int id){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> tipo = restTemplate.getForEntity("http://localhost:8080/cuota/tipoPago/" + id, String.class);
        return tipo.getBody();
    }

    // obtener numero de cuotas atrasadas
    public Integer getNumeroCuotasAtrasadas(@PathVariable("id") int id){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Integer> nro_cuotas_atrasadas = restTemplate.getForEntity("http://localhost:8080/cuota/nroAtrasos/" + id, Integer.class);
        return nro_cuotas_atrasadas.getBody();

    }

    // obtener el ultimo pago
    public LocalDateTime getUltimoPago(@PathVariable("id") int id){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LocalDateTime> fecha = restTemplate.getForEntity("http://localhost:8080/cuota/ultimoPago/" + id, LocalDateTime.class);
        return fecha.getBody();
    }


    // indica si la base de datos está vacia
    public List<Exam> obtenerExamenes(){
        return examRepository.findAll();
    }


    // aplica promedio a los ultimos examenes de un estudiante
    public Float obtenerPromedio(String rut){
        Float num = examRepository.findPuntajePromedio(rut);
        if(num != null){
            return num;
        }else{
            return 0F;
        }
    }

    // obtiene el numero de examenes que rindio un estudiante
    public int numeroPruebas(String rut){
        return examRepository.findNumeroPruebas(rut);
    }


    // aplicar descuento a la base de datos
    public void aplicarDescuentoPromedio(){
        System.out.println("ESTOY EN EL SERVICIO DE EXAM");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put("http://localhost:8080/cuota/descuento", String.class);
    }

    // aplicar intereses por atrado de cuotas
    public void aplicarInteresAtrasoCuotas(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put("http://localhost:8080/cuota/interes", String.class);
    }





}










    /*
    // aplicar descuento a todas las cuotas de los estudiantes
    public void aplicarDescuentoPromedio(){
        List<Student> estudiantes = studentService.obtenerEstudiantes();
        for(Student s : estudiantes){

            List<CuotaEntity> cuotas = cuotaService.encontrarCuotasPendientesPorIdEstudiante(s.getId());

            if(cuotas != null){

                // calcular promedio
                Float promedio = examService.obtenerPromedio(s.getRut());

                // si el estudiante tiene pago al contado aún no pagado se le añade lo obtenido al monto devuelto
                if (cuotas.get(0).getPago().getTipo_pago().equals("contado") && cuotas.get(0).getEstado_cuota().equals("pendiente")) {
                    Cuota c_contado = cuotas.get(0);
                    Float monto_nuevo = c_contado.getMonto() * (1 - cuotaService.descuentoPuntajePromedio(promedio)); // cuando hay que devolverle?
                    c_contado.getPago().setSaldo_devuelto(monto_nuevo);
                    cuotaService.guardarCuota(c_contado);
                }

                // si es en cuotas se aplica a todas las cuotas pendientes
                for (CuotaEntity c : cuotas) {
                    // aplicar descuento  a las cuotas
                    Float monto_nuevo = c.getValor_cuota() * (1 - cuotaService.descuentoPuntajePromedio(promedio));
                    c.setValor_cuota(monto_nuevo);
                    cuotaService.guardarCuota(c);
                }
            }
        }
    }






    }

    // descuento por atraso en cuotas
    public Float descuentoAtrasoCuotas(CuotaEntity c){
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

    public void aplicarInteresAtrasoCuotas(Long id_estudiante){
        ArrayList<CuotaEntity> cuotas = cuotaRepository.findCuotaEntitiesPendientesByIdStudent(id_estudiante);
        for(CuotaEntity c : cuotas){
            Float monto = c.getValor_cuota() * (1 + descuentoAtrasoCuotas(c));
            c.setValor_cuota(monto);
            cuotaRepository.save(c);
        }
    }

    public Integer numeroCuotasAtrasadas(Long id_estudiante){
        ArrayList<CuotaEntity> cuotas = encontrarCuotasPendientesPorIdEstudiante(id_estudiante);
        Integer atrasos = 0;
        for(CuotaEntity c : cuotas){
            if(descuentoAtrasoCuotas(c) != 0F){ // si el valor era cero no tenía atrados
                atrasos++;
            }
        }
        return atrasos;
    }

     */


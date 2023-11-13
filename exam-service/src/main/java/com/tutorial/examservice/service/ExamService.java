package com.tutorial.examservice.service;

import com.tutorial.examservice.entity.Exam;
import com.tutorial.examservice.model.Student;
import com.tutorial.examservice.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        ResponseEntity<Student> response = restTemplate.getForEntity("http://gateway-service/student/" + id, Student.class);
        return response.getBody();
    }


    // obtener monto por pagar
    public Float getMontoPorPagar(@PathVariable("id") int id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Float> response = restTemplate.getForEntity("http://gateway-service/cuota/saldoporpagar/" + id, Float.class);
        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            return 0F;
        }
        return response.getBody();
    }

    // obtener monto pagado
    public Float getMontoPagado(@PathVariable("id") int id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Float> response = restTemplate.getForEntity("http://gateway-service/cuota/saldopagado/" + id, Float.class);
        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            return 0F;
        }
        return response.getBody();
    }

    // obtener número de cuotas
    public Integer getNumeroCuotasPagadas(@PathVariable("id") int id){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Integer> pagadas = restTemplate.getForEntity("http://gateway-service/cuota/cuotapagada/" + id, Integer.class);
        return pagadas.getBody();
    }

    // obtener número de cuotas pagadas
    public Integer getNumeroCuotasPorPagar(@PathVariable("id") int id){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Integer> pendientes = restTemplate.getForEntity("http://gateway-service/cuota/cuotaporpagar/" + id, Integer.class);
        return pendientes.getBody();
    }

    // obtener tipo de pago
    public String getTipoPago(@PathVariable("id") int id){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> tipo = restTemplate.getForEntity("http://gateway-service/cuota/tipoPago/" + id, String.class);
        return tipo.getBody();
    }

    // obtener numero de cuotas atrasadas
    public Integer getNumeroCuotasAtrasadas(@PathVariable("id") int id){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Integer> nro_cuotas_atrasadas = restTemplate.getForEntity("http://gateway-service/cuota/nroAtrasos/" + id, Integer.class);
        return nro_cuotas_atrasadas.getBody();

    }

    // obtener el ultimo pago
    public LocalDateTime getUltimoPago(@PathVariable("id") int id){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LocalDateTime> fecha = restTemplate.getForEntity("http://gateway-service/cuota/ultimoPago/" + id, LocalDateTime.class);
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
        restTemplate.put("http://gateway-service/cuota/descuento", String.class);
    }

    // aplicar intereses por atrado de cuotas
    public void aplicarInteresAtrasoCuotas(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put("http://gateway-service/cuota/interes", String.class);
    }





}



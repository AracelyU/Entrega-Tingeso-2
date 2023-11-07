package com.tutorial.examservice.service;

import com.tutorial.examservice.entity.Exam;
import com.tutorial.examservice.model.Student;
import com.tutorial.examservice.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExamService {

    @Autowired
    ExamRepository examRepository;

    @Autowired
    RestTemplate restTemplate;


    // la función guardar es para traer a la carpeta src los archivos seleccionados
    public String guardar(MultipartFile file){
        String filename = file.getOriginalFilename();
        assert filename != null;
        if(!filename.toLowerCase().contains(".csv")){
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
            return "Archivo guardado con exito!";
        }
        else{
            return "No se pudo guardar el archivo";
        }
    }

    // leer el csv ingresado y guardarlo en BD
    public void leerCsv(String filename){
        String texto = ""; // para almacenar el contenido del texto
        BufferedReader bf = null; // Objeto para leer
        try{
            bf = new BufferedReader(new FileReader(filename)); // abro el archivo csv para lectura
            String temp = ""; // para acumular las líneas leidas
            String bfRead; // para almacenar cada línea de archivo
            int count = 1; // desde que fila va a leer (omite la primera línea del archivo csv que
            // tiene los nombres de las variables)
            while((bfRead = bf.readLine()) != null){ // mientras hayan lineas por leer
                if (count == 1){ // si el contador es 1
                    count = 0; // omite la primera fila del csv
                }
                else{ // sino, significa que se están leyendo las otras líneas
                    // guardo los datos de cada celda de la fila en la base de datos de examenes
                    guardarDataDB(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2], filename);

                    temp = temp + "\n" + bfRead; // acumulo la linea leida
                }
            }
            texto = temp;
            System.out.println("Archivo leido exitosamente");
        }catch(Exception e){
            System.err.println("No se encontro el archivo");
        }finally{
            if(bf != null){
                try{
                    bf.close(); // me encargo que se cierre BufferedReader
                }catch(IOException e){

                }
            }
        }
    }


    // guardar en BD lo obtenido del csv
    public void guardarDataDB(String rut, String fechaExamen, String puntajeObtenido, String direccion){
        Exam newData = new Exam();
        newData.setRut(rut);
        newData.setFecha_examen(LocalDateTime.parse(fechaExamen));
        newData.setPuntaje_examen(Float.valueOf(puntajeObtenido));
        newData.setNombre_examen(direccion.replaceAll("\\.csv$", "")); // se quita la extensión del string

        System.out.println("llego aqui");
        examRepository.save(newData);
    }

    // indica si la base de datos está vacia
    public List<Exam> obtenerTest(){
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

    public Integer cuotasPagadas(int studentId) {
        return restTemplate.getForObject("http://localhost:8080/cuota/cuotapagada/" + studentId, Integer.class);
    }

    public Integer cuotasPorPagar(int studentId){
        return restTemplate.getForObject("http://localhost:8080/cuota/cuotaporpagar/" + studentId, Integer.class);
    }

    public Float saldoPorPagar(int studentId){
        return restTemplate.getForObject("http://localhost:8080/cuota/saldoporpagar/" + studentId, Float.class);
    }

    public Float saldoPagado(int studentId){
        return restTemplate.getForObject("http://localhost:8080/cuota/saldopagado/" + studentId, Float.class);
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



}

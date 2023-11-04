package com.tutorial.examservice.service;

import com.tutorial.examservice.entity.Exam;
import com.tutorial.examservice.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class ExamService {

    @Autowired
    ExamRepository examRepository;

    // la función guardar es para traer a la carpeta src los archivos seleccionados
    public String guardar(MultipartFile file){
        String filename = file.getOriginalFilename();
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
    public String leerCsv(String direccion){
        String texto = "";
        BufferedReader bf = null;
        //testRepository.deleteAll();
        if(!direccion.toLowerCase().contains(".csv")){
            return "No ingreso un archivo csv";
        }
        try{
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if (count == 1){
                    count = 0;
                }
                else{
                    guardarDataDB(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2], direccion);
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            return "Archivo leido exitosamente";
        }catch(Exception e){
            return "No se encontro el archivo";
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException ignored){

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
        examRepository.save(newData);
    }



}

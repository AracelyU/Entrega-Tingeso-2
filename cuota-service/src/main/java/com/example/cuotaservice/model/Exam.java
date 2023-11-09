package com.example.cuotaservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exam {
    private int id;
    private String rut; // conecta con estudiante
    private String nombre_examen;
    private Float puntaje_examen;
    private LocalDate fecha_examen;
}

package com.tutorial.studentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam {

    private int id_examen;

    private String nombre_examen;

    private Float puntaje_examen;

    private LocalDate fecha_examen;

    private Long estudiante_id;  // conecta con estudiante

}

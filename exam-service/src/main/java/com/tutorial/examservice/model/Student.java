package com.tutorial.examservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private int id;
    private String rut;
    private String nombre_estudiante;
    private String apellido_estudiante;
    private LocalDate fecha_nacimiento;
    private String tipo_colegio;
    private String nombre_colegio;
    private Integer anio_egreso;

    //-----------
    private Float matricula; // indica la matricula de 70000
    private Float saldo_devuelto; // salgo que se devuelve para los que pagaron contado

}

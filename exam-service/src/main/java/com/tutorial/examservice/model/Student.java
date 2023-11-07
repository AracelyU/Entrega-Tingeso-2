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

    private Integer pago; // indica si hay un pago (1) o no (0) según si tiene o no cuotas

    private String tipo_pago;  // contado o cuota

    private Float matricula; // indica la matricula de 70000

}

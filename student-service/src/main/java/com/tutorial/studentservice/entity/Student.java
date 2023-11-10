package com.tutorial.studentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "estudiante")
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
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

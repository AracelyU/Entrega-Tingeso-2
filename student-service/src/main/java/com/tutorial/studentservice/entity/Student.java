package com.tutorial.studentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
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

    private String tipo_escuela;

    private String nombre_escuela;

    private Integer anio_egreso;

    //-----------

    private Integer pago; // indica si hay un pago (1) o no (0) según si tiene o no cuotas

    private String tipo_pago;  // contado o cuota

    private Float matricula; // indica la matricula de 70000

    private LocalDate ultimo_pago;  // ingresa el último pago que se hizo de pago

    private Float saldo_devuelto; // salgo que se devuelve para los que pagaron contado


}

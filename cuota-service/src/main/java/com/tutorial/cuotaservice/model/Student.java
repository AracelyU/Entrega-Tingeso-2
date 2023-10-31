package com.tutorial.cuotaservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private int id;

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

    private LocalDateTime ultimo_pago;  // ingresa el último pago que se hizo de pago

    private Float saldo_devuelto; // salgo que se devuelve para los que pagaron contado


}

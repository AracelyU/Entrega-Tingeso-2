package com.tutorial.studentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuota {

    private int id_cuota;

    private Integer num_cuota;

    private Float monto;

    private LocalDate fecha_vencimiento; // fecha en que vence la cuota

    private LocalDate fecha_pago; // fecha en que se pago

    private String estado_pago; // pagado o pendiente

    private Long estudiantet_id; // conecta con estudiante
}

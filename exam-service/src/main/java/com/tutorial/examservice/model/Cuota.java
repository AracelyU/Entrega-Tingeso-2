package com.tutorial.examservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cuota {

    private int id;
    private Integer num_cuota;
    private Float monto;
    private LocalDateTime fecha_vencimiento; // fecha en que vence la cuota
    private LocalDateTime fecha_pago; // fecha en que se pago
    private String estado_pago; // pagado o pendiente
    private String tipo_pago;  // contado o cuota
    private Float saldo_devuelto;
    private int estudiante_id; // conecta con estudiante
}

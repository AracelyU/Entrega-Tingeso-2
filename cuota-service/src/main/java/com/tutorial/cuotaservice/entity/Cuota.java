package com.tutorial.cuotaservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuota {   // es una cuota individual

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Integer num_cuota;
    private Float monto;
    private LocalDate fecha_vencimiento; // fecha en que vence la cuota
    private LocalDate fecha_pago; // fecha en que se pago
    private String estado_pago; // pagado o pendiente
    private int estudiante_id; // conecta con estudiante

}

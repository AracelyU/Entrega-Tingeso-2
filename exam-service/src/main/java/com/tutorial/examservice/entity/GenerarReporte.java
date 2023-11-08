package com.tutorial.examservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class GenerarReporte {

    /*
    RUT estudiante
▪ Nombre del estudiante
▪ Nro. exámenes rendidos
▪ Promedio puntaje exámenes
▪ Monto total arancel a pagar
▪ Tipo Pago (Contado/Cuotas)
▪ Nro. total de cuotas pactadas
▪ Nro. cuotas pagadas
▪ Monto total pagado
▪ Fecha último pago
▪ Saldo por pagar
▪ Nro. Cuotas con retraso

     */
    private int id;
    private String rut;
    private String nombre;
    private Integer nro_examenes;
    private Float promedio;
    private Float monto_total;
    private String tipo_pago;
    private Integer nro_cuotas;
    private Integer nro_cuotas_pagadas;
    private Float pagado;  // el monto pagado
    private LocalDate ultimo_pago;  // la fecha
    private Float por_pagar; // el monto por pagar
    private Integer nro_cuotas_atrado;

}

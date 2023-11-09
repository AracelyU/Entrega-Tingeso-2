package com.tutorial.examservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String rut;
    private String nombre;
    private String apellido;
    private Integer nro_examenes;
    private Float promedio;
    private Float monto_total;
    private String tipo_pago;
    private Integer nro_cuotas;
    private Integer nro_cuotas_pagadas;
    private Float pagado;  // el monto pagado
    private LocalDateTime ultimo_pago;  // la fecha
    private Float por_pagar; // el monto por pagar
    private Integer nro_cuotas_atraso;

}

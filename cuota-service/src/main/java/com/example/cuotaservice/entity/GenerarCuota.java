package com.example.cuotaservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerarCuota {

    int num_cuotas;
    String tipo_pago;
    int id_estudiante;

}

package modules.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "cuota")
public class CuotaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_cuota;

    private Integer num_cuota;

    private Float monto;

    private LocalDate fecha_vencimiento; // fecha en que vence la cuota

    private LocalDate fecha_pago; // fecha en que se pago

    private String estado_pago; // pagado o pendiente

    private Long estudiante_id; // conecta con estudiante

}

package Esports.Reglas_Torneo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reglas_del_torneo")
public class ReglaTorneo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ReglasTorneo_id;

    private Integer minimoJugadores;
    @Column(length = 500)
    private String descripcion;

    private Long  torneo_id;

    private Long juego_id;

    private Boolean activo;
}

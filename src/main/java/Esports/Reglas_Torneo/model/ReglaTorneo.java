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
    private Long reglasTorneoId;

    private Integer minimoJugadores;
    private Boolean requiereHandCam;
    private Boolean requiereAntiCheat;
    @Column(length = 500)
    private String descripcion;
    @Column(name = "torneo_id", nullable = false)
    private Long  torneoId;
    @Column(name = "juego_id", nullable = false)
    private Long juegoId;
    @Column(nullable = false)
    private Boolean activo;
}

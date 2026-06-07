package Esports.Reglas_Torneo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reglas_del_torneo")
public class ReglaTorneo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reglasTorneoId;
    @Column(name = "torneo_id", nullable = false)
    private Long  torneoId;
    @Column(name = "juego_id", nullable = false)
    private Long juegoId;
    @Column(name = "jugadores_requeridos")
    private Integer jugadoresRequeridos;
    @Column(name = "fecha_limite_inscripcion", nullable = false)
    private  LocalDate fechaLimiteInscripcion;
    @Column(name = "cupo_maximo_equipos", nullable = false)
    private Integer cupoMaximoEquipos;
    @Column(name = "formato_mejor_de", nullable = false)
    private Integer formatoMejorDe;
    @Column(length = 500)
    private String descripcion;
    @Column(nullable = false)
    private Boolean activo;
}

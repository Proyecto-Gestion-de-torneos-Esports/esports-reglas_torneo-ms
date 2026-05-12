package Esports.Reglas_Torneo.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

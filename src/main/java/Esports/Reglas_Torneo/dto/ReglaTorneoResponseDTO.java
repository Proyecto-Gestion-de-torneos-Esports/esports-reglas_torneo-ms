package Esports.Reglas_Torneo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReglaTorneoResponseDTO {
    private Long id;
    private Integer minimoJugadores;
    private String descripcion;
    private Long torneo_id;
    private Long juego_id;
    private Boolean activo;
}


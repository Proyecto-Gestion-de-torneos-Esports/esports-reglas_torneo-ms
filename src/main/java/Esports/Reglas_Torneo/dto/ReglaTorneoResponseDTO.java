package Esports.Reglas_Torneo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReglaTorneoResponseDTO {
    private Long id;
    private Integer minimoJugadores;
    private String descripcion;
    private Long torneoId;
    private Long juegoId;
    private Boolean activo;
}


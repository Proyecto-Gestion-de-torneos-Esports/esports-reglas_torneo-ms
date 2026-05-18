package Esports.Reglas_Torneo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReglaTorneoResponseDTO {
    private Long reglasTorneoid;
    private Integer minimoJugadores;
    private Boolean requiereHandCam;
    private Boolean requiereAntiCheat;
    private String descripcion;
    private Long torneoId;
    private Long juegoId;
    private Boolean activo;
}


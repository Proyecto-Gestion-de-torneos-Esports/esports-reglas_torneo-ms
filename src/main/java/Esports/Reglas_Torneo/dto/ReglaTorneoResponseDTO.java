package Esports.Reglas_Torneo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReglaTorneoResponseDTO {
    private Long reglasTorneoId;
    private Long torneoId;
    private Long juegoId;
    private Integer jugadoresRequeridos;
    private LocalDate fechaLimiteInscripcion;
    private Integer cupoMaximoEquipos;
    private Integer formatoMejorDe;
    private String descripcion;
    private Boolean activo;
}


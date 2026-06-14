package Esports.Reglas_Torneo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Respuesta con los datos de una regla de torneo")

public class ReglaResponseDTO extends RepresentationModel<ReglaResponseDTO> {
    @Schema(description = "ID único de la regla", example = "1")
    private Long reglasTorneoId;
    @Schema(description = "ID del torneo", example = "10")
    private Long torneoId;
    @Schema(description = "ID del juego", example = "3")
    private Long juegoId;
    @Schema(description = "Cantidad mínima de jugadores por equipo", example = "5")
    private Integer jugadoresRequeridos;
    @Schema(description = "Fecha límite de inscripción", example = "2026-06-20")
    private LocalDate fechaLimiteInscripcion;
    @Schema(description = "Cupo máximo de equipos permitidos", example = "16")
    private Integer cupoMaximoEquipos;
    @Schema(description = "Formato de las partidas (mejor de X)", example = "3")
    private Integer formatoMejorDe;
    @Schema(description = "Descripción general", example = "Torneo de FIFA")
    private String descripcion;
    @Schema(description = "Indica si la regla está activa", example = "true")
    private Boolean activo;
}


package Esports.Reglas_Torneo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos requeridos para crear o actualizar una regla de torneo")
public class ReglaRequestDTO {

    @NotNull(message = "El ID del torneo es obligatorio ")
    @Schema(description = "ID de torneo que pertenecen las reglas", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long torneoId;

    @NotNull(message = "El ID de juego es obligatorio")
    @Schema(description = "ID del juego asociado al torneo", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long juegoId;

    @NotNull(message = "Minimo de jugadores obligatorio")
    @Min(value = 1, message = "Debe haber al menos 1 jugador mínimo")
    @Schema(description = "Cantidad mínima de jugadores requeridos por equipo", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer jugadoresRequeridos;

    @NotNull(message = "La fecha limite de inscripcion es obligatoria")
    @Schema(description = "Fecha máxima para que los equipos se inscriban (anterior al inicio del torneo)", example = "2026-06-20", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fechaLimiteInscripcion;

    @NotNull(message = "El cupo maximo de equipos es obligatorio")
    @Schema(description = "Cantidad maxima de equipos permitidos (debe ser potencia de 2)", example = "16", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer cupoMaximoEquipos;

    @NotNull(message = "El formato mejor de es obligatorio")
    @Schema(description = "Formato de partidas, Debe ser impar (1, 3, 5)", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer formatoMejorDe;

    @NotBlank(message = "La descripcion no puede estar vacia")
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    @Schema(description = "Descripcion general", example = "Torneo de Rocket League", requiredMode = Schema.RequiredMode.REQUIRED)
    private String descripcion;

    @Schema(description = "Indica si la regla está activa", example = "true")
    @NotNull(message = "El campo activo es obligatorio")
    private Boolean activo;
}

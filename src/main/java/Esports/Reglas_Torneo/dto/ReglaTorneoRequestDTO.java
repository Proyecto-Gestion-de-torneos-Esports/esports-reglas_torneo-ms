package Esports.Reglas_Torneo.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReglaTorneoRequestDTO {
    @NotNull(message = "El ID del torneo es obligatorio ")
    private Long torneoId;
    @NotNull(message = ("El ID de juego es obligatorio"))
    private Long juegoId;
    @NotNull(message = "Minimo de jugadores obligatorio")
    @Min(value = 1, message = "Debe haber al menos 1 jugador mínimo")
    private Integer jugadoresRequeridos;
    @NotNull(message = "La fecha limite de inscripcion es obligatoria")
    private LocalDate fechaLimiteInscripcion;
    @NotNull(message = "el cupo maximo de equipos es obligatorio")
    private Integer cupoMaximoEquipos;
    @NotNull(message = "El formato mejor de es obligatorio")
    private Integer formatoMejorDe;
    @NotBlank(message = "La descripcion no puede estar vacia")
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String descripcion;

    @NotNull(message = "El campo activo es obligatorio")
    private Boolean activo;
}

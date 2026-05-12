package Esports.Reglas_Torneo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data

public class ReglaTorneoRequestDTO {

    @NotNull(message = "Minimo de jugadores obligatorio")
    @Positive(message = "El minimo de jugador es 1")
    private Integer minimoJugadores;
    @NotBlank(message = "La descripcion es obligatoria ")
    private String descripcion;

    @NotNull(message = "El ID del torneo es obligatorio ")
    private Long torneo_id;

    @NotNull(message = ("El ID de juego es obligatorio"))
    private Long juego_id;

    private Boolean activo;
}

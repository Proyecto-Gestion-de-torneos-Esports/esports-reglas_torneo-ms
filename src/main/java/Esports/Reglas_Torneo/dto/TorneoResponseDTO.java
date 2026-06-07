package Esports.Reglas_Torneo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TorneoResponseDTO {
    private Long torneoId;
    private LocalDate fecha;
    private Long idJuego;
}

package Esports.Reglas_Torneo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JuegoResponseDTO {
    private Long juegoId;
    private String nombre;

}

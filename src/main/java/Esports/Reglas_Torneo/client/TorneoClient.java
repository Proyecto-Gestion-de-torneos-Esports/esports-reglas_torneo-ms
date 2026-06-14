package Esports.Reglas_Torneo.client;

import Esports.Reglas_Torneo.dto.JuegoResponseDTO;
import Esports.Reglas_Torneo.dto.TorneoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "torneos", path = "/api/torneos")
public interface TorneoClient {
    @GetMapping("/{id}")
    TorneoResponseDTO obtenerTorneoPorId(@PathVariable Long id);
}

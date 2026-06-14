package Esports.Reglas_Torneo.client;

import Esports.Reglas_Torneo.dto.JuegoResponseDTO;
import Esports.Reglas_Torneo.dto.TorneoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-juego", path = "/api/juego")
public interface JuegoClient {
    @GetMapping("/{id}")
    JuegoResponseDTO obtenerJuegoPorId(@PathVariable("id") Long id);
}


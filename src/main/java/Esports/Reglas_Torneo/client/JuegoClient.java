package Esports.Reglas_Torneo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "juego-service", url = "http://localhost:8012/api/juego")
public interface JuegoClient {
    @GetMapping("/{id}")
    Object obtenerJuegoPorId(@PathVariable("id") Long id);

}

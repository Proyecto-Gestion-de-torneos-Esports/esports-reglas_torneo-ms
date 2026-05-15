package Esports.Reglas_Torneo.controller;

import Esports.Reglas_Torneo.dto.ReglaTorneoRequestDTO;
import Esports.Reglas_Torneo.dto.ReglaTorneoResponseDTO;
import Esports.Reglas_Torneo.service.ReglaTorneoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reglas_torneo")
@RequiredArgsConstructor
public class TorneoReglaController {
    private final ReglaTorneoService reglaTorneoService;

    @GetMapping
    public ResponseEntity<List<ReglaTorneoResponseDTO>> obtenertodo(){
        return ResponseEntity.ok(reglaTorneoService.obtenerTodos());
    }

    @GetMapping("{id}")
    public ResponseEntity<ReglaTorneoResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(reglaTorneoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ReglaTorneoResponseDTO> crearRegla(@Valid @RequestBody ReglaTorneoRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(reglaTorneoService.CrearRegla(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReglaTorneoResponseDTO> actualiazarRegla(@PathVariable Long id, @Valid @RequestBody ReglaTorneoRequestDTO dto){
        return ResponseEntity.ok(reglaTorneoService.actualizarRegla(id,dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRegla(@PathVariable Long id){
        reglaTorneoService.eliminarRegla(id);
        return ResponseEntity.noContent().build();
    }

}

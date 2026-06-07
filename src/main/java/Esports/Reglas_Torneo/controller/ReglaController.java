package Esports.Reglas_Torneo.controller;

import Esports.Reglas_Torneo.dto.ReglaTorneoRequestDTO;
import Esports.Reglas_Torneo.dto.ReglaTorneoResponseDTO;
import Esports.Reglas_Torneo.service.ReglaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reglas")
@RequiredArgsConstructor
public class ReglaController {
    private final ReglaService reglaService;

    @GetMapping
    public ResponseEntity<List<ReglaTorneoResponseDTO>> obtenertodo(){
        return ResponseEntity.ok(reglaService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReglaTorneoResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(reglaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ReglaTorneoResponseDTO> crearRegla(@Valid @RequestBody ReglaTorneoRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(reglaService.crearReglas(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReglaTorneoResponseDTO> actualiazarRegla(@PathVariable Long id, @Valid @RequestBody ReglaTorneoRequestDTO dto){
        return ResponseEntity.ok(reglaService.actualizarRegla(id,dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRegla(@PathVariable Long id){
        reglaService.eliminarRegla(id);
        return ResponseEntity.noContent().build();
    }

}

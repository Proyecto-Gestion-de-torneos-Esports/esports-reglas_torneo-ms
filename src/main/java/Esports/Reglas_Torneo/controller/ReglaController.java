package Esports.Reglas_Torneo.controller;

import Esports.Reglas_Torneo.assemblers.ReglaModelAssemblers;
import Esports.Reglas_Torneo.dto.ReglaRequestDTO;
import Esports.Reglas_Torneo.dto.ReglaResponseDTO;
import Esports.Reglas_Torneo.service.ReglaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reglas")
@RequiredArgsConstructor
@Tag(name = "Reglas del Torneo", description = "Endpoints para gestionar las configuraciones y reglas de los torneos")
public class ReglaController {
    private final ReglaService reglaService;
    private final ReglaModelAssemblers assembler;

    @Operation(summary = "Listar todas las reglas", description = "Devuelve una lista de todas las reglas registradas en el sistema con sus enlaces de navegacion")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @PreAuthorize("hasRole('JUGADOR') or hasRole('COACH') or hasRole('ARBITRO') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ReglaResponseDTO>> obtenerTodos() {
        List<ReglaResponseDTO> lista = reglaService.obtenerTodos().stream()
                .map(assembler::toModel)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar regla por ID", description = "Retorna el detalle de la regla por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Regla encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Regla no encontrada")
    })
    @PreAuthorize("hasRole('JUGADOR') or hasRole('COACH') or hasRole('ARBITRO') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ReglaResponseDTO> buscarPorId(@PathVariable Long id) {
        ReglaResponseDTO regla = reglaService.obtenerPorId(id);
        return ResponseEntity.ok(assembler.toModel(regla));
    }

    @Operation(summary = "Crear nueva regla", description = "Registra una nueva regla en el sistema")
    @ApiResponse(responseCode = "201", description = "Regla creada exitosamente")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ReglaResponseDTO> crear(@Valid @RequestBody ReglaRequestDTO dto) {
        ReglaResponseDTO nuevaRegla = reglaService.crearReglas(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevaRegla));
    }

    @Operation(summary = "Actualizar regla", description = "Modifica los atributos de una regla existente en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Regla actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Regla no encontrada para actualizar")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ReglaResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ReglaRequestDTO dto) {
        ReglaResponseDTO reglaActualizada = reglaService.actualizarRegla(id, dto);
        return ResponseEntity.ok(assembler.toModel(reglaActualizada));
    }

    @Operation(summary = "Eliminar regla", description = "Elimina de forma lógica una normativa del sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Regla eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Regla no encontrada")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reglaService.eliminarRegla(id);
        return ResponseEntity.noContent().build();
    }
}



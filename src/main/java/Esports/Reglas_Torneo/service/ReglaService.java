package Esports.Reglas_Torneo.service;

import Esports.Reglas_Torneo.client.AuditoriaClient;
import Esports.Reglas_Torneo.client.JuegoClient;
import Esports.Reglas_Torneo.client.TorneoClient;
import Esports.Reglas_Torneo.dto.*;
import Esports.Reglas_Torneo.model.ReglaTorneo;
import Esports.Reglas_Torneo.repository.ReglaTorneoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReglaService {

    private final ReglaTorneoRepository reglaTorneoRepository;
    private final AuditoriaClient auditoriaClient;
    private final JuegoClient juegoClient;
    private final TorneoClient torneoClient;

    public ReglaResponseDTO mapToDTO(ReglaTorneo regla){
        return new ReglaResponseDTO(
                regla.getReglasTorneoId(),
                regla.getTorneoId(),
                regla.getJuegoId(),
                regla.getJugadoresRequeridos(),
                regla.getFechaLimiteInscripcion(),
                regla.getCupoMaximoEquipos(),
                regla.getFormatoMejorDe(),
                regla.getDescripcion(),
                regla.getActivo()


        );
    }

    @Transactional(readOnly = true)
    public List<ReglaResponseDTO> obtenerTodos() {
        log.info("Consultando todas las reglas del torneo");
        return reglaTorneoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReglaResponseDTO obtenerPorId(long id) {
        log.info("Buscando reglas con ID {}", id);
        ReglaTorneo regla = reglaTorneoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La regla de torneo con ID " + id + " no existe."));

        return mapToDTO(regla);
    }
    @Transactional
    public ReglaResponseDTO crearReglas(ReglaRequestDTO dto) {
        log.info("Iniciando creación de reglas para el torneo ID {}", dto.getTorneoId());

        TorneoResponseDTO torneo = torneoClient.obtenerTorneoPorId(dto.getTorneoId());
        JuegoResponseDTO juego = juegoClient.obtenerJuegoPorId(dto.getJuegoId());

        if (!dto.getJuegoId().equals(torneo.getIdJuego())) {
            throw new RuntimeException("Error: El juego ID " + dto.getJuegoId() + " no coincide con el juego asignado a este torneo (ID " + torneo.getIdJuego() + ").");
        }

        if (dto.getFechaLimiteInscripcion().isAfter(torneo.getFecha()) || dto.getFechaLimiteInscripcion().isEqual(torneo.getFecha())) {
            throw new RuntimeException("Error: La fecha límite de inscripción debe ser anterior a la fecha de inicio del torneo (" + torneo.getFecha() + ").");
        }

        int cupos = dto.getCupoMaximoEquipos();
        boolean potenciaDeDos = (cupos > 0) && ((cupos & (cupos - 1)) == 0);

        if (!potenciaDeDos || cupos < 4) {
            throw new RuntimeException("Error: El cupo máximo de equipos debe ser de 2 4, 8, 16, 32, 64.");
        }
        if (dto.getJugadoresRequeridos() <= 0) {
            throw new RuntimeException("Error: La cantidad de jugadores requerida debe ser al menos 1.");
        }
        if (dto.getFormatoMejorDe() == null || dto.getFormatoMejorDe() <= 0 || dto.getFormatoMejorDe() % 2 == 0 ){
            throw new RuntimeException("Error: El formato de partida debe ser un numero impar mayor a 0 para evitar empates");
        }

        ReglaTorneo nuevaRegla = new ReglaTorneo();
        nuevaRegla.setTorneoId(dto.getTorneoId());
        nuevaRegla.setJuegoId(dto.getJuegoId());
        nuevaRegla.setJugadoresRequeridos(dto.getJugadoresRequeridos());
        nuevaRegla.setFechaLimiteInscripcion(dto.getFechaLimiteInscripcion());
        nuevaRegla.setCupoMaximoEquipos(dto.getCupoMaximoEquipos());
        nuevaRegla.setFormatoMejorDe(dto.getFormatoMejorDe());
        nuevaRegla.setActivo(dto.getActivo());

        ReglaTorneo guardada = reglaTorneoRepository.save(nuevaRegla);
        log.info("Reglas guardadas exitosamente con ID {}", guardada.getReglasTorneoId());
        generarAuditoria("regla guardada");

        return mapToDTO(guardada);
    }


    @Transactional
    public ReglaResponseDTO actualizarRegla(Long id, ReglaRequestDTO dto){
        log.info("Actualizando datos de las reglas del torneo con ID: {} ", id);
        ReglaTorneo existente  = reglaTorneoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Fallo la modificacion de regla torneo con ID '{}'", id);
                    return new RuntimeException("No se puede actualizar, ID" +id+ "no encontrado");
                });
        existente.setTorneoId(dto.getTorneoId());
        existente.setJuegoId(dto.getJuegoId());
        existente.setJugadoresRequeridos(dto.getJugadoresRequeridos());
        existente.setFechaLimiteInscripcion(dto.getFechaLimiteInscripcion());
        existente.setCupoMaximoEquipos(dto.getCupoMaximoEquipos());
        existente.setDescripcion(dto.getDescripcion());
        existente.setFormatoMejorDe(dto.getFormatoMejorDe());

        existente.setActivo(dto.getActivo());

        ReglaTorneo reglaActualizada = reglaTorneoRepository.save(existente);
        log.info("Regla con ID: {} actualizada correctamente ", id);
        generarAuditoria("Se actualizo Regla_torneo");
        return mapToDTO(reglaActualizada);
    }

    @Transactional
    public void eliminarRegla(Long id) {
        log.info("Eliminando la regla con el ID: {} ", id);
        if (!reglaTorneoRepository.existsById(id)){
            log.error("Fallo al eliminar: La regla de torneo con ID: {} ", id);
            throw new RuntimeException(" no se puede eliminar, id " + id + " no encontrado" );
        }
        reglaTorneoRepository.findById(id).ifPresent(reglaTorneo -> {
            reglaTorneo.setActivo(false);
            reglaTorneoRepository.save(reglaTorneo);
            log.info("Regla con ID: {} desactivada correctamente", id);
            generarAuditoria("Se desactivo una regla en el torneo");
        });
    }
    public void generarAuditoria(String detalle){
        AuditoriaRequestDTO dto = new AuditoriaRequestDTO();
        LocalDate ahora = LocalDate.now();
        dto.setDetalle(detalle);
        dto.setFecha(ahora);

        AuditoriaResponseDTO respuesta = auditoriaClient.generarAuditoria(dto);
    }

}

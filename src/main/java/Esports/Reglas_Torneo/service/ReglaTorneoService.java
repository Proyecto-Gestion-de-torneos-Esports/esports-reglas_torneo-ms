package Esports.Reglas_Torneo.service;

import Esports.Reglas_Torneo.client.AuditoriaClient;
import Esports.Reglas_Torneo.client.JuegoClient;
import Esports.Reglas_Torneo.client.TorneoClient;
import Esports.Reglas_Torneo.dto.AuditoriaRequestDTO;
import Esports.Reglas_Torneo.dto.AuditoriaResponseDTO;
import Esports.Reglas_Torneo.dto.ReglaTorneoRequestDTO;
import Esports.Reglas_Torneo.dto.ReglaTorneoResponseDTO;
import Esports.Reglas_Torneo.exception.ReglaTorneoNotFoundException;
import Esports.Reglas_Torneo.model.ReglaTorneo;
import Esports.Reglas_Torneo.repository.ReglaTorneoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReglaTorneoService {

    private final ReglaTorneoRepository reglaTorneoRepository;
    private final AuditoriaClient auditoriaClient;
    private final JuegoClient juegoClient;
    private final TorneoClient torneoClient;

    public ReglaTorneoResponseDTO mapToDTO(ReglaTorneo regla){
        return new ReglaTorneoResponseDTO(
                regla.getReglasTorneoId(),
                regla.getMinimoJugadores(),
                regla.getRequiereHandCam(),
                regla.getRequiereAntiCheat(),
                regla.getDescripcion(),
                regla.getTorneoId(),
                regla.getJuegoId(),
                regla.getActivo()

        );
    }

    @Transactional(readOnly = true)
    public List<ReglaTorneoResponseDTO> obtenerTodos() {
        log.info("Consultando todas las reglas del torneo");
        return reglaTorneoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReglaTorneoResponseDTO obtenerPorId(long id) {
        log.info("Iniciando busqueda ID: {}", id);
        Optional<ReglaTorneo> regla = reglaTorneoRepository.findById(id);

        if (regla.isPresent()){
            log.info("Regla de Torneo con ID: {} encontrado exitosamente ", id);
            return regla.map(this::mapToDTO).orElseThrow();

        }
        log.warn("No se encontro con la regla de torneo con el ID: '{}'", id);
        throw new ReglaTorneoNotFoundException("Regla con id " + id+ "no encontrada");
    }
    @Transactional
    public ReglaTorneoResponseDTO CrearRegla(ReglaTorneoRequestDTO dto){
        log.info("Creando una nueva regla para el torneo con ID: {} y juego ID {} ", dto.getTorneoId(), dto.getJuegoId());

        torneoClient.obtenerTorneoPorId(dto.getTorneoId());
        juegoClient.obtenerJuegoPorId(dto.getJuegoId());

        ReglaTorneo nuevaRegla = new ReglaTorneo();
        nuevaRegla.setMinimoJugadores(dto.getMinimoJugadores());
        nuevaRegla.setRequiereHandCam(dto.getRequiereHandCam());
        nuevaRegla.setRequiereAntiCheat(dto.getRequiereAntiCheat());
        nuevaRegla.setDescripcion(dto.getDescripcion());
        nuevaRegla.setTorneoId(dto.getTorneoId());
        nuevaRegla.setJuegoId(dto.getJuegoId());
        nuevaRegla.setActivo(true);


        ReglaTorneo reglaCreada = reglaTorneoRepository.save(nuevaRegla);
        log.info("Regla de torneo con ID : {} fue creada exitosamente ", reglaCreada.getReglasTorneoId());
        generarAuditoria("Se creo una nueva Regla para el torneo");
        return mapToDTO(reglaCreada);

    }
    @Transactional
    public  ReglaTorneoResponseDTO actualizarRegla(Long id, ReglaTorneoRequestDTO dto){
        log.info("Actualizando datos de las reglas del torneo con ID: {} ", id);
        ReglaTorneo existente  = reglaTorneoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Fallo la modificacion de regla torneo con ID '{}'", id);
                    return new ReglaTorneoNotFoundException("No se puede actualizar, ID" +id+ "no encontrado");
                });
        existente.setMinimoJugadores(dto.getMinimoJugadores());
        existente.setRequiereHandCam(dto.getRequiereHandCam());
        existente.setRequiereAntiCheat(dto.getRequiereAntiCheat());
        existente.setDescripcion(dto.getDescripcion());
        existente.setTorneoId(dto.getTorneoId());
        existente.setJuegoId(dto.getJuegoId());
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
            throw new ReglaTorneoNotFoundException(" no se puede eliminar, id " + id + " no encontrado" );
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

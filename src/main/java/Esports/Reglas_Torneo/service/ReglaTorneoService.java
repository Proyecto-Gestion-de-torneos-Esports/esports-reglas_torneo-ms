package Esports.Reglas_Torneo.service;

import Esports.Reglas_Torneo.dto.ReglaTorneoRequestDTO;
import Esports.Reglas_Torneo.dto.ReglaTorneoResponseDTO;
import Esports.Reglas_Torneo.exception.ReglaTorneoNotFoundException;
import Esports.Reglas_Torneo.model.ReglaTorneo;
import Esports.Reglas_Torneo.repository.ReglaTorneoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReglaTorneoService {

    private final ReglaTorneoRepository reglaTorneoRepository;

    public ReglaTorneoResponseDTO mapToDTO(ReglaTorneo regla){
        return new ReglaTorneoResponseDTO(
                regla.getReglasTorneo_id(),
                regla.getMinimoJugadores(),
                regla.getDescripcion(),
                regla.getTorneo_id(),
                regla.getJuego_id(),
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
        log.info("iniciando busqueda ID: {}", id);
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
        log.info("Iniciando creación de nueva regla para el torneo con ID: {} ", dto.getTorneo_id(), dto.getJuego_id());
        ReglaTorneo nuevaRegla = new ReglaTorneo(
                null,
                dto.getMinimoJugadores(),
                dto.getDescripcion(),
                dto.getTorneo_id(),
                dto.getJuego_id(),
                true
        );
        ReglaTorneo reglaCreada = reglaTorneoRepository.save(nuevaRegla);
        log.info("Regla de torneo con ID : {} fue creada exitosamente ", reglaCreada.getReglasTorneo_id());
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
        existente.setDescripcion(dto.getDescripcion());
        existente.setTorneo_id(dto.getTorneo_id());
        existente.setJuego_id(dto.getJuego_id());
        existente.setActivo(dto.getActivo());

        ReglaTorneo reglaActualizada = reglaTorneoRepository.save(existente);
        log.info("Regla con ID: {} actualizada correctamente ", id);
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
        });
    }


}

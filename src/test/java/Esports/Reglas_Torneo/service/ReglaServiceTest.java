package Esports.Reglas_Torneo.service;

import Esports.Reglas_Torneo.client.AuditoriaClient;
import Esports.Reglas_Torneo.client.JuegoClient;
import Esports.Reglas_Torneo.client.TorneoClient;
import Esports.Reglas_Torneo.dto.JuegoResponseDTO;
import Esports.Reglas_Torneo.dto.ReglaRequestDTO;
import Esports.Reglas_Torneo.dto.ReglaResponseDTO;
import Esports.Reglas_Torneo.dto.TorneoResponseDTO;
import Esports.Reglas_Torneo.model.ReglaTorneo;
import Esports.Reglas_Torneo.repository.ReglaTorneoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReglaServiceTest {
    @Mock
    private ReglaTorneoRepository reglaTorneoRepository;
    @Mock
    private AuditoriaClient auditoriaClient;
    @Mock
    private JuegoClient juegoClient;
    @Mock
    private TorneoClient torneoClient;

    @InjectMocks
    private ReglaService reglaService;

    private ReglaRequestDTO requestValido;
    private TorneoResponseDTO torneoMock;
    private JuegoResponseDTO juegoMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestValido = new ReglaRequestDTO(
                1L, 3L, 5, LocalDate.now().plusDays(5), 16, 3, "Torneo de CS2", true
        );

        torneoMock = new TorneoResponseDTO();
        torneoMock.setTorneoId(1L);
        torneoMock.setIdJuego(3L);
        torneoMock.setFecha(LocalDate.now().plusDays(10)); // El torneo inicia después de la inscripción

        juegoMock = new JuegoResponseDTO();
        juegoMock.setJuegoId(3L);
        juegoMock.setNombre("CS2");
    }

    @Test
    void crearReglaGuardarConExito() {
        when(torneoClient.obtenerTorneoPorId(1L)).thenReturn(torneoMock);
        when(juegoClient.obtenerJuegoPorId(3L)).thenReturn(juegoMock);

        ReglaTorneo reglaGuardada = new ReglaTorneo();
        reglaGuardada.setReglasTorneoId(100L);
        reglaGuardada.setTorneoId(1L);
        reglaGuardada.setActivo(true);
        when(reglaTorneoRepository.save(any(ReglaTorneo.class))).thenReturn(reglaGuardada);

        ReglaResponseDTO resultado = reglaService.crearReglas(requestValido);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getTorneoId());

        verify(reglaTorneoRepository, times(1)).save(any(ReglaTorneo.class));
        verify(auditoriaClient, times(1)).generarAuditoria(any());
    }

    @Test
    void crearReglasCupoNoEsPotenciaDeDos() {
        requestValido.setCupoMaximoEquipos(10);
        when(torneoClient.obtenerTorneoPorId(1L)).thenReturn(torneoMock);
        when(juegoClient.obtenerJuegoPorId(3L)).thenReturn(juegoMock);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reglaService.crearReglas(requestValido);
        });

        assertTrue(exception.getMessage().contains("El cupo máximo de equipos debe ser"));

        verify(reglaTorneoRepository, never()).save(any());
    }

    @Test
    void crearReglaFechaInscripcionInvalida() {

        requestValido.setFechaLimiteInscripcion(LocalDate.now().plusDays(15));
        when(torneoClient.obtenerTorneoPorId(1L)).thenReturn(torneoMock);
        when(juegoClient.obtenerJuegoPorId(3L)).thenReturn(juegoMock);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reglaService.crearReglas(requestValido);
        });

        assertTrue(exception.getMessage().contains("anterior a la fecha de inicio del torneo"));
        verify(reglaTorneoRepository, never()).save(any());
    }
    @Test
    void actualizarReglaConExito() {
        Long idRegla = 100L;
        ReglaTorneo reglaExistente = new ReglaTorneo();
        reglaExistente.setReglasTorneoId(idRegla);
        reglaExistente.setActivo(true);

        when(reglaTorneoRepository.findById(idRegla)).thenReturn(java.util.Optional.of(reglaExistente));
        when(reglaTorneoRepository.save(any(ReglaTorneo.class))).thenReturn(reglaExistente);

        ReglaResponseDTO resultado = reglaService.actualizarRegla(idRegla, requestValido);

        assertNotNull(resultado);
        verify(reglaTorneoRepository, times(1)).save(any(ReglaTorneo.class));
        verify(auditoriaClient, times(1)).generarAuditoria(any());
    }

    @Test
    void actualizarReglaIdNoExiste() {
        // Given
        Long idInvalido = 999L;
        when(reglaTorneoRepository.findById(idInvalido)).thenReturn(java.util.Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reglaService.actualizarRegla(idInvalido, requestValido);
        });

        assertTrue(exception.getMessage().contains("no encontrado"));
        verify(reglaTorneoRepository, never()).save(any());
    }

    @Test
    void eliminarReglaConExito() {

        Long idRegla = 100L;
        ReglaTorneo reglaExistente = new ReglaTorneo();
        reglaExistente.setReglasTorneoId(idRegla);
        reglaExistente.setActivo(true);

        when(reglaTorneoRepository.existsById(idRegla)).thenReturn(true);
        when(reglaTorneoRepository.findById(idRegla)).thenReturn(java.util.Optional.of(reglaExistente));

        reglaService.eliminarRegla(idRegla);

        verify(reglaTorneoRepository, times(1)).save(reglaExistente);
        verify(auditoriaClient, times(1)).generarAuditoria(any());

        assertFalse(reglaExistente.getActivo());
    }

    @Test
    void eliminarReglaIdNoExiste() {

        Long idInvalido = 999L;
        when(reglaTorneoRepository.existsById(idInvalido)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reglaService.eliminarRegla(idInvalido);
        });

        assertTrue(exception.getMessage().contains("no encontrado"));
        verify(reglaTorneoRepository, never()).save(any());
    }
}


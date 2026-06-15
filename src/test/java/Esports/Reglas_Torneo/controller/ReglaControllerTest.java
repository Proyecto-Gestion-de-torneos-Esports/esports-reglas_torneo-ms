package Esports.Reglas_Torneo.controller;

import Esports.Reglas_Torneo.assemblers.ReglaModelAssemblers;
import Esports.Reglas_Torneo.dto.ReglaRequestDTO;
import Esports.Reglas_Torneo.dto.ReglaResponseDTO;
import Esports.Reglas_Torneo.security.JwtFilter;
import Esports.Reglas_Torneo.security.SecurityConfig;
import Esports.Reglas_Torneo.service.ReglaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@WebMvcTest(ReglaController.class)
@Import({ReglaModelAssemblers.class})
@WithMockUser(roles = "ADMIN")
public class ReglaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReglaService reglaService;

    @Autowired
    private ObjectMapper objectMapper;

    private ReglaRequestDTO requestDTO;
    private ReglaResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new ReglaRequestDTO(
                1L, 3L, 5, LocalDate.now().plusDays(5), 16, 3, "Torneo de Counter Strike 2", true
        );

        responseDTO = new ReglaResponseDTO(
                1L, 1L, 3L, 5, LocalDate.now().plusDays(5), 16, 3, "Torneo de Counter Strike 2", true
        );
    }

    @Test
    void obtenerTodasEstado200() throws Exception {

        when(reglaService.obtenerTodos()).thenReturn(Arrays.asList(responseDTO));

        mockMvc.perform(get("/api/reglas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].reglasTorneoId").value(1L))
                .andExpect(jsonPath("$[0].descripcion").value("Torneo de Counter Strike 2"));
    }

    @Test
    void buscarPorIdConExito() throws Exception {
        when(reglaService.obtenerPorId(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/reglas/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reglasTorneoId").value(1L))
                .andExpect(jsonPath("$.descripcion").value("Torneo de Counter Strike 2"));
    }

    @Test
    void crearReglaDatosValidos() throws Exception {
        when(reglaService.crearReglas(any(ReglaRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/reglas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.reglasTorneoId").value(1L))
                .andExpect(jsonPath("$.descripcion").value("Torneo de Counter Strike 2"));
    }

    @Test
    void actualizarReglaConExito() throws Exception {
        when(reglaService.actualizarRegla(anyLong(), any())).thenReturn(responseDTO);

        mockMvc.perform(put("/api/reglas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reglasTorneoId").value(1L))
                .andExpect(jsonPath("$.descripcion").value("Torneo de Counter Strike 2"));
    }

    @Test
    void eliminarRegla204() throws Exception {
        doNothing().when(reglaService).eliminarRegla(1L);

        mockMvc.perform(delete("/api/reglas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}



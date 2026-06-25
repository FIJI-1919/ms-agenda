package com.vetnova.ms_agenda.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.vetnova.ms_agenda.dto.CitaRequestDTO;
import com.vetnova.ms_agenda.dto.CitaResponseDTO;
import com.vetnova.ms_agenda.exception.CitaNoEncontradaException;
import com.vetnova.ms_agenda.exception.GlobalExceptionHandler;
import com.vetnova.ms_agenda.service.CitaService;

@ExtendWith(MockitoExtension.class)
class CitaControllerTest {

    @Mock
    private CitaService citaService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        CitaController controller =
                new CitaController(citaService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setMessageConverters(
                        new MappingJackson2HttpMessageConverter())
                .build();
    }

    @Test
    @DisplayName("GET /api/v1/citas debe listar citas")
    void debeListarCitas() throws Exception {

        CitaResponseDTO cita =
                new CitaResponseDTO(
                        1L,
                        1L,
                        LocalDate.now(),
                        LocalTime.of(10, 0),
                        "Dr. Perez",
                        "Control",
                        "AGENDADA"
                );

        when(citaService.listar())
                .thenReturn(List.of(cita));

        mockMvc.perform(get("/api/v1/citas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(citaService).listar();
    }

    @Test
    @DisplayName("GET /api/v1/citas/{id} debe buscar cita")
    void debeBuscarCitaPorId() throws Exception {

        CitaResponseDTO cita =
                new CitaResponseDTO(
                        1L,
                        1L,
                        LocalDate.now(),
                        LocalTime.of(10, 0),
                        "Dr. Perez",
                        "Control",
                        "AGENDADA"
                );

        when(citaService.buscarPorId(1L))
                .thenReturn(cita);

        mockMvc.perform(get("/api/v1/citas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado")
                        .value("AGENDADA"));

        verify(citaService).buscarPorId(1L);
    }

    @Test
    @DisplayName("GET /api/v1/citas/{id} retorna 404")
    void debeRetornar404() throws Exception {

        when(citaService.buscarPorId(99L))
                .thenThrow(
                        new CitaNoEncontradaException(
                                "Cita no encontrada"
                        )
                );

        mockMvc.perform(get("/api/v1/citas/99"))
                .andExpect(status().isNotFound());

        verify(citaService).buscarPorId(99L);
    }

    @Test
    @DisplayName("POST /api/v1/citas debe guardar cita")
    void debeGuardarCita() throws Exception {

        CitaResponseDTO respuesta =
                new CitaResponseDTO(
                        1L,
                        1L,
                        LocalDate.now(),
                        LocalTime.of(10, 0),
                        "Dr. Perez",
                        "Control",
                        "AGENDADA"
                );

        when(citaService.guardar(any(CitaRequestDTO.class)))
                .thenReturn(respuesta);

        String json = """
                {
                  "mascotaId":1,
                  "fecha":"2030-01-01",
                  "hora":"10:00:00",
                  "veterinario":"Dr. Perez",
                  "motivo":"Control"
                }
                """;

        mockMvc.perform(
                post("/api/v1/citas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        verify(citaService)
                .guardar(any(CitaRequestDTO.class));
    }

@Test
@DisplayName("DELETE /api/v1/citas/{id} elimina cita")
void debeEliminarCita() throws Exception {

    mockMvc.perform(delete("/api/v1/citas/1"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                    "\"Cita eliminada correctamente\""
            ));

    verify(citaService).eliminar(1L);
}
}
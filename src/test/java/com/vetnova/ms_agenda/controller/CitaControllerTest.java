package com.vetnova.ms_agenda.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;

import com.vetnova.ms_agenda.dto.CitaResponseDTO;
import com.vetnova.ms_agenda.service.CitaService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CitaController.class)
public class CitaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CitaService service;

    @Test
    void buscarPorIdDebeRetornar200() throws Exception {

        CitaResponseDTO dto = new CitaResponseDTO(
                1L,
                1L,
                LocalDate.now(),
                LocalTime.of(10, 0),
                "Dr. Pérez",
                "Control",
                "AGENDADA"
        );

        when(service.buscarPorId(1L))
                .thenReturn(dto);

        mockMvc.perform(get("/api/v1/citas/1"))
                .andExpect(status().isOk());
    }
}
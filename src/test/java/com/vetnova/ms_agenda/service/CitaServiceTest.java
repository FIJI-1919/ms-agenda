package com.vetnova.ms_agenda.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

import com.vetnova.ms_agenda.dto.CitaResponseDTO;
import com.vetnova.ms_agenda.model.Cita;
import com.vetnova.ms_agenda.repository.CitaRepository;

public class CitaServiceTest {

    @Mock
    private CitaRepository repository;

    @Mock
    private WebClient webClient;

    @InjectMocks
    private CitaService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarPorIdDebeRetornarCita() {

        Cita cita = new Cita(
                1L,
                1L,
                LocalDate.now(),
                LocalTime.of(10, 0),
                "Dr. Pérez",
                "Control",
                "AGENDADA"
        );

        when(repository.findById(1L))
                .thenReturn(Optional.of(cita));

        CitaResponseDTO resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(repository, times(1))
                .findById(1L);
    }
}
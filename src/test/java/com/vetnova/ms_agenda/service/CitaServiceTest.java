package com.vetnova.ms_agenda.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.reactive.function.client.WebClient;

import com.vetnova.ms_agenda.dto.CitaResponseDTO;
import com.vetnova.ms_agenda.exception.CitaNoEncontradaException;
import com.vetnova.ms_agenda.model.Cita;
import com.vetnova.ms_agenda.repository.CitaRepository;

@ExtendWith(MockitoExtension.class)
class CitaServiceTest {

    @Mock
    private CitaRepository repository;

    @Mock
    private WebClient webClient;

    @InjectMocks
    private CitaService service;

    @Test
    @DisplayName("Debe listar citas")
    void debeListarCitas() {

        Cita cita = new Cita(
                1L,
                1L,
                LocalDate.now(),
                LocalTime.of(10, 0),
                "Dr. Perez",
                "Control",
                "AGENDADA"
        );

        when(repository.findAll())
                .thenReturn(List.of(cita));

        List<CitaResponseDTO> resultado =
                service.listar();

        assertEquals(1, resultado.size());

        verify(repository).findAll();
    }

    @Test
    @DisplayName("Debe buscar cita por ID")
    void debeBuscarPorId() {

        Cita cita = new Cita(
                1L,
                1L,
                LocalDate.now(),
                LocalTime.of(10, 0),
                "Dr. Perez",
                "Control",
                "AGENDADA"
        );

        when(repository.findById(1L))
                .thenReturn(Optional.of(cita));

        CitaResponseDTO resultado =
                service.buscarPorId(1L);

        assertEquals(1L, resultado.getId());

        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción si no existe")
    void debeLanzarExcepcion() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                CitaNoEncontradaException.class,
                () -> service.buscarPorId(99L)
        );
    }

    @Test
    @DisplayName("Debe eliminar cita")
    void debeEliminar() {

        Cita cita = new Cita();

        cita.setId(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(cita));

        service.eliminar(1L);

        verify(repository).delete(cita);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar si no existe")
    void debeLanzarExcepcionAlEliminar() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                CitaNoEncontradaException.class,
                () -> service.eliminar(99L)
        );
    }
}
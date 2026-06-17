package com.vetnova.ms_agenda.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitaResponseDTO {

    private Long id;
    private Long mascotaId;
    private LocalDate fecha;
    private LocalTime hora;
    private String veterinario;
    private String motivo;
    private String estado;
}
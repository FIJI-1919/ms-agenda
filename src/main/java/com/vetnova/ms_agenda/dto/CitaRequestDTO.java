package com.vetnova.ms_agenda.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class CitaRequestDTO {

    @NotNull(message = "El ID de la mascota es obligatorio")
    private Long mascotaId;

    @NotNull(message = "La fecha es obligatoria")
    @FutureOrPresent(message = "La fecha no puede ser pasada")
    private LocalDate fecha;

    @NotNull(message = "La hora es obligatoria")
    private LocalTime hora;

    @NotBlank(message = "El veterinario es obligatorio")
    @Size(min = 2, max = 50, message = "El veterinario debe tener entre 2 y 50 caracteres")
    private String veterinario;

    @NotBlank(message = "El motivo es obligatorio")
    @Size(min = 3, max = 100, message = "El motivo debe tener entre 3 y 100 caracteres")
    private String motivo;

    private String estado;
}
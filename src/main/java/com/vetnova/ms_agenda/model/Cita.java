package com.vetnova.ms_agenda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor

public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La mascota es obligatoria")
    @Size(min = 2, max = 50)
    private String mascota;

    @NotBlank(message = "El dueño es obligatorio")
    @Size(min = 2, max = 50)
    private String dueno;

    @NotBlank(message = "La fecha es obligatoria")
    private String fecha;

    @NotBlank(message = "La hora es obligatoria")
    private String hora;

    @NotBlank(message = "El veterinario es obligatorio")
    @Size(min = 2, max = 50)
    private String veterinario;
}
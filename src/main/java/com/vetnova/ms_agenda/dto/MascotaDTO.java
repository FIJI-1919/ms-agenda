package com.vetnova.ms_agenda.dto;

import lombok.Data;

@Data
public class MascotaDTO {

    private Long id;
    private String nombre;
    private String especie;
    private String raza;
    private Double edad;
    private Long duenoId;
}
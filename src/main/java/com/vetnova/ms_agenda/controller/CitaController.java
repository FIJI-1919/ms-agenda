package com.vetnova.ms_agenda.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.vetnova.ms_agenda.dto.MascotaDTO;
import com.vetnova.ms_agenda.model.Cita;
import com.vetnova.ms_agenda.service.CitaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/citas")
public class CitaController {

    private final CitaService service;

    public CitaController(CitaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Cita> listar() {
        return service.listar();
    }

    @PostMapping
    public Cita guardar(@Valid @RequestBody Cita cita) {
        return service.guardar(cita);
    }

    @GetMapping("/mascotas")
    public List<MascotaDTO> obtenerMascotas() {
        return service.obtenerMascotas();
    }
}
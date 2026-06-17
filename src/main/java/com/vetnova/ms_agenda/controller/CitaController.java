package com.vetnova.ms_agenda.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vetnova.ms_agenda.dto.CitaRequestDTO;
import com.vetnova.ms_agenda.dto.CitaResponseDTO;
import com.vetnova.ms_agenda.dto.MascotaDTO;
import com.vetnova.ms_agenda.service.CitaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/citas")
public class CitaController {

    private final CitaService service;

    public CitaController(CitaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CitaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<CitaResponseDTO> guardar(
            @Valid @RequestBody CitaRequestDTO dto) {

        return new ResponseEntity<>(
                service.guardar(dto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CitaRequestDTO dto) {

        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);

        return ResponseEntity.ok("Cita eliminada correctamente");
    }

    @GetMapping("/mascotas")
    public ResponseEntity<List<MascotaDTO>> obtenerMascotas() {
        return ResponseEntity.ok(service.obtenerMascotas());
    }
}
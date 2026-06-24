package com.vetnova.ms_agenda.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vetnova.ms_agenda.dto.CitaRequestDTO;
import com.vetnova.ms_agenda.dto.CitaResponseDTO;
import com.vetnova.ms_agenda.dto.MascotaDTO;
import com.vetnova.ms_agenda.service.CitaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(
    name = "Citas",
    description = "API para la gestión de citas veterinarias"
)
@RestController
@RequestMapping("/api/v1/citas")
public class CitaController {

    private final CitaService service;

    public CitaController(CitaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todas las citas")
    @GetMapping
    public ResponseEntity<List<CitaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Buscar cita por ID")
    @GetMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Registrar una nueva cita")
    @PostMapping
    public ResponseEntity<CitaResponseDTO> guardar(
            @Valid @RequestBody CitaRequestDTO dto) {

        return new ResponseEntity<>(
                service.guardar(dto),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Actualizar una cita")
    @PutMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CitaRequestDTO dto) {

        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar una cita")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);

        return ResponseEntity.ok("Cita eliminada correctamente");
    }

    @Operation(summary = "Obtener mascotas desde ms-mascotas")
    @GetMapping("/mascotas")
    public ResponseEntity<List<MascotaDTO>> obtenerMascotas() {
        return ResponseEntity.ok(service.obtenerMascotas());
    }
}
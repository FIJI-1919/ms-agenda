package com.vetnova.ms_agenda.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.vetnova.ms_agenda.dto.CitaRequestDTO;
import com.vetnova.ms_agenda.dto.CitaResponseDTO;
import com.vetnova.ms_agenda.dto.MascotaDTO;
import com.vetnova.ms_agenda.exception.CitaNoEncontradaException;
import com.vetnova.ms_agenda.exception.ErrorComunicacionException;
import com.vetnova.ms_agenda.exception.MascotaNoEncontradaException;
import com.vetnova.ms_agenda.model.Cita;
import com.vetnova.ms_agenda.repository.CitaRepository;

@Service
public class CitaService {

    private static final Logger logger = LoggerFactory.getLogger(CitaService.class);

    private final CitaRepository repository;
    private final WebClient webClient;

    public CitaService(CitaRepository repository, WebClient webClient) {
        this.repository = repository;
        this.webClient = webClient;
    }

    public List<CitaResponseDTO> listar() {
        logger.info("Listando citas");

        return repository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public CitaResponseDTO buscarPorId(Long id) {
        logger.info("Buscando cita con ID: " + id);

        Cita cita = repository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Cita no encontrada con ID: " + id);
                    return new CitaNoEncontradaException("Cita no encontrada");
                });

        return convertirAResponse(cita);
    }

    public CitaResponseDTO guardar(CitaRequestDTO dto) {
        logger.info("Registrando cita para mascota ID: " + dto.getMascotaId());

        validarMascota(dto.getMascotaId());

        Cita cita = new Cita();

        cita.setMascotaId(dto.getMascotaId());
        cita.setFecha(dto.getFecha());
        cita.setHora(dto.getHora());
        cita.setVeterinario(dto.getVeterinario());
        cita.setMotivo(dto.getMotivo());
        cita.setEstado(normalizarEstado(dto.getEstado()));

        Cita citaGuardada = repository.save(cita);

        logger.info("Cita registrada con ID: " + citaGuardada.getId());

        return convertirAResponse(citaGuardada);
    }

    public CitaResponseDTO actualizar(Long id, CitaRequestDTO dto) {
        logger.info("Actualizando cita con ID: " + id);

        Cita cita = repository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Cita no encontrada con ID: " + id);
                    return new CitaNoEncontradaException("Cita no encontrada");
                });

        validarMascota(dto.getMascotaId());

        cita.setMascotaId(dto.getMascotaId());
        cita.setFecha(dto.getFecha());
        cita.setHora(dto.getHora());
        cita.setVeterinario(dto.getVeterinario());
        cita.setMotivo(dto.getMotivo());
        cita.setEstado(normalizarEstado(dto.getEstado()));

        Cita citaActualizada = repository.save(cita);

        logger.info("Cita actualizada con ID: " + citaActualizada.getId());

        return convertirAResponse(citaActualizada);
    }

    public void eliminar(Long id) {
        logger.info("Eliminando cita con ID: " + id);

        Cita cita = repository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Cita no encontrada con ID: " + id);
                    return new CitaNoEncontradaException("Cita no encontrada");
                });

        repository.delete(cita);

        logger.info("Cita eliminada con ID: " + id);
    }

    public List<MascotaDTO> obtenerMascotas() {
        try {
            logger.info("Consultando mascotas desde ms-mascotas");

            return webClient.get()
                    .uri("http://localhost:8081/api/mascotas")
                    .retrieve()
                    .bodyToFlux(MascotaDTO.class)
                    .collectList()
                    .block();

        } catch (Exception e) {
            logger.error("Error al comunicarse con ms-mascotas");

            throw new ErrorComunicacionException(
                    "No se pudieron obtener las mascotas. Verifica que ms-mascotas esté funcionando");
        }
    }

    private void validarMascota(Long mascotaId) {
        try {
            logger.info("Validando mascota con ID: " + mascotaId);

            webClient.get()
                    .uri("http://localhost:8081/api/mascotas/" + mascotaId)
                    .retrieve()
                    .bodyToMono(MascotaDTO.class)
                    .block();

        } catch (WebClientResponseException.NotFound e) {
            logger.error("Mascota no encontrada con ID: " + mascotaId);

            throw new MascotaNoEncontradaException(
                    "La mascota con ID " + mascotaId + " no existe");

        } catch (Exception e) {
            logger.error("Error al comunicarse con ms-mascotas");

            throw new ErrorComunicacionException(
                    "No se pudo validar la mascota. Verifica que ms-mascotas esté funcionando");
        }
    }

    private String normalizarEstado(String estado) {
        if (estado == null || estado.isBlank()) {
            return "AGENDADA";
        }

        String estadoNormalizado = estado.toUpperCase();

        if (!estadoNormalizado.equals("AGENDADA")
                && !estadoNormalizado.equals("CANCELADA")
                && !estadoNormalizado.equals("ATENDIDA")) {

            throw new IllegalArgumentException(
                    "El estado debe ser AGENDADA, CANCELADA o ATENDIDA");
        }

        return estadoNormalizado;
    }

    private CitaResponseDTO convertirAResponse(Cita cita) {
        return new CitaResponseDTO(
                cita.getId(),
                cita.getMascotaId(),
                cita.getFecha(),
                cita.getHora(),
                cita.getVeterinario(),
                cita.getMotivo(),
                cita.getEstado());
    }
}
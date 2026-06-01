package com.vetnova.ms_agenda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vetnova.ms_agenda.dto.MascotaDTO;
import com.vetnova.ms_agenda.model.Cita;
import com.vetnova.ms_agenda.repository.CitaRepository;

@Service
public class CitaService {

    private final CitaRepository repository;
    private final WebClient webClient;

    public CitaService(CitaRepository repository, WebClient webClient) {
        this.repository = repository;
        this.webClient = webClient;
    }

    public List<Cita> listar() {
        return repository.findAll();
    }

    public Cita guardar(Cita cita) {
        return repository.save(cita);
    }

    public List<MascotaDTO> obtenerMascotas() {

        return webClient.get()
                .uri("http://localhost:8081/api/mascotas")
                .retrieve()
                .bodyToFlux(MascotaDTO.class)
                .collectList()
                .block();
    }
}
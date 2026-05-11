package com.vetnova.ms_agenda.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vetnova.ms_agenda.model.Cita;
import com.vetnova.ms_agenda.repository.CitaRepository;

@Service
public class CitaService {

    private final CitaRepository repository;

    public CitaService(CitaRepository repository) {
        this.repository = repository;
    }

    public List<Cita> listar() {
        return repository.findAll();
    }

    public Cita guardar(Cita cita) {
        return repository.save(cita);
    }
}
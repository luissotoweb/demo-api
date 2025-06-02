package com.decode.api.demo.service;

import com.decode.api.demo.model.Persona;

import java.util.List;

public interface PersonaService {
    Persona guardarPersona(Persona persona);

    List<Persona> listarPersonas();

    Persona buscarPorId(Long id);
}

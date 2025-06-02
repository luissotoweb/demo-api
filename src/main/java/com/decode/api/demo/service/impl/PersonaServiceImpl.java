package com.decode.api.demo.service.impl;

import com.decode.api.demo.model.Persona;
import com.decode.api.demo.repository.PersonaRepository;
import com.decode.api.demo.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PersonaServiceImpl implements PersonaService {

    private static final Logger logger = LoggerFactory.getLogger(PersonaServiceImpl.class);

    private final PersonaRepository personaRepository;

    @Autowired
    public PersonaServiceImpl(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    public Persona guardarPersona(Persona persona) {
        logger.info("Intentando guardar persona: {}", persona);
        Persona guardada = personaRepository.save(persona);
        logger.info("Persona guardada exitosamente con id: {}", guardada.getId());
        return guardada;
    }

    @Override
    public List<Persona> listarPersonas() {
        logger.info("Listando todas las personas");
        List<Persona> personas = personaRepository.findAll();
        logger.info("Se encontraron {} personas", personas.size());
        return personas;
    }

    @Override
    public Persona buscarPorId(Long id) {
        logger.info("Buscando persona con id: {}", id);
        Persona persona =  personaRepository.findById(id).orElse(null);
        if (persona != null) {
            logger.info("Persona encontrada: {}", persona);
        } else {
            logger.info("No se encontró persona con id: {}", id);
        }
        return persona;
    }
}

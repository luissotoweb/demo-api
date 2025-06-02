package com.decode.api.demo.controller;

import com.decode.api.demo.model.Persona;
import com.decode.api.demo.service.PersonaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    private static final Logger logger = LoggerFactory.getLogger(PersonaController.class);

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @PostMapping
    public ResponseEntity<Persona> guardarPersona(@RequestBody Persona persona) {
        logger.info("Solicitud para guardar persona: {}", persona);
        Persona personaGuardada = personaService.guardarPersona(persona);
        logger.info("Persona guardada exitosamente con id: {}", personaGuardada.getId());
        return ResponseEntity.ok(personaGuardada);
    }

    @GetMapping
    public ResponseEntity<List<Persona>> listarPersonas() {
        logger.info("Solicitud para listar todas las personas");
        List<Persona> personas = personaService.listarPersonas();
        logger.info("Cantidad de personas encontradas: {}", personas.size());
        return ResponseEntity.ok(personas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> buscarPorId(@PathVariable Long id) {
        logger.info("Solicitud para buscar persona con id: {}", id);
        Persona persona = personaService.buscarPorId(id);
        if (persona != null) {
            logger.info("Persona encontrada: {}", persona);
            return ResponseEntity.ok(persona);
        } else {
            logger.info("No se encontró persona con id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> actualizarPersona(@PathVariable Long id, @RequestBody Persona personaActualizada) {
        logger.info("Solicitud para actualizar persona con id: {}", id);

        Persona personaExistente = personaService.buscarPorId(id);

        if (personaExistente == null) {
            logger.warn("No se encontró persona con id: {} para actualizar", id);
            return ResponseEntity.notFound().build();
        }

        personaExistente.setNombre(personaActualizada.getNombre());
        personaExistente.setApellido(personaActualizada.getApellido());
        personaExistente.setEdad(personaActualizada.getEdad());
        personaExistente.setEmail(personaActualizada.getEmail());

        Persona personaGuardada = personaService.guardarPersona(personaExistente);
        logger.info("Persona con id: {} actualizada correctamente", personaGuardada.getId());

        return ResponseEntity.ok(personaGuardada);
    }
}

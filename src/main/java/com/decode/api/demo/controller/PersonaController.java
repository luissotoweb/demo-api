package com.decode.api.demo.controller;

import com.decode.api.demo.model.Persona;
import com.decode.api.demo.service.PersonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Guardar una nueva persona",
            description = "Permite insertar un nuevo registro de persona en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona guardada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Persona.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<Persona> guardarPersona(
            @Parameter(description = "Objeto persona a guardar", required = true)
            @RequestBody Persona persona) {
        logger.info("Solicitud para guardar persona: {}", persona);
        Persona personaGuardada = personaService.guardarPersona(persona);
        logger.info("Persona guardada exitosamente con id: {}", personaGuardada.getId());
        return ResponseEntity.ok(personaGuardada);
    }

    @GetMapping
    @Operation(summary = "Listar todas las personas",
            description = "Obtiene una lista con todas las personas registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de personas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Persona.class)))
    })
    public ResponseEntity<List<Persona>> listarPersonas() {
        logger.info("Solicitud para listar todas las personas");
        List<Persona> personas = personaService.listarPersonas();
        logger.info("Cantidad de personas encontradas: {}", personas.size());
        return ResponseEntity.ok(personas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar persona por ID",
            description = "Busca una persona por su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Persona.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró la persona")
    })
    public ResponseEntity<Persona> buscarPorId(
            @Parameter(description = "ID de la persona a buscar", required = true)
            @PathVariable Long id) {
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
    @Operation(summary = "Actualizar persona por ID",
            description = "Actualiza los datos de una persona existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona actualizada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Persona.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró la persona para actualizar")
    })
    public ResponseEntity<Persona> actualizarPersona(
            @Parameter(description = "ID de la persona a actualizar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados de la persona", required = true)
            @RequestBody Persona personaActualizada) {

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

    @GetMapping("/buscar")
    @Operation(summary = "Buscar personas por nombre",
            description = "Retorna una lista de personas cuyo nombre contiene el texto indicado (case-insensitive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de personas encontrada"),
            @ApiResponse(responseCode = "204", description = "No se encontraron personas")
    })
    public ResponseEntity<List<Persona>> buscarPorNombre(
            @Parameter(description = "Nombre o parte del nombre para buscar", required = true)
            @RequestParam String nombre) {
        List<Persona> personas = personaService.buscarPorNombre(nombre);
        if (personas.isEmpty()) {
            logger.info("No se encontraron personas con el nombre: {}", nombre);
            return ResponseEntity.noContent().build();
        }
        logger.info("Se encontraron {} personas con el nombre: {}", personas.size(), nombre);
        return ResponseEntity.ok(personas);
    }
}

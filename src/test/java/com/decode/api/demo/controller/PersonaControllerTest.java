package com.decode.api.demo.controller;

import com.decode.api.demo.model.Persona;
import com.decode.api.demo.service.PersonaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonaController.class)
class PersonaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonaService personaService;

    private Persona persona;

    @BeforeEach
    void setUp() {
        persona = new Persona();
        persona.setId(1L);
        persona.setNombre("Luis");
        persona.setApellido("Soto");
        persona.setEdad(35);
        persona.setEmail("luis@example.com");
    }

    @Test
    void testGuardarPersona() throws Exception {
        when(personaService.guardarPersona(any(Persona.class))).thenReturn(persona);

        mockMvc.perform(post("/api/personas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(persona)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Luis")))
                .andExpect(jsonPath("$.apellido", is("Soto")))
                .andExpect(jsonPath("$.email", is("luis@example.com")));
    }

    @Test
    void testListarPersonas() throws Exception {
        when(personaService.listarPersonas()).thenReturn(Arrays.asList(persona));

        mockMvc.perform(get("/api/personas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre", is("Luis")));
    }

    @Test
    void testBuscarPersonaPorId_Existente() throws Exception {
        when(personaService.buscarPorId(1L)).thenReturn(persona);

        mockMvc.perform(get("/api/personas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Luis")));
    }

    @Test
    void testBuscarPersonaPorId_NoExistente() throws Exception {
        when(personaService.buscarPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/api/personas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testBuscarPorNombre() throws Exception {
        Persona persona1 = new Persona(1L, "Luis", "Soto", 35, "luis@example.com", null, null);
        Persona persona2 = new Persona(2L, "Luisa", "Perez", 28, "luisa@example.com", null, null);

        List<Persona> personas = Arrays.asList(persona1, persona2);

        when(personaService.buscarPorNombre("Luis")).thenReturn(personas);

        mockMvc.perform(get("/personas/buscar")
                        .param("nombre", "Luis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre").value("Luis"))
                .andExpect(jsonPath("$[1].nombre").value("Luisa"));
    }

    @Test
    public void testBuscarPorNombre_NoContent() throws Exception {
        when(personaService.buscarPorNombre("NoExiste")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/personas/buscar")
                        .param("nombre", "NoExiste"))
                .andExpect(status().isNoContent());
    }



    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

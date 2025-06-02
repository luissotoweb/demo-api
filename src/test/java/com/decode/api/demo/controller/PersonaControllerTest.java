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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
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
        Mockito.when(personaService.guardarPersona(any(Persona.class))).thenReturn(persona);

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
        Mockito.when(personaService.listarPersonas()).thenReturn(Arrays.asList(persona));

        mockMvc.perform(get("/api/personas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre", is("Luis")));
    }

    @Test
    void testBuscarPersonaPorId_Existente() throws Exception {
        Mockito.when(personaService.buscarPorId(1L)).thenReturn(persona);

        mockMvc.perform(get("/api/personas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Luis")));
    }

    @Test
    void testBuscarPersonaPorId_NoExistente() throws Exception {
        Mockito.when(personaService.buscarPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/api/personas/99"))
                .andExpect(status().isNotFound());
    }

    // Utilidad para convertir un objeto a JSON
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

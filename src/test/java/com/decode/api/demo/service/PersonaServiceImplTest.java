package com.decode.api.demo.service;

import com.decode.api.demo.model.Persona;
import com.decode.api.demo.repository.PersonaRepository;
import com.decode.api.demo.service.impl.PersonaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonaServiceImplTest {

    @Mock
    private PersonaRepository personaRepository;

    @InjectMocks
    private PersonaServiceImpl personaService;

    private Persona persona;

    @BeforeEach
    void setUp() {
        persona = new Persona();
        persona.setId(1L);
        persona.setNombre("Luis");
        persona.setApellido("Soto");
        persona.setEdad(40);
        persona.setEmail("luis@example.com");
    }

    @Test
    void testGuardar() {
        when(personaRepository.save(persona)).thenReturn(persona);
        Persona resultado = personaService.guardarPersona(persona);
        assertNotNull(resultado);
        assertEquals("Luis", resultado.getNombre());
        verify(personaRepository, times(1)).save(persona);
    }

    @Test
    void testListarPersonas() {
        when(personaRepository.findAll()).thenReturn(Arrays.asList(persona));
        List<Persona> personas = personaService.listarPersonas();
        assertEquals(1, personas.size());
        assertEquals("Luis", personas.get(0).getNombre());
        verify(personaRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId_existente() {
        when(personaRepository.findById(1L)).thenReturn(Optional.of(persona));
        Persona resultado = personaService.buscarPorId(1L);
        assertNotNull(resultado);
        assertEquals("Luis", resultado.getNombre());
        verify(personaRepository, times(1)).findById(1L);
    }

    @Test
    void testBuscarPorId_noExistente() {
        when(personaRepository.findById(99L)).thenReturn(Optional.empty());
        Persona resultado = personaService.buscarPorId(99L);
        assertNull(resultado);
        verify(personaRepository, times(1)).findById(99L);
    }
}

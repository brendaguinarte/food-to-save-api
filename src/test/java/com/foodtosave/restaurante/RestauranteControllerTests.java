package com.foodtosave.restaurante;

import com.foodtosave.restaurante.api.RestauranteController;
import com.foodtosave.restaurante.domain.model.Restaurante;
import com.foodtosave.restaurante.domain.service.RestauranteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = RestauranteController.class)
public class RestauranteControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestauranteService restauranteService;

    @Test
    void testDelete() throws Exception {
        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);

        when(restauranteService.buscaPorId(1L)).thenReturn(restaurante);

        mockMvc.perform(delete("/api/v1/restaurante/1"))
                .andExpect(status().isNoContent());

        verify(restauranteService).deletar(1L);
    }


    @Test
    void testGet() throws Exception {
        Restaurante mockRestaurante1 = new Restaurante();
        mockRestaurante1.setId(1L);
        mockRestaurante1.setNome("Restaurante A");

        Restaurante mockRestaurante2 = new Restaurante();
        mockRestaurante2.setId(2L);
        mockRestaurante2.setNome("Restaurante B");

        List<Restaurante> mockRestaurantes = Arrays.asList(mockRestaurante1, mockRestaurante2);
        when(restauranteService.buscaTodos()).thenReturn(mockRestaurantes);

        mockMvc.perform(get("/api/v1/restaurante"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Restaurante A"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].nome").value("Restaurante B"));
    }
}
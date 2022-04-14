package com.desafioItau.controllers;

import com.desafioItau.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) ->new MappingJackson2JsonView()).build();

    }

    @Test
    void salvarCliente() {


    }

    @Test
    @DisplayName("")
    void listarCliente() {


    }

    @Test
    @DisplayName("")
    void obterCliente(){

    }

    @Test
    void obterClienteCnpj() {
    }

    @Test
    void atualizarCliente() {
    }

    @Test
    void atualizarClienteCnpj() {
    }

    @Test
    void deletarCliente() {
    }

    @Test
    void deletarClienteCnpj() {
    }
}
package com.desafioItau.controllers;

import com.desafioItau.dtos.ClienteDto;
import com.desafioItau.entidades.ClienteEntidade;
import com.desafioItau.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @Mock
    private ModelMapper modelMapper;

    private ClienteEntidade clienteEntidade;
    private ClienteDto clienteDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startClienteEntidade();
    }

    @Test
    void salvarCliente() {
        when(clienteService.criarCliente(any())).thenReturn(clienteEntidade);

        ResponseEntity<?> resposta = clienteController.salvarCliente(clienteDto);

        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());


    }

    @Test
    @DisplayName("")
    void listarCliente() {
        when(clienteService.findAll()).thenReturn(List.of(clienteEntidade));

        ResponseEntity<List<ClienteEntidade>> resposta = clienteController.listarCliente();

        assertNotNull(resposta);

    }

    @Test
    @DisplayName("")
    void obterCliente() {
        when(clienteService.obter(anyString())).thenReturn(clienteEntidade);
        when(modelMapper.map(any(), any())).thenReturn(clienteDto);

        ResponseEntity<?> resposta = clienteController.obterCliente(clienteEntidade.getCpf());

        assertNotNull(resposta);


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

    private void startClienteEntidade(){
        ClienteEntidade cliente = new ClienteEntidade(1,"leonardo",
                "04537183373", "61.962.903/0001-48", "085981543671");
    }
}
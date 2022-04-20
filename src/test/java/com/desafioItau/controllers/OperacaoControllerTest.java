package com.desafioItau.controllers;

import com.desafioItau.dtos.OperacaoDto;
import com.desafioItau.entidades.OperacaoEntidade;
import com.desafioItau.services.OperacaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static com.desafioItau.enums.EnumOperacao.DEPOSITO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)  //junit5
@ActiveProfiles("test")   //rodar no perfil de teste
@MockitoSettings(strictness = Strictness.LENIENT)
class OperacaoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private OperacaoController operacaoController;

    @Mock
    private OperacaoService operacaoService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(operacaoController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void depositar() {
        //cenario
        when(operacaoService.depositar(any())).thenReturn(mockOperacaoEntidade());

        //ação ou execução
        var result = operacaoController.depositar(mockOperacaoDto());

        //verificação
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(mockOperacaoEntidade(), result.getBody());
    }

    @Test
    void sacar() {
        //cenario
        when(operacaoService.sacar(any())).thenReturn(mockOperacaoEntidade());

        //ação ou execução
        var result = operacaoController.sacar(mockOperacaoDto());

        //verificação
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(mockOperacaoEntidade(), result.getBody());
    }

    @Test
    void transferencia() {
        //cenario
        when(operacaoService.transferencia(any())).thenReturn(mockOperacaoEntidade());

        //ação ou execução
        var result = operacaoController.transferencia(mockOperacaoDto());

        //verificação
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(mockOperacaoEntidade(), result.getBody());
    }

    @Test
    void saldo() {
        when(operacaoService.saldo(any())).thenReturn(mockOperacaoEntidadeSaldo());

        var result = operacaoController.saldo("12345");

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertNotNull(result.getBody());
    }

    @Test
    void extrato() {
        //cenario
        when(operacaoService.extrato(any())).thenReturn(Collections.singletonList(mockOperacaoEntidade()));

        //ação ou execução
        var result = operacaoController.extrato(String.valueOf(mockOperacaoEntidade()));

        //verificação
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertNotNull(result.getBody());
    }

    private BigDecimal mockOperacaoEntidadeSaldo() {
        OperacaoEntidade operacao = OperacaoEntidade.builder().id(1L).numeroDaConta("12345").saldo(BigDecimal.valueOf(0)).build();
        return operacao.getSaldo();
    }

    private OperacaoEntidade mockOperacaoEntidade() {
        return OperacaoEntidade.builder()
                .id(1L).numeroDaConta("12345").numeroDaContaDestino("54321").tipoDaOperacao(DEPOSITO)
                .valorDaTransação(BigDecimal.valueOf(100)).taxa(BigDecimal.valueOf(0))
                .saldo(BigDecimal.valueOf(0)).mensagem("ok").aviso("sucesso").build();
    }

    private OperacaoDto mockOperacaoDto() {
        return OperacaoDto.builder()
                .id(1L).numeroDaConta("12345").numeroDaContaDestino("54321").tipoDaOperacao(DEPOSITO)
                .valorDaTransação(BigDecimal.valueOf(100)).taxa(BigDecimal.valueOf(0))
                .saldo(BigDecimal.valueOf(0)).mensagem("ok").aviso("sucesso").build();
    }
}
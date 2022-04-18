package com.desafioItau.services;

import com.desafioItau.entidades.OperacaoEntidade;
import com.desafioItau.repositorys.OperacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OperacaoServiceTest {

    @InjectMocks
    private OperacaoService operacaoService;

    @Mock
    private OperacaoRepository operacaoRepository;

    @Mock
    OperacaoEntidade operacaoEntidade;

    @Test
    void depositar() {
//       //cenario
//        OperacaoDto operacaoDto = OperacaoDto.builder().id(1L).numeroDaConta("1415").numeroDaContaDestino("12345")
//                .tipoDaOperacao(DEPOSITO).valorDaTransação(BigDecimal.valueOf(1)).taxa(BigDecimal.valueOf(10))
//                .saldo(BigDecimal.valueOf(0)).mensagem("sucesso").aviso("ok").build();
//
//        OperacaoDto operacaoEntidade = new OperacaoDto();
//        BeanUtils.copyProperties(operacaoDto, operacaoEntidade);
//
//        //execucao
//        when(operacaoRepository.save(operacaoEntidade)).thenReturn(operacaoEntidade);
//
//        OperacaoEntidade operacaoSalva = operacaoService.depositar(operacaoEntidade);
//
//        //verificacao
//        assertThat(operacaoSalva.getId()).isNotNull();
//        assertThat(operacaoSalva.getNumeroDaConta()).isEqualTo("1415");
//        assertThat(operacaoSalva.getNumeroDaContaDestino()).isEqualTo("12345");
//        assertThat(operacaoSalva.getTipoDaOperacao()).isEqualTo(DEPOSITO);
//        assertThat(operacaoSalva.getValorDaTransação()).isEqualTo(1);
//        assertThat(operacaoSalva.getTaxa()).isEqualTo(10);
//        assertThat(operacaoSalva.getSaldo()).isEqualTo(0);
//        assertThat(operacaoSalva.getMensagem()).isEqualTo("sucesso");
//        assertThat(operacaoSalva.getAviso()).isEqualTo("ok");
//        verify(operacaoRepository, times(1)).
//        verify(operacaoRepository, times(1)).save(operacaoEntidade);
    }

    @Test
    void sacar() {
//        //cenario
//        OperacaoDto operacaoDto = OperacaoDto.builder().id(1L).numeroDaConta("1415").numeroDaContaDestino("12345")
//                .tipoDaOperacao(SAQUE).valorDaTransação(BigDecimal.valueOf(1)).taxa(BigDecimal.valueOf(10))
//                .saldo(BigDecimal.valueOf(0)).mensagem("sucesso").aviso("ok").build();
//
//        OperacaoDto operacaoEntidade = new OperacaoDto();
//        BeanUtils.copyProperties(operacaoDto, operacaoEntidade);
//
//        //execucao
//        when(operacaoRepository.save(operacaoEntidade)).thenReturn(operacaoEntidade);
//
//        OperacaoEntidade operacaoSalva = operacaoService.depositar(operacaoEntidade);
//
//        //verificacao
//        assertThat(operacaoSalva.getId()).isNotNull();
//        assertThat(operacaoSalva.getNumeroDaConta()).isEqualTo("1415");
//        assertThat(operacaoSalva.getNumeroDaContaDestino()).isEqualTo("12345");
//        assertThat(operacaoSalva.getTipoDaOperacao()).isEqualTo(DEPOSITO);
//        assertThat(operacaoSalva.getValorDaTransação()).isEqualTo(1);
//        assertThat(operacaoSalva.getTaxa()).isEqualTo(10);
//        assertThat(operacaoSalva.getSaldo()).isEqualTo(0);
//        assertThat(operacaoSalva.getMensagem()).isEqualTo("sucesso");
//        assertThat(operacaoSalva.getAviso()).isEqualTo("ok");
//        verify(operacaoRepository, times(1)).
//        verify(operacaoRepository, times(1)).save(operacaoEntidade);
    }

    @Test
    void transferencia() {
    }

    @Test
    void saldo() {
    }

    @Test
    void extrato() {
    }
}
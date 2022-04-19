package com.desafioItau.services;

import com.desafioItau.dtos.OperacaoDto;
import com.desafioItau.entidades.ContaEntidade;
import com.desafioItau.entidades.OperacaoEntidade;
import com.desafioItau.enums.EnumTipoDaConta;
import com.desafioItau.repositorys.ContaRepository;
import com.desafioItau.repositorys.OperacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

import static com.desafioItau.enums.EnumOperacao.DEPOSITO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OperacaoServiceTest {

    @InjectMocks
    private OperacaoService operacaoService;

    @Mock
    private OperacaoRepository operacaoRepository;

    @Mock
    private ContaRepository contaRepository;

    @Test
    void depositar() {
       //cenario
        OperacaoEntidade operacaoEntidade = new OperacaoEntidade();
        BeanUtils.copyProperties(operacaoDtoMock(), operacaoEntidade);

        //execucao
        when(operacaoRepository.save(operacaoEntidade)).thenReturn(operacaoEntidade);

        when(contaRepository.findContaByNumeroDaConta(any())).thenReturn(ContaEntidadeMock());

        OperacaoEntidade operacaoSalva = operacaoService.depositar(operacaoEntidade);

        //verificacao
        assertThat(operacaoSalva.getId()).isNotNull();
        assertThat(operacaoSalva.getNumeroDaConta()).isEqualTo("1415");
        assertThat(operacaoSalva.getNumeroDaContaDestino()).isEqualTo("12345");
        assertThat(operacaoSalva.getTipoDaOperacao()).isEqualTo(DEPOSITO);
        assertThat(operacaoSalva.getValorDaTransação()).isEqualTo(new BigDecimal(1));
        assertThat(operacaoSalva.getTaxa()).isEqualTo(new BigDecimal(10));
        assertThat(operacaoSalva.getSaldo()).isEqualTo(new BigDecimal("11.0"));
        assertThat(operacaoSalva.getMensagem()).isEqualTo("sucesso");
        assertThat(operacaoSalva.getAviso()).isEqualTo("ok");
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

    private OperacaoDto operacaoDtoMock() {
        return OperacaoDto.builder().id(1L).numeroDaConta("1415").numeroDaContaDestino("12345")
                .tipoDaOperacao(DEPOSITO).valorDaTransação(BigDecimal.valueOf(1)).taxa(BigDecimal.valueOf(10))
                .saldo(new BigDecimal(1000)).mensagem("sucesso").aviso("ok").build();
    }

    private ContaEntidade ContaEntidadeMock() {
        return ContaEntidade.builder().id(1L).agencia("1515").numeroDaConta("1415").tipoDaConta(EnumTipoDaConta.PESSOA_FISICA)
                .digitoVerificador(7).clienteCpf("045.371.833-73").clienteCnpj("90.383.151/0001-69").saldo(new BigDecimal(10))
                .saqueSemTaxa(10).aviso("ok").build();

    }
}
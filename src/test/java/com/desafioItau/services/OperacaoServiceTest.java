package com.desafioItau.services;

import com.desafioItau.dtos.OperacaoDto;
import com.desafioItau.entidades.ContaEntidade;
import com.desafioItau.entidades.OperacaoEntidade;
import com.desafioItau.enums.EnumOperacao;
import com.desafioItau.enums.EnumTipoDaConta;
import com.desafioItau.exceptions.TransacaoException;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OperacaoServiceTest {

    @InjectMocks
    private OperacaoService operacaoService;

    @Mock
    private OperacaoRepository operacaoRepository;

    @Mock
    private ContaService contaService;

    @Mock
    private ProducerOperacaoSaqueService producerOperacaoSaqueService;

    @Mock
    private ContaRepository contaRepository;

    public static final String NUMERO_DA_CONTA = "12345";
    public static final String NUMERO_DA_CONTA_DESTINO = "12344";

    @Test
    void depositar() {
       //cenario
        OperacaoEntidade operacaoEntidade = new OperacaoEntidade();
        BeanUtils.copyProperties(operacaoDtoMockDeposito(), operacaoEntidade);

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
        OperacaoEntidade operacaoEsperada = buildOperacaoSaque();
        operacaoEsperada.setSaldo(BigDecimal.valueOf(9.0));
        operacaoEsperada.setMensagem("saque realizado com sucesso");
        operacaoEsperada.setAviso("voce possui 9 saques gratuitos");

        when(contaRepository.findContaByNumeroDaConta(NUMERO_DA_CONTA)).thenReturn(buildConta());
        when(operacaoRepository.save(eq(operacaoEsperada))).thenReturn(operacaoEsperada);

        OperacaoEntidade operacaoRetornada = operacaoService.sacar(buildOperacaoSaque());

        assertEquals(operacaoEsperada, operacaoRetornada);
        verify(contaRepository, times(1)).findContaByNumeroDaConta(NUMERO_DA_CONTA);
        //verify(producerOperacaoSaqueService, times(1)).send(any());
        verify(contaRepository, times(1)).save(any());
        verify(operacaoRepository, times(1)).save(any());

    }

    @Test
    void transferencia() {
        OperacaoEntidade operacaoEsperada = buildOperacaoTransferencia();
        operacaoEsperada.setSaldo(BigDecimal.valueOf(9.0));

        when(contaRepository.findContaByNumeroDaConta(NUMERO_DA_CONTA)).thenReturn(buildConta());
        when(contaRepository.findContaByNumeroDaConta(NUMERO_DA_CONTA_DESTINO)).thenReturn(buildConta());
        when(operacaoRepository.save(eq(operacaoEsperada))).thenReturn(operacaoEsperada);

        OperacaoEntidade operacaoRetornada = operacaoService.transferencia(buildOperacaoTransferencia());

        assertEquals(operacaoEsperada, operacaoRetornada);
        verify(contaRepository, times(1)).findContaByNumeroDaConta(NUMERO_DA_CONTA);
        verify(contaRepository, times(1)).findContaByNumeroDaConta(NUMERO_DA_CONTA_DESTINO);
        verify(operacaoRepository, times(1)).save(any());
    }

    @Test
    void deveFalharATransferenciaQuandoContaOrigemForIgualADestino() {
        OperacaoEntidade operacaoEsperada = buildOperacaoTransferencia();
        operacaoEsperada.setNumeroDaConta(NUMERO_DA_CONTA);
        operacaoEsperada.setNumeroDaContaDestino(NUMERO_DA_CONTA);
        operacaoEsperada.setSaldo(BigDecimal.valueOf(9.0));

        when(contaRepository.findContaByNumeroDaConta(NUMERO_DA_CONTA)).thenReturn(buildConta());
        when(contaRepository.findContaByNumeroDaConta(NUMERO_DA_CONTA_DESTINO)).thenReturn(buildConta());
        when(operacaoRepository.save(eq(operacaoEsperada))).thenReturn(operacaoEsperada);

        assertThrows(TransacaoException.class, () -> operacaoService.transferencia(operacaoEsperada));
    }

    @Test
    void deveFalharATransferenciaQuandoSaldoForInsuficiente() {
        OperacaoEntidade operacaoEsperada = buildOperacaoTransferenciaFail();
        operacaoEsperada.setSaldo(BigDecimal.valueOf(1));

        when(contaRepository.findContaByNumeroDaConta(NUMERO_DA_CONTA)).thenReturn(buildConta());
        when(contaRepository.findContaByNumeroDaConta(NUMERO_DA_CONTA_DESTINO)).thenReturn(buildConta());
        when(operacaoRepository.save(eq(operacaoEsperada))).thenReturn(operacaoEsperada);

        assertThrows(TransacaoException.class, () -> operacaoService.transferencia(operacaoEsperada));
    }

    @Test
    void saldo() {
        OperacaoEntidade operacaoEntidade = new OperacaoEntidade();
        operacaoEntidade.setSaldo(BigDecimal.valueOf(100));

        when(contaRepository.findContaByNumeroDaConta(NUMERO_DA_CONTA)).thenReturn(buildConta());

        verify(contaRepository, times(1)).findContaByNumeroDaConta(NUMERO_DA_CONTA);
        verify(contaRepository, times(1)).save(any());
    }

    @Test
    void extrato() {
        OperacaoEntidade operacaoEntidade = new OperacaoEntidade();


    }

    private OperacaoDto operacaoDtoMockDeposito() {
        return OperacaoDto.builder().id(1L).numeroDaConta("1415").numeroDaContaDestino("12345")
                .tipoDaOperacao(DEPOSITO).valorDaTransação(BigDecimal.valueOf(1)).taxa(BigDecimal.valueOf(10))
                .saldo(new BigDecimal(1000)).mensagem("sucesso").aviso("ok").build();
    }

    private ContaEntidade ContaEntidadeMock() {
        return ContaEntidade.builder().id(1L).agencia("1515").numeroDaConta("1415").tipoDaConta(EnumTipoDaConta.PESSOA_FISICA)
                .digitoVerificador(7).clienteCpf("045.371.833-73").clienteCnpj("90.383.151/0001-69").saldo(new BigDecimal(10))
                .saqueSemTaxa(10).aviso("ok").build();
    }

    private static ContaEntidade buildConta(){
        return ContaEntidade.builder().numeroDaConta(NUMERO_DA_CONTA).tipoDaConta(EnumTipoDaConta.PESSOA_FISICA)
                .saldo(BigDecimal.TEN).build();
    }

    private static OperacaoEntidade buildOperacaoSaque() {
        return OperacaoEntidade.builder().tipoDaOperacao(EnumOperacao.SAQUE).numeroDaConta(NUMERO_DA_CONTA)
                .valorDaTransação(BigDecimal.ONE).build();
    }

    private static OperacaoEntidade buildOperacaoSaqueFail() {
        return OperacaoEntidade.builder().tipoDaOperacao(EnumOperacao.SAQUE).numeroDaConta(NUMERO_DA_CONTA)
                .valorDaTransação(BigDecimal.valueOf(100)).build();
    }

    private static OperacaoEntidade buildOperacaoDeposito() {
        return OperacaoEntidade.builder().tipoDaOperacao(DEPOSITO).numeroDaConta(NUMERO_DA_CONTA)
                .valorDaTransação(BigDecimal.ONE).build();
    }

    private static OperacaoEntidade buildOperacaoTransferencia() {
        return OperacaoEntidade.builder().tipoDaOperacao(EnumOperacao.TRANSFERENCIA).numeroDaConta(NUMERO_DA_CONTA)
                .numeroDaContaDestino(NUMERO_DA_CONTA_DESTINO).valorDaTransação(BigDecimal.ONE).build();
    }

    private static OperacaoEntidade buildOperacaoTransferenciaFail() {
        return OperacaoEntidade.builder().tipoDaOperacao(EnumOperacao.TRANSFERENCIA).numeroDaConta(NUMERO_DA_CONTA)
                .numeroDaContaDestino(NUMERO_DA_CONTA_DESTINO).valorDaTransação(BigDecimal.valueOf(10)).build();
    }
}
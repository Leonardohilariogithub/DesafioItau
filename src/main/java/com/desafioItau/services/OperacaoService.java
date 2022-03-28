package com.desafioItau.services;

import com.desafioItau.entidades.ContaEntidade;
import com.desafioItau.entidades.OperacaoEntidade;
import com.desafioItau.enums.EnumOperacao;
import com.desafioItau.enums.EnumTipoDaConta;
import com.desafioItau.exceptions.ClienteCpfException;
import com.desafioItau.exceptions.ContaNaoEncontradaException;
import com.desafioItau.exceptions.OperacoesException;
import com.desafioItau.exceptions.SaldoInsuficienteException;
import com.desafioItau.repositorys.ContaRepository;
import com.desafioItau.repositorys.OperacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OperacaoService {

    private static final String QUANTIDADE_DE_SAQUES_GRATUITOS = " Você possui %d saques gratuitos";
    private static final String LIMITE_DE_SAQUES_ATIGIDO = "Limite de saques atingido!, " + "Foi cobrado uma taxa adicional %s";

    private final OperacaoRepository operacaoRepository; //Utilizar metodos prontos do JPARepository
    private final ContaRepository contaRepository;
    private final ContaService contaService;

    private final ProducerSaqueService producerSaqueService; //kafka

    public OperacaoEntidade depositar(OperacaoEntidade operacaoEntidade) {
        if (operacaoEntidade.getValorDaTransação().doubleValue() <= 0.0) {
            throw new ClienteCpfException(" o valor nao é valido!"); // exeception OPERAÇOES
        }
        ContaEntidade conta = contaRepository.findContaByNumeroDaConta(operacaoEntidade.getNumeroDaConta());
        if (Objects.nonNull(conta)) {
            double valorSaldo = conta.getSaldo().doubleValue();
            double valorDeposito = operacaoEntidade.getValorDaTransação().doubleValue();
            double novoValorSaldo = valorSaldo + valorDeposito;
            conta.setSaldo(BigDecimal.valueOf(novoValorSaldo));
            operacaoEntidade.setSaldo(BigDecimal.valueOf(novoValorSaldo));
        } else {
            throw new ClienteCpfException(String.format("Conta de numero %s nao encontrado!", operacaoEntidade.getNumeroDaConta()));
        }
        contaRepository.save(conta);
        operacaoEntidade.setTipoDaOperacao(EnumOperacao.DEPOSITO);
        return operacaoRepository.save(operacaoEntidade);
    }

    @Transactional
    public OperacaoEntidade sacar(OperacaoEntidade operacaoEntidade) {
        ContaEntidade contaEntidade = Optional.ofNullable(contaRepository.findContaByNumeroDaConta(operacaoEntidade.getNumeroDaConta())).orElseThrow(() -> {
            throw new ContaNaoEncontradaException(
                    String.format("Conta não encontrada, %s", operacaoEntidade.getNumeroDaConta()));
        });
        double valorSaque = operacaoEntidade.getValorDaTransação().doubleValue();

        double valorSaldo = contaEntidade.getSaldo().doubleValue();

        String alerta = null;

        double taxa = EnumTipoDaConta.PESSOA_FISICA.getTaxa();

        EnumTipoDaConta tipoDaConta = contaEntidade.getTipoDaConta();
        String numConta = contaEntidade.getNumeroDaConta();

        var quantidaDeSaque = contaEntidade.getSaqueSemTaxa();

        if (valorSaque > valorSaldo){
            throw new SaldoInsuficienteException(String.format(
                    "Saldo insuficiente! SALDO: R$ %s", valorSaldo
            ));
        }
        if (tipoDaConta == EnumTipoDaConta.PESSOA_FISICA || tipoDaConta == EnumTipoDaConta.PESSOA_JURIDICA) {

            if (quantidaDeSaque > 0) {
                double novoValorSaldo = valorSaldo - valorSaque;
                contaEntidade.setSaldo(BigDecimal.valueOf(novoValorSaldo));
                quantidaDeSaque--;

                contaEntidade.setSaqueSemTaxa(Math.toIntExact(quantidaDeSaque));

                alerta = String.format(QUANTIDADE_DE_SAQUES_GRATUITOS, contaEntidade.getSaqueSemTaxa());

            } else {
                if (valorSaque + taxa > valorSaldo) {
                    throw new SaldoInsuficienteException(String.format(
                            "Saldo insuficiente para sacar devida a taxa de %s", taxa));
                }

                double novoValorSaldo = valorSaldo - (valorSaque + taxa);
                contaEntidade.setSaldo(BigDecimal.valueOf(novoValorSaldo));
                operacaoEntidade.setTaxa(BigDecimal.valueOf(taxa));

                alerta = String.format(LIMITE_DE_SAQUES_ATIGIDO, operacaoEntidade.getTaxa());
            }

        } else if (tipoDaConta == EnumTipoDaConta.GOVERNAMENTAL) {
            taxa = EnumTipoDaConta.GOVERNAMENTAL.getTaxa();

            if (quantidaDeSaque > 0) {
                double novoValorSaldo = valorSaldo - valorSaque;
                contaEntidade.setSaldo(BigDecimal.valueOf(novoValorSaldo));

                quantidaDeSaque--;

                contaEntidade.setSaqueSemTaxa(Math.toIntExact(quantidaDeSaque));

                alerta = String.format(QUANTIDADE_DE_SAQUES_GRATUITOS, contaEntidade.getSaqueSemTaxa());

            } else {
                if (valorSaque + taxa > valorSaldo) {
                    throw new SaldoInsuficienteException(String.format(
                            "Saldo insuficiente para sacar devida a taxa de %s", taxa));
                }

                double novoValorSaldo = valorSaldo - (valorSaque + taxa);
                contaEntidade.setSaldo(BigDecimal.valueOf(novoValorSaldo));
                operacaoEntidade.setTaxa(BigDecimal.valueOf(taxa));

                alerta = String.format(LIMITE_DE_SAQUES_ATIGIDO, operacaoEntidade.getTaxa());
            }
        }
        contaRepository.save(contaEntidade);
        operacaoEntidade.setSaldo(contaEntidade.getSaldo());
        operacaoEntidade.setMensagem("Saque realizado com sucesso!");
        operacaoEntidade.setAviso(alerta);
        operacaoEntidade.setTipoDaOperacao(EnumOperacao.SAQUE);

        producerSaqueService.send(operacaoEntidade);

        return operacaoRepository.save(operacaoEntidade);
    }

    public OperacaoEntidade transferencia(OperacaoEntidade operacaoEntidade) {
        if (operacaoEntidade.getValorDaTransação().doubleValue() <= 0.0) {
            throw new OperacoesException(" o valor nao é valido!");
        }
        ContaEntidade conta = contaRepository.findContaByNumeroDaConta(operacaoEntidade.getNumeroDaConta());
        if (Objects.isNull(conta)) {
            throw new ClienteCpfException(String.format("Conta de numero %s nao encontrado!", operacaoEntidade.getNumeroDaConta()));
        }
        ContaEntidade contaDestino = contaRepository.findContaByNumeroDaConta(operacaoEntidade.getNumeroDaContaDestino());
        if (Objects.isNull(contaDestino)) {
            throw new ClienteCpfException(String.format("Conta de numero %s nao encontrado!", operacaoEntidade.getNumeroDaContaDestino()));
        }
        if (conta.getSaldo().doubleValue() - operacaoEntidade.getValorDaTransação().doubleValue() < 0) {
            throw new ClienteCpfException(String.format(" A conta não tem saldo para operação!!!", operacaoEntidade.getNumeroDaContaDestino()));
        }

        double saldoContaOrigem = conta.getSaldo().doubleValue();
        double saldoContaDestino = contaDestino.getSaldo().doubleValue();
        double valorTransferencia = operacaoEntidade.getValorDaTransação().doubleValue();

        double novoSaldoContaOrigem = saldoContaOrigem -= valorTransferencia;
        double novoSaldoContaDestino = saldoContaDestino += valorTransferencia;

        conta.setSaldo(BigDecimal.valueOf(novoSaldoContaOrigem));
        contaDestino.setSaldo(BigDecimal.valueOf(novoSaldoContaDestino));

        contaRepository.save(conta);
        contaRepository.save(contaDestino);

        operacaoEntidade.setSaldo(BigDecimal.valueOf(novoSaldoContaOrigem));

        operacaoEntidade.setTipoDaOperacao(EnumOperacao.TRANSFERENCIA);

        return operacaoRepository.save(operacaoEntidade);

    }

    public BigDecimal saldo(String numeroDaConta) {
        ContaEntidade conta = contaRepository.findContaByNumeroDaConta(numeroDaConta);
        if (Objects.isNull(conta)) {
            throw new ClienteCpfException(String.format("Conta de numero %s nao encontrado!", numeroDaConta));
        }
        return conta.getSaldo();
    }

    public List<OperacaoEntidade> extrato(String numeroDaConta) {
        List<OperacaoEntidade> operacoes = operacaoRepository.findExtratoByNumeroDaConta(numeroDaConta);
        List<OperacaoEntidade> operacoesDestino = operacaoRepository.findExtratoByNumeroDaContaDestino(numeroDaConta);

        operacoes.addAll(operacoesDestino);

        if (operacoes.size() > 0) {
            for (OperacaoEntidade operacaoEntidade : operacoes) {
                operacaoEntidade.getNumeroDaConta();
            }
        } else {
            throw new ContaNaoEncontradaException(
                    String.format("Conta não encontrada, por favor verificar", numeroDaConta));
        }
        return operacoes;
    }
}


package com.desafioItau.services;

import com.desafioItau.entidades.ContaEntidade;
import com.desafioItau.entidades.OperacaoEntidade;
import com.desafioItau.enums.EnumOperacao;
import com.desafioItau.exceptions.ClienteCpfException;
import com.desafioItau.repositorys.ContaRepository;
import com.desafioItau.repositorys.OperacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OperacaoService {

    private final OperacaoRepository operacaoRepository; //Utilizar metodos prontos do JPARepository
    private final ContaRepository contaRepository;
    private final ContaService contaService;

    public OperacaoEntidade depositar(OperacaoEntidade operacaoEntidade) {
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
}
//    @Transactional// evita dados quebrados
//    public OperacaoEntidade save(OperacaoEntidade operacaoEntidade) {
//        return operacaoRepository.save(operacaoEntidade);
//    }
//
//    public List<OperacaoEntidade> findAll() {
//        return operacaoRepository.findAll();
//    }
//
//    public Optional<OperacaoEntidade> findById(Long id) {
//        return operacaoRepository.findById(id);
//    }
//
//    @Transactional// evita dados quebrados
//    public void delete(OperacaoEntidade operacaoEntidade) {
//        operacaoRepository.delete(operacaoEntidade);
//    }
//
//    public OperacaoEntidade atualizar(Long id, OperacaoEntidade operacaoAtualizada) {
//        OperacaoEntidade conta = operacaoRepository.getById(id);
//        operacaoAtualizada.setId(conta.getId());
//        operacaoAtualizada.setRegistro(LocalDateTime.now(ZoneId.of("UTC")));
//        return operacaoRepository.save(operacaoAtualizada);
//    }


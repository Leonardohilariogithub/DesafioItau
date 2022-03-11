package com.desafioItau.services;

import com.desafioItau.entidades.OperacaoEntidade;
import com.desafioItau.repositorys.OperacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OperacaoService {

    private final OperacaoRepository operacaoRepository; //Utilizar metodos prontos do JPARepository

    @Transactional// evita dados quebrados
    public OperacaoEntidade save(OperacaoEntidade operacaoEntidade) {
        return operacaoRepository.save(operacaoEntidade);
    }

    public List<OperacaoEntidade> findAll() {
        return operacaoRepository.findAll();
    }

    public Optional<OperacaoEntidade> findById(Long id) {
        return operacaoRepository.findById(id);
    }

    @Transactional// evita dados quebrados
    public void delete(OperacaoEntidade operacaoEntidade) {
        operacaoRepository.delete(operacaoEntidade);
    }

    public OperacaoEntidade atualizar(Long id, OperacaoEntidade operacaoAtualizada) {
        OperacaoEntidade conta = operacaoRepository.getById(id);
        operacaoAtualizada.setId(conta.getId());
        operacaoAtualizada.setRegistro(LocalDateTime.now(ZoneId.of("UTC"))); // .setRegistro(LocalDateTime.now(ZoneId.of("UTC")));
        return operacaoRepository.save(operacaoAtualizada);
    }
}

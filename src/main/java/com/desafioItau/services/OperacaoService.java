package com.desafioItau.services;

import com.desafioItau.entidades.OperacaoEntidade;
import com.desafioItau.repositorys.OperacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
}

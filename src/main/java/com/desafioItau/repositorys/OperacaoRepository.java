package com.desafioItau.repositorys;

import com.desafioItau.entidades.OperacaoEntidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OperacaoRepository extends JpaRepository<OperacaoEntidade, Long> {
    List<OperacaoEntidade> findExtratoByNumeroDaConta(String numeroDaConta);

    List<OperacaoEntidade> findExtratoByNumeroDaContaDestino(String numeroDaConta);
}

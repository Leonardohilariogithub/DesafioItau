package com.desafioItau.repositorys;

import com.desafioItau.entidades.OperacaoEntidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperacaoRepository extends JpaRepository<OperacaoEntidade, Long> {

}

package com.desafioItau.repositorys;

import com.desafioItau.entidades.ContaEntidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContaRepository extends JpaRepository<ContaEntidade, Long> {
    ContaEntidade findContaByNumeroDaConta(String numeroDaConta);

    ContaEntidade findClienteByNumeroDaConta(String numeroDaConta);

    List<ContaEntidade> findContaByClienteCpf(String clienteCpf);

}

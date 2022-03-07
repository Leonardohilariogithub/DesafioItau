package com.desafioItau.repositorys;

import com.desafioItau.entidades.ContaEntidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<ContaEntidade, Long> {
}

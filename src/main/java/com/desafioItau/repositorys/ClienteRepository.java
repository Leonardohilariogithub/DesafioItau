package com.desafioItau.repositorys;

import com.desafioItau.entidades.ClienteEntidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntidade, Long> {

    ClienteEntidade findClienteByCpf(String cpf);

    ClienteEntidade findClienteByCnpj(String cnpj);

    //boolean existsByCpf(String cpf);
}

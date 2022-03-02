package com.desafioItau.services;

import com.desafioItau.entidades.ClienteEntidade;
import com.desafioItau.repositorys.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public ClienteEntidade save(ClienteEntidade clienteEntidade) {
        return clienteRepository.save(clienteEntidade);
    }
}

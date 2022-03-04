package com.desafioItau.services;

import com.desafioItau.dtos.ClienteDto;
import com.desafioItau.entidades.ClienteEntidade;
import com.desafioItau.repositorys.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository; //Utilizar metodos prontos do JPA

    public ClienteEntidade criarCliente(ClienteDto clienteDto){
        ClienteEntidade clienteEntidade = clienteDto.transformaParaObjeto();
        clienteEntidade.setRegistro(LocalDateTime.now(ZoneId.of("UTC")));
        return clienteRepository.save(clienteEntidade);
    }

    public List<ClienteEntidade> listar() {
        return clienteRepository.findAll();
    }

    public Optional<ClienteEntidade> obter(Long id) {
        return clienteRepository.findById(id);
    }

    public ClienteEntidade atualizar(Long id, ClienteEntidade clienteAtualizado) {
        ClienteEntidade cliente = clienteRepository.getById(id);
        clienteAtualizado.setId(cliente.getId());
        clienteAtualizado.setRegistro(LocalDateTime.now(ZoneId.of("UTC")));
        return clienteRepository.save(clienteAtualizado);
    }

    private void update(ClienteEntidade cliente) {
        cliente.setNome(cliente.getNome());
        cliente.setCpf(cliente.getCpf());
        cliente.setTelefone(cliente.getTelefone());
        cliente.setEndereco(cliente.getEndereco());
    }

    public Optional<ClienteEntidade> findById(Long id) {
        return clienteRepository.findById(id);
    }

    public void deletarCliente(ClienteEntidade clienteEntidade) {
        clienteRepository.delete(clienteEntidade);
    }


}

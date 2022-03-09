package com.desafioItau.services;

import com.desafioItau.dtos.ClienteDto;
import com.desafioItau.entidades.ClienteEntidade;
import com.desafioItau.repositorys.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository; //Utilizar metodos prontos do JPARepository
    private final ModelMapper modelMapper;

    @Transactional// evita dados quebrados
    public  ClienteEntidade  criarCliente ( ClienteDto  clienteDto ){
        ClienteEntidade clienteEntidade = clienteDto.transformaParaObjeto();
        clienteEntidade.setRegistro( LocalDateTime.now(ZoneId.of("UTC")));
        return clienteRepository.save(clienteEntidade);
    }
    public boolean existsByCpf(String cpf) {
        return clienteRepository.existsByCpf(cpf);
    }

    public Page<ClienteEntidade> findAll(Pageable pageable) { //listar
        return clienteRepository.findAll(pageable);
    }

    public Optional<ClienteEntidade> obter(Long id) {
        return clienteRepository.findById(id);
    }

    public ClienteEntidade atualizar(Long id, ClienteEntidade clienteAtualizado) {  // Setando atributo ID e registro automaticos
        ClienteEntidade cliente = clienteRepository.getById(id);
        clienteAtualizado.setId(cliente.getId());
        clienteAtualizado.setRegistro(LocalDateTime.now(ZoneId.of("UTC")));
        return clienteRepository.save(clienteAtualizado);
    }

    public Optional<ClienteEntidade> findById(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional
    public void deletarCliente(ClienteEntidade clienteEntidade) {
        clienteRepository.delete(clienteEntidade);
    }

}

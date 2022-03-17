package com.desafioItau.services;

import com.desafioItau.dtos.ClienteDto;
import com.desafioItau.entidades.ClienteEntidade;
import com.desafioItau.exceptions.ClienteExistenteException;
import com.desafioItau.repositorys.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository; //Utilizar metodos prontos do JPARepository
    private final ModelMapper modelMapper;

    @Transactional// evita dados quebrados
    public ClienteEntidade criarCliente(ClienteDto clienteDto) {
//        if(clienteRepository.existsByCpf(clienteDto.getCpf())){
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF ou CNPJ já tem cadastro, por favor verificar!");
//        }
        ClienteEntidade clienteEntidade = modelMapper.map(clienteDto, ClienteEntidade.class);
        clienteEntidade.setRegistro(LocalDateTime.now(ZoneId.of("UTC")));
        return clienteRepository.save(clienteEntidade);
    }

    public List<ClienteEntidade> findAll() { //listar
        return clienteRepository.findAll();
    }

    public ClienteEntidade obter(String cpf) {
        ClienteEntidade cliente1 = clienteRepository.findClienteByCpf(cpf);
        if (Objects.nonNull(cliente1)) {
            return cliente1;
        } else {
            throw new ClienteExistenteException("teste");
        }
    }

    public ClienteEntidade obterCnpj(String cnpj) {
        ClienteEntidade clienteCnpj = clienteRepository.findClienteByCnpj(cnpj);
        if (Objects.nonNull(clienteCnpj)) {
            return clienteCnpj;
        } else {
            throw new ClienteExistenteException("teste");
        }
    }

    public ClienteEntidade atualizar(ClienteEntidade cliente, String cpf) {  // Setando atributo ID e registro automaticos
        ClienteEntidade clienteAtual = clienteRepository.findClienteByCpf(cpf);
        if (Objects.nonNull(clienteAtual)) {
            BeanUtils.copyProperties(cliente, clienteAtual);
            return clienteRepository.save(clienteAtual);
        } else {
            throw new ClienteExistenteException(String.format(
                    "cliente de documento %s nao encontrado!", cpf
            ));
        }
    }

    public ClienteEntidade atualizarCnpj(ClienteEntidade cliente, String cnpj) {  // Setando atributo ID e registro automaticos
        ClienteEntidade clienteCnpj = clienteRepository.findClienteByCnpj(cnpj);
        if (Objects.nonNull(clienteCnpj)) {
            BeanUtils.copyProperties(cliente, clienteCnpj);
            return clienteRepository.save(clienteCnpj);
        } else {
            throw new ClienteExistenteException(String.format(
                    "cliente de documento %s nao encontrado!", clienteCnpj
            ));
        }
    }

    public Optional<ClienteEntidade> findById(Long id) {
        return clienteRepository.findById(id);
    }

    public void deletarCliente(String cpf) {
        ClienteEntidade cliente = clienteRepository.findClienteByCpf(cpf);
        if (Objects.nonNull(cliente)) {
            clienteRepository.delete(cliente);
        } else {
            throw new ClienteExistenteException(String.format(
                    "cliente de documento %s nao encontrado ou não existe!", cpf
            ));
        }
    }

    public void deletarClienteCnpj(String cnpj) {
        ClienteEntidade cliente = clienteRepository.findClienteByCnpj(cnpj);
        if (Objects.nonNull(cliente)) {
            clienteRepository.delete(cliente);
        } else {
            throw new ClienteExistenteException(String.format(
                    "cliente de documento %s nao encontrado ou não existe!", cnpj
            ));
        }
    }
}



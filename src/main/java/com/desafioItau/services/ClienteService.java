package com.desafioItau.services;

import com.desafioItau.dtos.ClienteDto;
import com.desafioItau.entidades.ClienteEntidade;
import com.desafioItau.exceptions.ClienteExistenteException;
import com.desafioItau.repositorys.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;

    @Transactional// evita dados quebrados
    public ClienteEntidade criarCliente(ClienteDto clienteDto) {
        ClienteEntidade clienteEntidade = modelMapper.map(clienteDto, ClienteEntidade.class);
        ClienteEntidade clienteCpf = clienteRepository.findClienteByCpf(clienteDto.getCpf());
        ClienteEntidade clienteCnpj = clienteRepository.findClienteByCnpj(clienteDto.getCnpj());
        if (Objects.nonNull(clienteCpf) && Objects.nonNull(clienteCnpj)) {
            throw new ClienteExistenteException(
                    "Documento informado já possui cadastro! Informe outro Documento!");
        }
        else {
            clienteRepository.save(clienteEntidade);
            }
        return clienteEntidade;
    }

    public List<ClienteEntidade> findAll() {
        return clienteRepository.findAll();
    }

    public ClienteEntidade obter(String cpf) {
        ClienteEntidade cliente1 = clienteRepository.findClienteByCpf(cpf);
        if (Objects.nonNull(cliente1)) {
            return cliente1;
        } else {
            throw new ClienteExistenteException("Documento Inexistente ou Invalido!");
        }
    }

    public ClienteEntidade obterCnpj(String cnpj) {
        ClienteEntidade clienteCnpj = clienteRepository.findClienteByCnpj(cnpj);
        if (Objects.nonNull(clienteCnpj)) {
            return clienteCnpj;
        } else {
            throw new ClienteExistenteException("Documento Inexistente ou Invalido!");
        }
    }

    public ClienteEntidade atualizar(ClienteEntidade cliente, String cpf) {
        ClienteEntidade clienteAtual = clienteRepository.findClienteByCpf(cpf);
        if (Objects.nonNull(clienteAtual)) {
            BeanUtils.copyProperties(cliente, clienteAtual);
            return clienteRepository.save(clienteAtual);
        } else {
            throw new ClienteExistenteException(String.format(
                    "Cliente de documento %s nao encontrado!", cpf));
        }
    }

    public ClienteEntidade atualizarCnpj(ClienteEntidade cliente, String cnpj) {
        ClienteEntidade clienteCnpj = clienteRepository.findClienteByCnpj(cnpj);
        if (Objects.nonNull(clienteCnpj)) {
            BeanUtils.copyProperties(cliente, clienteCnpj);
            return clienteRepository.save(clienteCnpj);
        } else {
            throw new ClienteExistenteException(String.format(
                    "Cliente de documento %s nao encontrado!", clienteCnpj));
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
                    "Cliente de documento %s nao encontrado ou não existe!", cpf));
        }
    }

    public void deletarClienteCnpj(String cnpj) {
        ClienteEntidade cliente = clienteRepository.findClienteByCnpj(cnpj);
        if (Objects.nonNull(cliente)) {
            clienteRepository.delete(cliente);
        } else {
            throw new ClienteExistenteException(String.format(
                    "Cliente de documento %s nao encontrado ou não existe!", cnpj));
        }
    }
}



package com.desafioItau.services;

import com.desafioItau.dtos.ClienteDto;
import com.desafioItau.entidades.ClienteEntidade;
import com.desafioItau.enums.EnumTipoPessoaCpfOuCnpj;
import com.desafioItau.exceptions.ClienteCpfException;
import com.desafioItau.exceptions.ClienteExistenteException;
import com.desafioItau.repositorys.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository; //Utilizar metodos prontos do JPARepository
    private final ModelMapper modelMapper;

    @Transactional// evita dados quebrados
    public  ClienteEntidade  criarCliente ( ClienteDto  clienteDto ){
        if (clienteDto.getDocumento().length() != 11 && clienteDto.getTipoDocumento() == EnumTipoPessoaCpfOuCnpj.CPF){
            throw new ClienteCpfException();
        }
        if (clienteDto.getDocumento().length() != 14 && clienteDto.getTipoDocumento() == EnumTipoPessoaCpfOuCnpj.CNPJ){
            throw new ClienteCpfException();
        }

        ClienteEntidade clienteEntidade = modelMapper.map(clienteDto, ClienteEntidade.class);
        clienteEntidade.setRegistro(LocalDateTime.now(ZoneId.of("UTC")));
        return clienteRepository.save(clienteEntidade);
    }
    public boolean existsByDocumento(String documento) {
        return clienteRepository.existsByDocumento(documento);
    }

    public Page<ClienteEntidade> findAll(Pageable pageable) { //listar
        return clienteRepository.findAll(pageable);
    }

    public ClienteEntidade obter(String documento) {
        ClienteEntidade cliente1 = clienteRepository.findClienteByDocumento(documento);
        if(Objects.nonNull(cliente1)){
            return cliente1;
        } else {
            throw new ClienteExistenteException("teste");
        }
    }

    public ClienteEntidade atualizar( ClienteEntidade cliente, String documento) {  // Setando atributo ID e registro automaticos

        ClienteEntidade clienteAtual = clienteRepository.findClienteByDocumento(documento);
        if(Objects.nonNull(clienteAtual)){
            BeanUtils.copyProperties(cliente, clienteAtual);
            return clienteRepository.save(clienteAtual);
        } else {
            throw new ClienteExistenteException(String.format(
                    "cliente de documento %s nao encontrado!",documento
            ));

        }
    }

    public Optional<ClienteEntidade> findById(Long id) {
        return clienteRepository.findById(id);
    }

    public void deletarCliente(String documento) {
        ClienteEntidade cliente = clienteRepository.findClienteByDocumento(documento);
        if(Objects.nonNull(cliente)){
           clienteRepository.delete(cliente);
        } else {
            throw new ClienteExistenteException(String.format(
                    "cliente de documento %s nao encontrado ou não existe!",documento
            ));

        }
    }
        //clienteRepository.delete(clienteEntidade);
    }



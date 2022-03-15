package com.desafioItau.services;

import com.desafioItau.dtos.ContaDto;
import com.desafioItau.entidades.ClienteEntidade;
import com.desafioItau.entidades.ContaEntidade;
import com.desafioItau.exceptions.ClienteExistenteException;
import com.desafioItau.repositorys.ClienteRepository;
import com.desafioItau.repositorys.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
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
public class ContaService {

    private final ContaRepository contaRepository;  //Utilizar metodos prontos do JPARepository
    private final ModelMapper modelMapper;
    private  final ClienteRepository clienteRepository;

    @Transactional // evita dados quebrados
    public ContaEntidade criarConta(ContaDto contaDto) {
        ContaEntidade contaEntidade = modelMapper.map(contaDto, ContaEntidade.class);
        ClienteEntidade clienteEntidade = clienteRepository.findClienteByDocumento(contaDto.getClienteCpf());
        contaEntidade.setRegistro(LocalDateTime.now(ZoneId.of("UTC")));
        contaEntidade.setCliente(clienteEntidade);
        return contaRepository.save(contaEntidade);
    }

    public ContaEntidade save(ContaEntidade contaEntidade) {
        return contaRepository.save(contaEntidade);
    }

    public List<ContaEntidade> findAll() {
        return  contaRepository.findAll();
    }

    public ContaEntidade obter (String numeroDaConta){
        ContaEntidade conta1 = contaRepository.findContaByNumeroDaConta(numeroDaConta);
        if(Objects.nonNull(conta1)){
            return conta1;
        } else {
            throw new ClienteExistenteException("teste");
        }
    }

    public ContaEntidade atualizar(ContaEntidade conta, String numeroDaConta) {  // Setando atributo ID e registro automaticos

        ContaEntidade clienteAtual = contaRepository.findClienteByNumeroDaConta(numeroDaConta);
        if(Objects.nonNull(clienteAtual)){
            BeanUtils.copyProperties(conta, clienteAtual);
            return contaRepository.save(clienteAtual);
        } else {
            throw new ClienteExistenteException(String.format(
                    "cliente de documento %s nao encontrado!",numeroDaConta
            ));

        }

        //        ContaEntidade conta = contaRepository.getById(id);
//        contaAtualizada.setId(conta.getId());
//        contaAtualizada.setRegistro(LocalDateTime.now(ZoneId.of("UTC"))); // .setRegistro(LocalDateTime.now(ZoneId.of("UTC")));
//        return contaRepository.save(contaAtualizada);
    }

    public Optional<ContaEntidade> findById(Long id) {
        return contaRepository.findById(id);
    }

    public void deletarConta(String numeroDaConta) {
        ContaEntidade conta = contaRepository.findContaByNumeroDaConta(numeroDaConta);
        if(Objects.nonNull(conta)){
            contaRepository.delete(conta);
        } else {
            throw new ClienteExistenteException(String.format(
                    "cliente de documento %s nao encontrado ou não existe!",numeroDaConta
            ));
        }
    }

    public List<ContaEntidade> buscarDocumento (String clienteCpf) { //Buscar Pelo Documento
        List<ContaEntidade> contas = contaRepository.findContaByClienteCpf(clienteCpf);
        if (contas.size() > 0) {
            for (ContaEntidade conta : contas) {
                conta.getClienteCpf();
            }
        } else {
            throw new ClienteExistenteException(String.format(
                    "conta de documento %s não encontrado", clienteCpf
            ));
        }
        return contas;
    }


}

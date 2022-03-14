package com.desafioItau.services;

import com.desafioItau.dtos.ContaDto;
import com.desafioItau.entidades.ContaEntidade;
import com.desafioItau.exceptions.ClienteExistenteException;
import com.desafioItau.repositorys.ClienteRepository;
import com.desafioItau.repositorys.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
        contaEntidade.setRegistro(LocalDateTime.now(ZoneId.of("UTC")));
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

    public ContaEntidade atualizar(Long id, ContaEntidade contaAtualizada) {  // Setando atributo ID e registro automaticos
        ContaEntidade conta = contaRepository.getById(id);
        contaAtualizada.setId(conta.getId());
        contaAtualizada.setRegistro(LocalDateTime.now(ZoneId.of("UTC"))); // .setRegistro(LocalDateTime.now(ZoneId.of("UTC")));
        return contaRepository.save(contaAtualizada);
    }

    public Optional<ContaEntidade> findById(Long id) {
        return contaRepository.findById(id);
    }

    public void deletarConta(ContaEntidade contaEntidade) {
        contaRepository.delete(contaEntidade);
    }
}

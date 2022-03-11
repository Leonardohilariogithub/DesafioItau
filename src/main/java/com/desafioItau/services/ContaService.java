package com.desafioItau.services;

import com.desafioItau.dtos.ContaDto;
import com.desafioItau.entidades.ClienteEntidade;
import com.desafioItau.entidades.ContaEntidade;
import com.desafioItau.repositorys.ClienteRepository;
import com.desafioItau.repositorys.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;  //Utilizar metodos prontos do JPARepository
    private final ModelMapper modelMapper;
    private  final ClienteRepository clienteRepository;

    @Transactional // evita dados quebrados
    public ContaEntidade criarConta(ContaDto contaDto) {
        Optional<ClienteEntidade> clienteEntidade = Optional.ofNullable(clienteRepository.findClienteByDocumento(contaDto.getClienteCpf()));
        ContaEntidade contaEntidade = modelMapper.map(contaDto, ContaEntidade.class);
        contaEntidade.setRegistro(LocalDateTime.now(ZoneId.of("UTC")));
        contaEntidade.setClienteCpf(clienteEntidade.get().getDocumento());
        return contaRepository.save(contaEntidade);
    }

    public ContaEntidade save(ContaEntidade contaEntidade) {
        return contaRepository.save(contaEntidade);
    }

    public List<ContaEntidade> findAll() {
        return  contaRepository.findAll();
    }

    public Optional<ContaEntidade> obter(Long id) {
        return contaRepository.findById(id);
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

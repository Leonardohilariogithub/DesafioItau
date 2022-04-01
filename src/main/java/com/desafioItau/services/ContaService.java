package com.desafioItau.services;

import com.desafioItau.dtos.ContaDto;
import com.desafioItau.entidades.ClienteEntidade;
import com.desafioItau.entidades.ContaEntidade;
import com.desafioItau.enums.EnumTipoDaConta;
import com.desafioItau.exceptions.ClienteCpfException;
import com.desafioItau.exceptions.ClienteExistenteException;
import com.desafioItau.repositorys.ClienteRepository;
import com.desafioItau.repositorys.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;
    private final ModelMapper modelMapper;
    private final ClienteRepository clienteRepository;
    //private final ProducerContaService producerContaService;  //kafka

    private final JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost",6379);

    @Transactional // evita dados quebrados
    public ContaEntidade criarConta(ContaDto contaDto) { // se passou um documento

        if (clienteRepository.findClienteByCpf(contaDto.getClienteCpf()) == null || clienteRepository.findClienteByCnpj(contaDto.getClienteCnpj())== null){
            throw new ClienteExistenteException("cliente nao EXISTE!!!!");
        }

        ContaEntidade conta = contaRepository.findContaByNumeroDaConta(contaDto.getNumeroDaConta());
        if (contaDto.getTipoDaConta() == EnumTipoDaConta.PESSOA_FISICA && contaDto.getClienteCpf() == null ) {
            throw new ClienteCpfException("cliente nao pussui CPF para abrir conta FISICA!");
        }
        if (contaDto.getTipoDaConta() == EnumTipoDaConta.PESSOA_JURIDICA && contaDto.getClienteCnpj() == null) {
            throw new ClienteCpfException("cliente nao pussui CNPJ para abrir conta JURIDICA!");
        }
        if (contaDto.getTipoDaConta() == EnumTipoDaConta.GOVERNAMENTAL && contaDto.getClienteCpf() == null && contaDto.getClienteCnpj() == null) {
            throw new ClienteCpfException("Informe documento valido para abrir conta GOVERNAMENTAL!");
        }
        ClienteEntidade cliente = null; // se a pessoa existe
        if (Objects.nonNull(conta)) {
            throw new ClienteCpfException(String.format("conta de numero %s ja existe", contaDto.getNumeroDaConta()));
        }
        if (contaDto.getTipoDaConta() == EnumTipoDaConta.PESSOA_FISICA && clienteRepository.findClienteByCpf(contaDto.getClienteCpf()) != null) {
            cliente = clienteRepository.findClienteByCpf(contaDto.getClienteCpf());
            contaDto.setSaqueSemTaxa(5);
        }
        else if (contaDto.getTipoDaConta() == EnumTipoDaConta.PESSOA_JURIDICA && clienteRepository.findClienteByCnpj(contaDto.getClienteCnpj()) != null) {
            cliente = clienteRepository.findClienteByCnpj(contaDto.getClienteCnpj());
            contaDto.setSaqueSemTaxa(50);
        }
        else if (contaDto.getTipoDaConta() == EnumTipoDaConta.GOVERNAMENTAL && clienteRepository.findClienteByCpf(contaDto.getClienteCpf()) != null
                || clienteRepository.findClienteByCnpj(contaDto.getClienteCnpj()) != null) {

            cliente = clienteRepository.findClienteByCnpj(contaDto.getClienteCnpj());
            contaDto.setSaqueSemTaxa(250);
        }

        ContaEntidade contaEntidade = new ContaEntidade();
        contaEntidade.setTipoDaConta(contaDto.getTipoDaConta());
        contaEntidade.setSaqueSemTaxa(contaDto.getSaqueSemTaxa());
        contaEntidade.setNumeroDaConta(contaDto.getNumeroDaConta());
        contaEntidade.setDigitoVerificador(contaDto.getDigitoVerificador());
        contaEntidade.setAgencia(contaDto.getAgencia());

        contaEntidade.setCliente(cliente);
//        ClienteEntidade clienteEntidade = clienteRepository.findClienteByCpf(contaDto.getClienteCpf());
//        ClienteEntidade clienteCnpj = clienteRepository.findClienteByCnpj(contaDto.getClienteCnpj());
        //Optional<ContaEntidade> verificarConta = Optional.ofNullable(contaRepository.findContaByNumeroDaConta(contaDto.getNumeroDaConta()));

        Jedis jedis = pool.getResource();

        jedis.set(contaEntidade.getNumeroDaConta(), Integer.toString(contaEntidade.getSaqueSemTaxa()));

        //producerContaService.send(contaEntidade);//Kafka

        return contaRepository.save(contaEntidade);
    }

    public ContaEntidade save(ContaEntidade contaEntidade) {
        return contaRepository.save(contaEntidade);
    }

    public List<ContaEntidade> findAll() {
        return contaRepository.findAll();
    }

    public ContaEntidade obter(String numeroDaConta) {
        ContaEntidade conta1 = contaRepository.findContaByNumeroDaConta(numeroDaConta);
        if (Objects.nonNull(conta1)) {
            return conta1;
        } else {
            throw new ClienteExistenteException("Documento Inexistente ou Invalido!");
        }
    }

    public ContaEntidade atualizar(ContaEntidade conta, String numeroDaConta) {  // Setando atributo ID e registro automaticos
        ContaEntidade clienteAtual = contaRepository.findClienteByNumeroDaConta(numeroDaConta);
        if (Objects.nonNull(clienteAtual)) {
            BeanUtils.copyProperties(conta, clienteAtual);
            return contaRepository.save(clienteAtual);
        } else {
            throw new ClienteExistenteException(String.format(
                    "cliente de documento %s nao encontrado!", numeroDaConta
            ));
        }
    }

    public Optional<ContaEntidade> findById(Long id) {
        return contaRepository.findById(id);
    }

    public void deletarConta(String numeroDaConta) {
        ContaEntidade conta = contaRepository.findContaByNumeroDaConta(numeroDaConta);
        if (Objects.nonNull(conta)) {
            if (conta.getSaldo().doubleValue() == 0) {
                contaRepository.delete(conta);
            }
            else {
                throw new ClienteExistenteException(String.format(  // criar exception para transação voltar bad request
                        "Para excluir a conta o saldo deve estar zerado, " + "você possui R$%s de saldo!",
                        conta.getSaldo()));
            }

        } else {
            throw new ClienteExistenteException(String.format(
                    "Cliente de documento %s nao encontrado ou não existe!", numeroDaConta
            ));
        }
    }

    public List<ContaEntidade> buscarDocumento(String clienteCpf) { //Buscar Pelo Documento
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


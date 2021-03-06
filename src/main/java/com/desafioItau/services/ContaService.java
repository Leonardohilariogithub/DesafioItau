package com.desafioItau.services;

import com.desafioItau.dtos.ContaDto;
import com.desafioItau.entidades.ClienteEntidade;
import com.desafioItau.entidades.ContaEntidade;
import com.desafioItau.enums.EnumTipoDaConta;
import com.desafioItau.exceptions.ClienteCpfException;
import com.desafioItau.exceptions.ClienteExistenteException;
import com.desafioItau.exceptions.ContaNaoEncontradaException;
import com.desafioItau.exceptions.TransacaoException;
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
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;
    private final ModelMapper modelMapper;
    private final ClienteRepository clienteRepository;

    private final JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost",6379);

    Random random = new Random(); //numeros aleatorios

    @Transactional // evita dados quebrados
    public ContaEntidade criarConta(@Valid ContaDto contaDto) {

        String nunConta = gerarNumConta();//numeros aleatorios
        int digitoVerificador = random.nextInt(10);//numeros aleatorios

        if (clienteRepository.findClienteByCpf(contaDto.getClienteCpf()) == null || clienteRepository.findClienteByCnpj(contaDto.getClienteCnpj())== null){
            throw new ClienteExistenteException("Cliente nao EXISTE!, Coloque tipo de documento Valido!");
        }
        ContaEntidade conta = contaRepository.findContaByNumeroDaConta(contaDto.getNumeroDaConta());
        if (contaDto.getTipoDaConta() == EnumTipoDaConta.PESSOA_FISICA && contaDto.getClienteCpf() == null ) {
            throw new ClienteCpfException("Cliente nao pussui CPF para abrir conta FISICA!");
        }
        if (contaDto.getTipoDaConta() == EnumTipoDaConta.PESSOA_JURIDICA && contaDto.getClienteCnpj() == null) {
            throw new ClienteCpfException("Cliente nao pussui CNPJ para abrir conta JURIDICA!");
        }
        if (contaDto.getTipoDaConta() == EnumTipoDaConta.GOVERNAMENTAL && contaDto.getClienteCpf() == null && contaDto.getClienteCnpj() == null) {
            throw new ClienteCpfException("Informe documento valido para abrir conta GOVERNAMENTAL!");
        }
        ClienteEntidade cliente = null; // se a pessoa existe
        if (Objects.nonNull(conta)) {
            throw new ClienteCpfException(String.format("Conta de numero %s ja existe", contaDto.getNumeroDaConta()));
        }
        if (contaDto.getTipoDaConta() == EnumTipoDaConta.PESSOA_FISICA && clienteRepository.findClienteByCpf(contaDto.getClienteCpf()) != null) {
            cliente = clienteRepository.findClienteByCpf(contaDto.getClienteCpf());
            contaDto.setNumeroDaConta(nunConta);
            contaDto.setDigitoVerificador(digitoVerificador);
            contaDto.setSaqueSemTaxa(5);
        }
        else if (contaDto.getTipoDaConta() == EnumTipoDaConta.PESSOA_JURIDICA && clienteRepository.findClienteByCnpj(contaDto.getClienteCnpj()) != null) {
            cliente = clienteRepository.findClienteByCnpj(contaDto.getClienteCnpj());
            contaDto.setNumeroDaConta(nunConta);
            contaDto.setDigitoVerificador(digitoVerificador);
            contaDto.setSaqueSemTaxa(50);
        }
        else if (contaDto.getTipoDaConta() == EnumTipoDaConta.GOVERNAMENTAL && clienteRepository.findClienteByCpf(contaDto.getClienteCpf()) != null
                || clienteRepository.findClienteByCnpj(contaDto.getClienteCnpj()) != null) {
            cliente = clienteRepository.findClienteByCnpj(contaDto.getClienteCnpj());
            contaDto.setNumeroDaConta(nunConta);
            contaDto.setDigitoVerificador(digitoVerificador);
            contaDto.setSaqueSemTaxa(250);
        }

        ContaEntidade contaEntidade = new ContaEntidade();
        contaEntidade.setTipoDaConta(contaDto.getTipoDaConta());
        contaEntidade.setSaqueSemTaxa(contaDto.getSaqueSemTaxa());
        contaEntidade.setNumeroDaConta(contaDto.getNumeroDaConta());
        contaEntidade.setDigitoVerificador(contaDto.getDigitoVerificador());
        contaEntidade.setAgencia(contaDto.getAgencia());

        contaEntidade.setCliente(cliente);

        Jedis jedis = pool.getResource();
        jedis.set(contaEntidade.getNumeroDaConta(), Integer.toString(contaEntidade.getSaqueSemTaxa()));

        //producerContaService.send(contaEntidade);//Kafka

        contaRepository.save(contaEntidade);
        return contaEntidade;
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
            throw new ContaNaoEncontradaException("Documento Inexistente ou Invalido!");
        }
    }

    public ContaEntidade atualizar(ContaEntidade conta, String numeroDaConta) {  // Setando atributo ID e registro automaticos
        ContaEntidade clienteAtual = contaRepository.findClienteByNumeroDaConta(numeroDaConta);
        if (Objects.nonNull(clienteAtual)) {
            BeanUtils.copyProperties(conta, clienteAtual);
            return contaRepository.save(clienteAtual);
        } else {
            throw new ContaNaoEncontradaException(String.format(
                    "Cliente de documento %s nao encontrado!", numeroDaConta));
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
                throw new TransacaoException(String.format(
                        "Para excluir a conta o saldo deve estar zerado, " + "voc?? possui R$%s de saldo!",
                        conta.getSaldo()));
            }

        } else {
            throw new TransacaoException(String.format(
                    "Cliente de documento %s nao encontrado ou n??o existe!", numeroDaConta
            ));
        }
    }

    public List<ContaEntidade> buscarCpf(String clienteCpf) {
        ClienteEntidade cliente = clienteRepository.findClienteByCpf(clienteCpf);
        List<ContaEntidade> contas = contaRepository.findByClienteId(cliente.getId());
//        if (contas.size() > 0) {
//            for (ContaEntidade conta : contas) {             //outra forma de fazer
//                conta.getClienteCpf();
//            }
//        }
          if (contas.isEmpty()){
            throw new TransacaoException(String.format(
                    "Conta de documento %s n??o encontrado", clienteCpf));
        }
        return contas;
    }

    public List<ContaEntidade> buscarCnpj(String clienteCnpj){
        ClienteEntidade cliente = clienteRepository.findClienteByCnpj(clienteCnpj);
        List<ContaEntidade> contas = contaRepository.findByClienteId(cliente.getId());
        if(contas.size() > 0){
            for(ContaEntidade conta : contas){
                conta.getClienteCnpj();
            }
        }else {
            throw new TransacaoException(String.format(
                    "Conta de documento %s n??o encontrado", clienteCnpj));
        }
        return contas;
    }

    private String gerarNumConta() {//numeros aleatorios
        StringBuilder numConta = new StringBuilder(Integer.toString(random.nextInt(10)));
        for (int i = 0; i < 4; i++) {
            numConta.append(random.nextInt(10));
        }
//        while (contaRepository.findContaByNumeroDaConta(String.valueOf(numConta)) !=null) {
//            gerarNumConta();
//        }
        return String.valueOf(numConta);
    }
}


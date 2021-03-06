package com.desafioItau.controllers;

import com.desafioItau.dtos.ContaDto;
import com.desafioItau.entidades.ContaEntidade;
import com.desafioItau.services.ContaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/contas")
public class ContaController {

    private final ContaService contaService;
    private final ModelMapper modelMapper;

    @PostMapping(value = "/cadastro")
    public ResponseEntity<Object> criarConta(@RequestBody @Valid ContaDto contaDto){
        var contaEntidade = new ContaEntidade();
        BeanUtils.copyProperties(contaDto, contaEntidade);
        ContaEntidade conta = contaService.criarConta(contaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }

    @GetMapping
    public ResponseEntity<List<ContaEntidade>> listarConta(){
        return ResponseEntity.status(HttpStatus.OK).body(contaService.findAll());
    }

    @GetMapping("/obterConta/")
    public ResponseEntity<?> obterConta (@RequestParam(name = "numeroDaConta") String numeroDaConta){
        ContaEntidade conta = contaService.obter(numeroDaConta);
        return ResponseEntity.status(HttpStatus.OK).body(conta);
    }

    @GetMapping("/obterContaPeloCpf/")
    public ResponseEntity<?> obterCpf (@RequestParam(name = "clienteCpf") String clienteCpf){
        List<ContaEntidade> conta = contaService.buscarCpf(clienteCpf);
        return ResponseEntity.status(HttpStatus.OK).body(conta);
    }

    @GetMapping("/obterContaPeloCnpj/")
    public ResponseEntity<?> buscarCnpj (@RequestParam(name = "clienteCnpj") String clienteCnpj){
        List<ContaEntidade> conta = contaService.buscarCnpj(clienteCnpj);
        return ResponseEntity.status(HttpStatus.OK).body(conta);
    }

    @PutMapping("/atualizar/")
    public ResponseEntity<ContaEntidade> atualizarConta(@RequestBody @Valid ContaDto contaDto, @RequestParam(name = "numeroDaConta") String numeroDaConta){
        ContaEntidade conta = new ContaEntidade();
        BeanUtils.copyProperties(contaDto, conta);
        contaService.atualizar(conta, numeroDaConta);     //PUT usando PARANS- KEY  -VALUE
        return ResponseEntity.status(HttpStatus.OK).body(conta);
    }

    @DeleteMapping("/deletar/")
    public ResponseEntity<?> deletarConta(@RequestParam(name = "numeroDaConta") String numeroDaConta){
        contaService.deletarConta(numeroDaConta);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

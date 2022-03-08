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
import java.util.Optional;

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
        return ResponseEntity.status(HttpStatus.CREATED).body(contaService.criarConta(contaDto));
    }

    @GetMapping
    public ResponseEntity<List<ContaEntidade>> listarConta(){
        return ResponseEntity.status(HttpStatus.OK).body(contaService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object>obterConta(@PathVariable Long id){
        Optional<ContaEntidade> contaEntidadeOptional = contaService.findById(id);
        return ResponseEntity.ok(contaEntidadeOptional.get());
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ContaEntidade> atualizarConta(@RequestBody @Valid ContaDto contaDto, @RequestParam(name = "id") Long id){
        ContaEntidade conta = new ContaEntidade();
        BeanUtils.copyProperties(contaDto, conta);
        contaService.atualizar(id, conta);     //PUT usando PARANS- KEY id -VALUE -2
        return ResponseEntity.status(HttpStatus.OK).body(conta);
    }
    
//    public ResponseEntity<Object> atualizarConta(@RequestBody @Valid ContaDto contaDto, Long id){
//       Optional<ContaEntidade> contaEntidadeOptional = contaService.findById(id);
//       var contaEntidade = contaService.atualizar(id, modelMapper.map(contaDto,ContaEntidade.class));
//       return ResponseEntity.ok(null).body(contaEntidadeOptional.get());
//    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletarConta(@PathVariable Long id){
        Optional<ContaEntidade> contaEntidadeOptional = contaService.findById(id);
        contaService.deletarConta(contaEntidadeOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Conta Deletada com SUCESSO!!!");
    }

}

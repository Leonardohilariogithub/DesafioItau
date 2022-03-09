package com.desafioItau.controllers;

import com.desafioItau.dtos.OperacaoDto;
import com.desafioItau.entidades.OperacaoEntidade;
import com.desafioItau.services.OperacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)//pemitir acessar de qualquer fonte
@RestController
@RequestMapping(value = "/operacoes")
public class OperacaoController {

    private final OperacaoService operacaoService;

    @PostMapping
    public ResponseEntity<Object> salvarOperacao(@RequestBody @Valid OperacaoDto operacaoDto){
        var operacaoEntidade = new OperacaoEntidade();
        BeanUtils.copyProperties(operacaoDto, operacaoEntidade);
        operacaoEntidade.setRegistro(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(operacaoService.save(operacaoEntidade));
    }

    @GetMapping
    public ResponseEntity<List<OperacaoEntidade>> listarOperacao(){
        return ResponseEntity.status(HttpStatus.OK).body(operacaoService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object>obterOperacao(@PathVariable Long id){
        Optional<OperacaoEntidade> operacaoObtida = operacaoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(operacaoObtida.get());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletarOperacao(@PathVariable Long id){
        Optional<OperacaoEntidade> operacaoDeletada = operacaoService.findById(id);
        operacaoService.delete(operacaoDeletada.get());
        return ResponseEntity.status(HttpStatus.OK).body("Operação Deletada");
    }
}

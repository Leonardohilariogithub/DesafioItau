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

@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)//pemitir acessar de qualquer fonte
@RestController
@RequestMapping(value = "/operacoes")
public class OperacaoController {

    private final OperacaoService operacaoService;

    @PostMapping("/deposito")
    public ResponseEntity<?> depositar(@RequestBody @Valid OperacaoDto operacaoDto){
        OperacaoEntidade operacaoAtual = new OperacaoEntidade();
        BeanUtils.copyProperties(operacaoDto, operacaoAtual);
        operacaoService.depositar(operacaoAtual);
        return ResponseEntity.status(HttpStatus.OK).body(operacaoAtual);
    }

    // 3 post e 2 get@RequestBody @Valid
    // get pelo param

//    public ResponseEntity<Object> salvarOperacao(@RequestBody @Valid OperacaoDto operacaoDto){
//        var operacaoEntidade = new OperacaoEntidade();
//        BeanUtils.copyProperties(operacaoDto, operacaoEntidade);
//        operacaoEntidade.setRegistro(LocalDateTime.now(ZoneId.of("UTC")));
//        return ResponseEntity.status(HttpStatus.CREATED).body(operacaoService.save(operacaoEntidade));
//    }

//    @GetMapping
//    public ResponseEntity<List<OperacaoEntidade>> listarOperacao(){
//        return ResponseEntity.status(HttpStatus.OK).body(operacaoService.findAll());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Object>obterOperacao(@PathVariable Long id){
//        Optional<OperacaoEntidade> operacaoObtida = operacaoService.findById(id);
//        return ResponseEntity.status(HttpStatus.OK).body(operacaoObtida.get());
//    }
//
//    @PutMapping("/atualizar/{id}")
//    public ResponseEntity<Object> atualizarOperacao(@PathVariable @RequestParam(value = "id") Long id, @RequestBody @Valid OperacaoDto operacaoDto){
//        OperacaoEntidade conta = new OperacaoEntidade();
//        BeanUtils.copyProperties(operacaoDto, conta);
//        operacaoService.atualizar(id, conta);     //PUT usando PARANS- KEY id -VALUE -2
//        return ResponseEntity.status(HttpStatus.OK).body(conta);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Object> deletarOperacao(@PathVariable Long id){
//        Optional<OperacaoEntidade> operacaoDeletada = operacaoService.findById(id);
//        if (!operacaoDeletada.isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não existe nenhuma Operação!");
//        }
//        operacaoService.delete(operacaoDeletada.get());
//        return ResponseEntity.status(HttpStatus.OK).body("Operação Deletada");
//    }
}

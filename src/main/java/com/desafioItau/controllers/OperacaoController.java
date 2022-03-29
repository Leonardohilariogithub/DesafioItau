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
import java.math.BigDecimal;
import java.util.List;

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

    @PostMapping("/saque")
    public ResponseEntity<?> sacar(@RequestBody @Valid OperacaoDto operacaoDto){
        OperacaoEntidade operacaoAtual = new OperacaoEntidade();
        BeanUtils.copyProperties(operacaoDto, operacaoAtual);
        return ResponseEntity.status(HttpStatus.OK).body(operacaoService.sacar(operacaoAtual));
    }

    @PostMapping("/transferencia")
    public ResponseEntity<?> transferencia(@RequestBody @Valid OperacaoDto operacaoDto){
        OperacaoEntidade operacaoAtual = new OperacaoEntidade();
        BeanUtils.copyProperties(operacaoDto, operacaoAtual);
        operacaoService.transferencia(operacaoAtual);
        return ResponseEntity.status(HttpStatus.OK).body(operacaoAtual);
    }

    @GetMapping("/saldo/")
    public ResponseEntity<?> saldo(@RequestParam(name = "numeroDaConta") String numeroDaConta){
        BigDecimal saldoDaConta = operacaoService.saldo(numeroDaConta);
        return ResponseEntity.ok().body("SALDO: R$" + saldoDaConta);
    }

    @GetMapping("/extrato/")
    public ResponseEntity<List<OperacaoEntidade>> extrato(@RequestParam(name = "numeroDaConta") String numeroDaConta){
        List<OperacaoEntidade> operacaoEntidade = operacaoService.extrato(numeroDaConta);
        return ResponseEntity.ok(operacaoEntidade);
    }
}

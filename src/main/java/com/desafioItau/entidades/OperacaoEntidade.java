package com.desafioItau.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "operacao")
public class OperacaoEntidade {

    private float saldo;
    private float extrato;
    private float saque;
    private float transferencia;
}

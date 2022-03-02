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
@Table(name = "conta")
public class Conta {

    private Long id;
    private String agencia;
    private double numeroDaConta;
    private double tipoDaConta;
    private double digitoVerificador;
    private String clienteCpf;
}

package com.desafioItau.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum EnumOperacao {

    TRANSFERENCIA(1,"Transferencia"),
    SAQUE(2, "Saque"),
    DEPOSITO(3, "Deposito");

    private int codigo;
    public String descricao;

}

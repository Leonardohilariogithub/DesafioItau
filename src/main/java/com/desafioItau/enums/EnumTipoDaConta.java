package com.desafioItau.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum EnumTipoDaConta {

    PESSOA_FISICA(0,"Pessoa Fisica"),
    PESSOA_JURIDICA(1, "Pessoa Juridica"),
    GOVERNAMENTAL(2, "Governamental");

    public int codigo;
    public String descricao;


}

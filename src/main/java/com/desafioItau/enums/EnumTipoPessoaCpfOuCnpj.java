package com.desafioItau.enums;

import com.desafioItau.entidades.groups.CnpjGroup;
import com.desafioItau.entidades.groups.CpfGroup;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum EnumTipoPessoaCpfOuCnpj {

    CPF("CPF","CPF","000.000.000-00", CpfGroup.class),
    CNPJ("CNPJ","CNPJ","00.000.000/0000-00", CnpjGroup.class);

    public String descricao;
    public String documento;
    public String mascara;
    public Class<?> group;

}

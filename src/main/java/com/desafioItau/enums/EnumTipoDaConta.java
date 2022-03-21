package com.desafioItau.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum EnumTipoDaConta implements CalcularTaxa{

    PESSOA_FISICA(0,"Pessoa Fisica", 10){
        @Override
        public double taxa(double valor){
            return  valor = 10;
        }
    },

    PESSOA_JURIDICA(1, "Pessoa Juridica", 10){
        @Override
        public double taxa(double valor){
            return  valor = 10;
        }
    },

    GOVERNAMENTAL(2, "Governamental", 20){
        @Override
        public double taxa(double valor){
            return valor = 20;
        }
    };

    public int codigo;
    public String descricao;
    private double taxa;
}

package com.desafioItau;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PrimeiroTeste {

    @Test
    public void deveSomar2Numeros(){
        //cenario
        int numero1 = 10, numero2 = 5;

        //execução
        int resultado = numero1 + numero2;

        //verificações Formas Diferentes

        //Assert.assertEquals(15, resultado); //Assert já vem funçoes basicas JUnit

        Assertions.assertThat(resultado).isBetween(14,16);       //Assertions - funçoes mais avançadas

        Assertions.assertThat(resultado).isEqualTo(15);

    }
}

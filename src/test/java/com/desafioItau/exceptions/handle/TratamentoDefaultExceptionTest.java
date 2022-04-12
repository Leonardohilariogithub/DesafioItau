package com.desafioItau.exceptions.handle;

import com.desafioItau.exceptions.ClienteCpfException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TratamentoDefaultExceptionTest {

    @InjectMocks
    private TratamentoDefaultException tratamentoDefaultException;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handle() {
        ResponseEntity<DefaultException> resposta = tratamentoDefaultException
                .handle(new ClienteCpfException(
                        "Documento informado já possui cadastro! Informe outro Documento!")
                );
        assertNotNull(resposta);
        assertNotNull(resposta.getBody());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, resposta.getStatusCode());
        assertEquals(ResponseEntity.class, resposta.getClass());
        assertEquals("Documento informado já possui cadastro! Informe outro Documento!",
                resposta.getBody().getMensagem());
        assertEquals(406, resposta.getBody().getStatus());

    }

    @Test
    void testHandle() {
    }

    @Test
    void testHandle1() {
    }

    @Test
    void testHandle2() {
    }

    @Test
    void testHandle3() {
    }

    @Test
    void testHandle4() {
    }

    @Test
    void testHandle5() {
    }

    @Test
    void testHandle6() {
    }

    @Test
    void testHandle7() {
    }

    @Test
    void testHandle8() {
    }

    @Test
    void testHandle9() {
    }

    @Test
    void testHandle10() {
    }

    @Test
    void testHandle11() {
    }

    @Test
    void testHandle12() {
    }
}
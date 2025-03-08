//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import banco.exception.ValidacaoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author rodri
 */
class ValidacaoExceptionTest {

    // Testando o construtor que recebe uma mensagem de erro
    @Test
    void testValidacaoExceptionComMensagem() {
        String mensagem = "Erro de validação";
        
        ValidacaoException exception = new ValidacaoException(mensagem);
        
        assertEquals(mensagem, exception.getMessage(), "A mensagem da exceção não é a esperada.");
    }

    // Testando o construtor que recebe uma mensagem de erro e a causa da exceção
    @Test
    void testValidacaoExceptionComMensagemECausa() {
        String mensagem = "Erro de validação";
        Throwable causa = new IllegalArgumentException("Argumento inválido");
        
        ValidacaoException exception = new ValidacaoException(mensagem, causa);
        
        assertEquals(mensagem, exception.getMessage(), "A mensagem da exceção não é a esperada.");
        assertEquals(causa, exception.getCause(), "A causa da exceção não é a esperada.");
    }
}
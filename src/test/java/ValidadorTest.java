//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB

import banco.exception.ValidacaoException;
import banco.exception.Validador;
import banco.model.Conta; // Importando a classe Conta
import java.util.ArrayList; // Importando a classe ArrayList
import org.junit.jupiter.api.Test; // Importando a anotação Test do JUnit 5
import static org.junit.jupiter.api.Assertions.*; // Importando as asserções do JUnit 5


class ValidadorTest {

    // Testes para nome
    @Test
    void testNomeValido() {
        assertTrue(Validador.nomeValido("João Silva"));
        assertFalse(Validador.nomeValido("J"));
        assertFalse(Validador.nomeValido("João123"));
        assertFalse(Validador.nomeValido("João@@"));
        assertFalse(Validador.nomeValido("Jooonnn"));
    }

    @Test
    void testValidarNome() {
        // Teste nome válido
        assertDoesNotThrow(() -> Validador.validarNome("João Silva"));
        
        // Testes com exceções
        assertThrows(ValidacaoException.class, () -> Validador.validarNome("J"));
        assertThrows(ValidacaoException.class, () -> Validador.validarNome("João123"));
        assertThrows(ValidacaoException.class, () -> Validador.validarNome("João@@"));
        assertThrows(ValidacaoException.class, () -> Validador.validarNome("Jooonnn"));
    }

    // Testes para CPF
    @Test
    void testCpfValido() {
        assertFalse(Validador.cpfValido("11111111111"));
        assertTrue(Validador.cpfValido("12345678900"));
    }

    @Test
    void testValidarCpf() {
        // Teste CPF válido
        assertDoesNotThrow(() -> Validador.validarCpf("12345678900"));
        
        // Testes com exceções
        assertThrows(ValidacaoException.class, () -> Validador.validarCpf("11111111111"));
        assertThrows(ValidacaoException.class, () -> Validador.validarCpf("123"));
        assertThrows(ValidacaoException.class, () -> Validador.validarCpf("123456789012"));
    }

    // Testes para Senha
    @Test
    void testSenhaValida() {
        assertTrue(Validador.senhaValida("123456".toCharArray()));
        assertFalse(Validador.senhaValida("1234".toCharArray()));
        assertFalse(Validador.senhaValida("12345".toCharArray()));
        assertFalse(Validador.senhaValida("12345aa".toCharArray()));
    }

    @Test
    void testValidarSenha() {
        // Teste senha válida
        assertDoesNotThrow(() -> Validador.validarSenha(123456));
        
        // Testes com exceções
        assertThrows(ValidacaoException.class, () -> Validador.validarSenha(12345));
        assertThrows(ValidacaoException.class, () -> Validador.validarSenha(123456));
        assertThrows(ValidacaoException.class, () -> Validador.validarSenha(111111));
        assertThrows(ValidacaoException.class, () -> Validador.validarSenha(123));
    }

    // Testes para número da conta
    @Test
    void testNumeroContaValido() {
        assertTrue(Validador.numeroContaValido(123456));
        assertFalse(Validador.numeroContaValido(12345));
        assertFalse(Validador.numeroContaValido(1234567));
    }

    @Test
    void testValidarNumeroConta() {
        // Teste número da conta válido
        assertDoesNotThrow(() -> Validador.validarNumeroConta(123456));
        
        // Testes com exceções
        assertThrows(ValidacaoException.class, () -> Validador.validarNumeroConta(12345));
        assertThrows(ValidacaoException.class, () -> Validador.validarNumeroConta(1234567));
        assertThrows(ValidacaoException.class, () -> Validador.validarNumeroConta(123456));
    }

    // Testes para depósito, saque e transferência
    @Test
    void testValidarDeposito() {
        // Teste depósito válido
        assertDoesNotThrow(() -> Validador.validarDeposito(100.0));
        
        // Testes com exceções
        assertThrows(ValidacaoException.class, () -> Validador.validarDeposito(0.0));
        assertThrows(ValidacaoException.class, () -> Validador.validarDeposito(-10.0));
    }

    @Test
    void testValidarSaque() {
        // Teste saque válido
        assertDoesNotThrow(() -> Validador.validarSaque(100.0, 200.0));
        
        // Testes com exceções
        assertThrows(ValidacaoException.class, () -> Validador.validarSaque(0.0, 200.0));
        assertThrows(ValidacaoException.class, () -> Validador.validarSaque(300.0, 200.0));
    }

    @Test
    void testValidarTransferencia() {
        // Criando uma conta para o teste
        Conta contaDestino = new Conta(123456, 123456);
        ArrayList<Conta> contasCadastradas = new ArrayList<>();
        contasCadastradas.add(new Conta(123456, 123456));

        // Teste de transferência válida
        assertDoesNotThrow(() -> Validador.validarTransferencia(100.0f, contaDestino, contasCadastradas));
        
        // Testes com exceções
        assertThrows(ValidacaoException.class, () -> Validador.validarTransferencia(0.0f, contaDestino, contasCadastradas));
        assertThrows(ValidacaoException.class, () -> Validador.validarTransferencia(100.0f, null, contasCadastradas));
        assertThrows(ValidacaoException.class, () -> Validador.validarTransferencia(100.0f, contaDestino, new ArrayList<>()));
    }
}

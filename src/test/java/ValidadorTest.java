import banco.exception.ValidacaoException;
import banco.exception.Validador;
import banco.model.Conta;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class ValidadorTest {

    @Test
    void testNomeValido() {
        assertTrue(Validador.nomeValido("João"));
        assertFalse(Validador.nomeValido("J"));
        assertFalse(Validador.nomeValido("João123"));
        assertFalse(Validador.nomeValido("Joooão"));
    }

    @Test
    void testValidarNome() {
        assertDoesNotThrow(() -> Validador.validarNome("João"));
        assertThrows(ValidacaoException.class, () -> Validador.validarNome("J"));
        assertThrows(ValidacaoException.class, () -> Validador.validarNome("João123"));
        assertThrows(ValidacaoException.class, () -> Validador.validarNome("Joooão"));
    }

    @Test
    void testCpfValido() {
        assertTrue(Validador.cpfValido("12345678909"));
        assertFalse(Validador.cpfValido("11111111111"));
        assertFalse(Validador.cpfValido("123"));
    }

    @Test
    void testValidarCpf() {
        assertDoesNotThrow(() -> Validador.validarCpf("12345678909"));
        assertThrows(ValidacaoException.class, () -> Validador.validarCpf("123"));
        assertThrows(ValidacaoException.class, () -> Validador.validarCpf("11111111111"));
    }

    @Test
    void testSenhaValida() {
        assertTrue(Validador.senhaValida("147258".toCharArray()));
        assertFalse(Validador.senhaValida("111111".toCharArray()));
        assertFalse(Validador.senhaValida("123456".toCharArray()));
    }

@Test
void testValidarSenha() {
    // Aqui a senha "123456" é considerada inválida, pois é uma sequência numerica
    assertThrows(ValidacaoException.class, () -> Validador.validarSenha(123456)); 
    
    // A senha "12345" é inválida porque tem menos de 6 caracteres
    assertThrows(ValidacaoException.class, () -> Validador.validarSenha(12345));
}


    @Test
    void testNumeroContaValido() 
    {
    // Teste para número de conta válido
    assertTrue(Validador.numeroContaValido(147258)); // Esperado true, porque é um número válido de 6 dígitos e não é sequência

    // Teste para número de conta inválido (menos de 6 dígitos)
    assertFalse(Validador.numeroContaValido(123)); // Esperado false, porque o número tem menos de 6 dígitos

    // Teste para número de conta inválido (com sequência de números)
    assertFalse(Validador.numeroContaValido(123456)); // Esperado false, porque é uma sequência de números
}

    @Test
    void testValidarDeposito() {
        assertDoesNotThrow(() -> Validador.validarDeposito(100));
        assertThrows(ValidacaoException.class, () -> Validador.validarDeposito(0));
    }

    @Test
    void testValidarSaque() {
        assertDoesNotThrow(() -> Validador.validarSaque(50, 100));
        assertThrows(ValidacaoException.class, () -> Validador.validarSaque(150, 100));
    }

    //erro do getDono null... mas acredito que o teste funcionaria
    @Test
    void testValidarTransferencia() {
        // Preparar contas para o teste
        
        ArrayList <Conta>contas = new ArrayList();
        
        contas.add(new Conta(697413, 1)); //conta de origem
        contas.add(new Conta(136974, 2)); //conta de destino
        Conta contaDestinoInexistente = new Conta(999999, 3); // Conta destino inexistente, a conta é criada, mas ela não faz parte da lista
                                                              //de contas

        contas.get(0).deposito(500f); // Depositando R$500 na conta origem

        //Transferência válida
        assertDoesNotThrow(() -> Validador.validarTransferencia(100f, contas.get(1), contas),
                "Não deve lançar exceção para uma transferência válida.");

        //Transferência com valor negativo
        assertThrows(ValidacaoException.class, () -> Validador.validarTransferencia(-100f, contas.get(1), contas),
                "Deve lançar exceção para valor de transferência negativo.");

        //Transferência com valor maior que o saldo da conta origem
        assertThrows(ValidacaoException.class, () -> Validador.validarTransferencia(600f, contas.get(1), contas),
                "Deve lançar exceção quando o valor da transferência for maior que o saldo da conta origem.");

        //Transferência para uma conta de destino que não existe
        assertThrows(ValidacaoException.class, () -> Validador.validarTransferencia(100f, contaDestinoInexistente, contas),
                "Deve lançar exceção quando a conta de destino não existir.");
    }


}

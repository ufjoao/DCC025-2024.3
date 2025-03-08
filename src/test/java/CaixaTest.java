//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB

import banco.model.Caixa;
import banco.model.Cliente;
import banco.model.Conta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CaixaTest {

    private Caixa caixa;
    private Cliente cliente;
    private Conta conta;

    @BeforeEach
    public void setUp() {
        // Criando um cliente 
        cliente = new Cliente("João Paulo", "07013762016", 147258);
        cliente.setId(1);  // Supondo que a classe Cliente tenha um método setId() para definir o ID

        conta = new Conta(963852, cliente.getId());

        // Criando um caixa
        caixa = new Caixa("Carlos Eduardo", "02710789019", 258147);

        // Associando o cliente à conta
        conta = new Conta(134679, cliente.getId());
    }

    //erro relacionado ao getDono do Conta
    @Test
    public void testRealizarDeposito() {
        // Antes do depósito, o saldo da conta deve ser 0
        assertEquals(0, conta.getSaldo(), 0.001);

        // Realizando depósito de R$ 500,00
        caixa.realizarDeposito(conta, 500);

        // Após o depósito, o saldo da conta deve ser 500
        assertEquals(500, conta.getSaldo(), 0.001);
    }

    @Test
    public void testRealizarSaque() {
        // Realizando um depósito primeiro para ter saldo
        caixa.realizarDeposito(conta, 500);

        // Antes do saque, o saldo da conta deve ser 500
        assertEquals(500, conta.getSaldo(), 0.001);

        // Realizando saque de R$ 200,00
        caixa.realizarSaque(conta, 200);

        // Após o saque, o saldo da conta deve ser 300
        assertEquals(300, conta.getSaldo(), 0.001);
    }

    @Test
    public void testSaqueInvalido() {
        // Tentando realizar um saque maior que o saldo
        caixa.realizarDeposito(conta, 300);

        // Aqui, a exceção não é gerada diretamente no método da classe Caixa,
        // mas a classe Conta deve lançar uma IllegalArgumentException se o saque for inválido.

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            caixa.realizarSaque(conta, 500);  // Tentativa de saque maior que o saldo
        });

        assertEquals("Saldo insuficiente.", exception.getMessage());
    }

    @Test
    public void testMensagemDeposito() {
        // Realizando depósito de R$ 100,00
        caixa.realizarDeposito(conta, 100);

        // Verificando se o saldo da conta foi atualizado corretamente
        assertEquals(100, conta.getSaldo(), 0.001);
    }

    @Test
    public void testMensagemSaque() {
        // Realizando depósito primeiro para ter saldo
        caixa.realizarDeposito(conta, 500);

        // Realizando saque de R$ 100,00
        caixa.realizarSaque(conta, 100);

        // Verificando se o saldo da conta foi atualizado corretamente
        assertEquals(400, conta.getSaldo(), 0.001);
    }
}

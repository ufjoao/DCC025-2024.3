//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB

import banco.exception.Validador;
import banco.model.Cliente;
import banco.model.Conta;
import banco.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContaTest {

    private Conta conta1;
    private Conta conta2;
    private Cliente cliente1;
    private Cliente cliente2;

    @BeforeEach
    void setUp() {
        // Configura o ambiente antes de cada teste
        // Criar clientes
        cliente1 = new Cliente("João A", "85515627032", 147258);
        cliente2 = new Cliente("Maria B", "87145148005", 741852);

        // Criar contas associadas aos clientes
        conta1 = new Conta(369258, 1); // Conta 1 para o João
        conta2 = new Conta(258147, 2); // Conta 2 para a Maria

        // Registrar as contas (simulando uma persistência)
        Persistence.salvarConta(conta1);
        Persistence.salvarConta(conta2);
    }

    //erro de getDono null
    @Test
    void testCriarContaComParametros() {
        // Verifica se a conta foi criada corretamente
        assertNotNull(conta1);
        assertEquals(12345, conta1.getNumeroDaConta());
        assertEquals(0.0f, conta1.getSaldo());
        assertTrue(conta1.getMovimentacoes().isEmpty());
    }

    //erro de getDono null
    @Test
    void testAdicionarMovimentacao() {
        // Adiciona uma movimentação válida
        conta1.adicionarMovimentacao("Depósito de R$ 100");

        assertEquals(1, conta1.getMovimentacoes().size());
        assertEquals("Depósito de R$ 100", conta1.getMovimentacoes().get(0));

        // Testa a exceção ao tentar adicionar movimentação nula ou vazia
        assertThrows(IllegalArgumentException.class, () -> conta1.adicionarMovimentacao(null));
        assertThrows(IllegalArgumentException.class, () -> conta1.adicionarMovimentacao(""));
    }

    //mesmo erro
    @Test
    void testDeposito() {
        // Depósito válido
        conta1.deposito(500);
        assertEquals(500, conta1.getSaldo(), 0.001);

        // Depósito inválido (valor negativo)
        assertThrows(IllegalArgumentException.class, () -> conta1.deposito(-100));
    }

    //mesmo erro
    @Test
    void testSaque() {
        // Depósito inicial
        conta1.deposito(500);

        // Saque válido
        conta1.saque(200);
        assertEquals(300, conta1.getSaldo(), 0.001);

        // Saque inválido (valor maior que o saldo)
        assertThrows(IllegalArgumentException.class, () -> conta1.saque(400));

        // Saque inválido (valor negativo)
        assertThrows(IllegalArgumentException.class, () -> conta1.saque(-100));
    }

    //mesmo erro
    @Test
    void testGerarExtrato() {
        // Adiciona movimentações
        conta1.adicionarMovimentacao("Depósito de R$ 200");
        conta1.adicionarMovimentacao("Saque de R$ 50");

        String extrato = conta1.gerarExtrato();

        assertTrue(extrato.contains("Extrato da Conta"));
        assertTrue(extrato.contains("Depósito de R$ 200"));
        assertTrue(extrato.contains("Saque de R$ 50"));

        // Conta sem movimentações
        Conta contaSemMovimentacao = new Conta(67890, 1);
        String extratoVazio = contaSemMovimentacao.gerarExtrato();
        assertTrue(extratoVazio.contains("Conta sem movimentações."));
    }

    //mesmo erro
    @Test
    void testTransferir() {
        // Depósitos iniciais
        conta1.deposito(500);
        conta2.deposito(200);

        // Transferência válida
        conta1.transferir(conta2, 100, 12345);  // Supondo que a senha esteja correta

        assertEquals(400, conta1.getSaldo(), 0.001);
        assertEquals(300, conta2.getSaldo(), 0.001);
        assertTrue(conta1.getMovimentacoes().contains("Transferência de R$ 100 para conta 54321"));

        // Transferência inválida (saldo insuficiente)
        assertThrows(IllegalArgumentException.class, () -> conta1.transferir(conta2, 600, 12345));

        // Transferência inválida (senha incorreta)
        assertThrows(IllegalArgumentException.class, () -> conta1.transferir(conta2, 100, 11111));  // Senha incorreta
    }

    //mesmo erro
    @Test
    void testVerificaNumeroDaConta() {
        // Verifica o número da conta
        assertTrue(conta1.verificaNumeroDaConta(12345));
        assertFalse(conta1.verificaNumeroDaConta(54321));
    }

    //mesmo erro
    @Test
    void testGetDono() {
        // Verifica se o dono da conta é retornado corretamente
        Cliente dono = conta1.getDono();
        assertNotNull(dono);
        assertEquals(cliente1.getId(), dono.getId());
    }
}

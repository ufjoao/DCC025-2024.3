//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB

import banco.model.Cliente;
import banco.model.Conta;
import banco.model.Movimentacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    private Cliente cliente;
    private Conta conta;

    @BeforeEach
    public void setUp() {
        // Criando um cliente
        cliente = new Cliente("João", "12345678901", 1234);
        
        // Criando uma conta e associando ao cliente
        conta = new Conta(98765, cliente.getId());
        cliente.adicionarConta(conta);
    }

    @Test
    public void testCriarConta() {
        // Verificando se a conta foi adicionada corretamente
        assertNotNull(cliente.getContas());
        assertEquals(1, cliente.getContas().size());
        assertEquals(conta, cliente.getContas().get(0));
    }

    @Test
    public void testRetornaConta() {
        // Testando o retorno da conta a partir do número da conta
        Conta contaRetornada = cliente.retornaConta(98765);
        assertNotNull(contaRetornada);
        assertEquals(conta, contaRetornada);
    }

    @Test
    public void testRetornaContaNaoEncontrada() {
        // Testando quando a conta não é encontrada
        Conta contaNaoEncontrada = cliente.retornaConta(12345);
        assertNull(contaNaoEncontrada);
    }

    //erro relacionado ao getDono em Conta
    @Test
    public void testConsultarSaldo() {
        // Depositando um valor na conta
        conta.deposito(500);
        
        // Consultando saldo da conta
        float saldo = cliente.consultarSaldo(conta);
        assertEquals(500, saldo, 0.001);
    }

    //erro, não consegui entender
    @Test
    public void testGerarExtratoSemMovimentacoes() {
        // Gerando extrato sem movimentações
        String extrato = cliente.gerarExtrato();
        assertEquals("Conta sem movimentações.", extrato);
    }

    //erro, não consegui entender... a lista é iniciada vazia, então não é nula
    @Test
    public void testGerarExtratoComMovimentacoes() {
        // Registrando movimentações
        cliente.registrarMovimentacao("Depósito", 500);
        cliente.registrarMovimentacao("Saque", -200);

        // Gerando extrato com movimentações
        String extrato = cliente.gerarExtrato();
        assertTrue(extrato.contains("Depósito"));
        assertTrue(extrato.contains("Saque"));
    }

    @Test
    public void testAdicionarLimiteCredito() {
        // Verificando limite de crédito inicial
        assertEquals(0, cliente.getLimiteCredito(), 0.001);

        // Adicionando limite de crédito
        cliente.adicionarLimiteCredito(1000);

        // Verificando limite de crédito após adição
        assertEquals(1000, cliente.getLimiteCredito(), 0.001);
    }

    @Test
    public void testVerificaNumeroDaConta() {
        // Verificando a conta válida
        boolean contaValida = cliente.verificaNumeroDaConta(98765);
        assertTrue(contaValida);

        // Verificando conta inválida
        boolean contaInvalida = cliente.verificaNumeroDaConta(12345);
        assertFalse(contaInvalida);
    }
}

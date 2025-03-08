//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB

import banco.model.Cliente;
import banco.model.Conta;
import banco.model.Solicitacao;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolicitacaoTest {

    @Test
    void testSolicitacaoConstrutor() {
        Conta conta = new Conta(12345);
        Cliente cliente = new Cliente("João", "12345678900", 1234);
        String tipoEsperado = "Saque";
        float valorEsperado = 200.0f;

        Solicitacao solicitacao = new Solicitacao(tipoEsperado, valorEsperado, conta, cliente);

        assertEquals(tipoEsperado, solicitacao.getTipo(), "Tipo da solicitação não está correto.");
        assertEquals(valorEsperado, solicitacao.getValor(), "Valor da solicitação não está correto.");
        assertEquals(conta, solicitacao.getConta(), "Conta associada à solicitação não está correta.");
        assertEquals(cliente, solicitacao.getCliente(), "Cliente associado à solicitação não está correto.");
        assertFalse(solicitacao.isAprovado(), "A solicitação deveria ser inicialmente não aprovada.");
    }

    @Test
    void testAprovarSolicitacao() {
        Conta conta = new Conta(12345);
        Cliente cliente = new Cliente("Maria", "987.654.321-00", 4321);
        Solicitacao solicitacao = new Solicitacao("Deposito", 500.0f, conta, cliente);

        solicitacao.aprovar();

        assertTrue(solicitacao.isAprovado(), "A solicitação deveria ser aprovada após chamar o método aprovar.");
    }

    @Test
    void testSettersAndGetters() {
        Conta conta = new Conta(98765);
        Cliente cliente = new Cliente("Carlos", "111.222.333-44", 1234);
        Solicitacao solicitacao = new Solicitacao("Saque", 1000.0f, conta, cliente);

        solicitacao.setTipo("Deposito");
        solicitacao.setValor(1500.0f);
        Conta novaConta = new Conta(54321);
        solicitacao.setConta(novaConta);
        solicitacao.setAprovado(true);

        assertEquals("Deposito", solicitacao.getTipo(), "Tipo da solicitação não foi atualizado corretamente.");
        assertEquals(1500.0f, solicitacao.getValor(), "Valor da solicitação não foi atualizado corretamente.");
        assertEquals(novaConta, solicitacao.getConta(), "Conta da solicitação não foi atualizada corretamente.");
        assertTrue(solicitacao.isAprovado(), "Solicitação deveria ser aprovada.");
    }

    @Test
    void testToString() {
        Conta conta = new Conta(12345);
        Cliente cliente = new Cliente("João", "123.456.789-00", 1234);
        Solicitacao solicitacao = new Solicitacao("Saque", 200.0f, conta, cliente);

        String resultado = solicitacao.toString();

        String esperado = "Solicitação [Tipo=Saque, Valor=200.0, Conta=12345, Aprovado=false]";
        assertEquals(esperado, resultado, "O método toString não retornou a string esperada.");
    }
}

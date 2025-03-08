//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB

import banco.model.Cliente;
import banco.model.SolicitacaoCredito;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolicitacaoCreditoTest {

    @Test
    void testSolicitacaoCreditoConstrutor() {
        Cliente cliente = new Cliente("Carlos Silva", "12345678900", 1234);
        double valorEsperado = 5000.0;

        SolicitacaoCredito solicitacaoCredito = new SolicitacaoCredito(cliente, valorEsperado);

        assertEquals(cliente, solicitacaoCredito.getCliente(), "O cliente da solicitação não está correto.");
        assertEquals(valorEsperado, solicitacaoCredito.getValorSolicitado(), "O valor solicitado não está correto.");
    }

    @Test
    void testToString() {
        Cliente cliente = new Cliente("Maria Oliveira", "98765432100", 5678);
        double valorSolicitado = 1500.0;
        SolicitacaoCredito solicitacaoCredito = new SolicitacaoCredito(cliente, valorSolicitado);

        String resultado = solicitacaoCredito.toString();

        String esperado = "Cliente: Maria Oliveira, Valor solicitado: R$1500.0";
        assertEquals(esperado, resultado, "O método toString não retornou a string esperada.");
    }

    @Test
    void testGetterCliente() {
        Cliente cliente = new Cliente("João Pereira", "11122233344", 4321);
        SolicitacaoCredito solicitacaoCredito = new SolicitacaoCredito(cliente, 1000.0);

        Cliente resultado = solicitacaoCredito.getCliente();

        assertEquals(cliente, resultado, "O cliente retornado não está correto.");
    }

    @Test
    void testGetterValorSolicitado() {
        Cliente cliente = new Cliente("Ana Souza", "55566677788", 8765);
        double valorSolicitado = 2000.0;
        SolicitacaoCredito solicitacaoCredito = new SolicitacaoCredito(cliente, valorSolicitado);

        double resultado = solicitacaoCredito.getValorSolicitado();

        assertEquals(valorSolicitado, resultado, "O valor solicitado não está correto.");
    }
}

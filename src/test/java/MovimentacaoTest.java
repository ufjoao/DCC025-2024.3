//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB

import banco.model.Movimentacao;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MovimentacaoTest {

    @Test
    void testMovimentacaoConstrutor() {
        String descricaoEsperada = "Depósito";
        float valorEsperado = 500.0f;
        
        Movimentacao movimentacao = new Movimentacao(descricaoEsperada, valorEsperado);
        
        // Assert
        assertEquals(descricaoEsperada, movimentacao.getDescricao(), "A descrição deve ser a esperada");
        assertEquals(valorEsperado, movimentacao.getValor(), "O valor deve ser o esperado");
    }

    @Test
    void testToString() {
        String descricao = "Transferência";
        float valor = 150.0f;
        Movimentacao movimentacao = new Movimentacao(descricao, valor);

        String resultado = movimentacao.toString();
        
        String resultadoEsperado = "Transferência de R$ 150.0";
        assertEquals(resultadoEsperado, resultado, "O método toString não retornou o valor esperado");
    }

    @Test
    void testDescricaoNaoNula() {
        Movimentacao movimentacao = new Movimentacao("Saque", 100.0f);

        String descricao = movimentacao.getDescricao();
        
        assertNotNull(descricao, "A descrição não deve ser nula");
    }

    @Test
    void testValorPositivo() {
        Movimentacao movimentacao = new Movimentacao("Transferência", 200.0f);
        
        float valor = movimentacao.getValor();

        assertTrue(valor > 0, "O valor deve ser positivo");
    }

    @Test
    void testValorZero() {
        Movimentacao movimentacao = new Movimentacao("Saque", 0.0f);

        float valor = movimentacao.getValor();
        
        assertEquals(0.0f, valor, "O valor deve ser igual a zero quando o valor informado for zero");
    }

    @Test
    void testValorNegativo() {
        Movimentacao movimentacao = new Movimentacao("Saque", -50.0f);

        float valor = movimentacao.getValor();

        assertEquals(-50.0f, valor, "O valor negativo deve ser retornado corretamente");
    }
}

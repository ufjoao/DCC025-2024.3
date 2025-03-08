//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB

import banco.model.Investimento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvestimentoTest {

    private Investimento investimentoRendaFixa;
    private Investimento investimentoRendaVariavel;

    @BeforeEach
    void setUp() {
        // Inicializando os investimentos para os testes
        investimentoRendaFixa = new Investimento("Renda Fixa", 5.0); // 5% de taxa de rendimento
        investimentoRendaVariavel = new Investimento("Renda Variável", 10.0); // 10% de taxa de rendimento
    }

    @Test
    void testCriacaoInvestimentoRendaFixa() {
        // Verificando se o investimento foi criado corretamente
        assertNotNull(investimentoRendaFixa);
        assertEquals("Renda Fixa", investimentoRendaFixa.getTipoInvestimento());
        assertEquals(5.0, investimentoRendaFixa.getTaxaRendimento());
    }

    @Test
    void testCriacaoInvestimentoRendaVariavel() {
        // Verificando se o investimento foi criado corretamente
        assertNotNull(investimentoRendaVariavel);
        assertEquals("Renda Variável", investimentoRendaVariavel.getTipoInvestimento());
        assertEquals(10.0, investimentoRendaVariavel.getTaxaRendimento());
    }

    @Test
    void testRealizarInvestimentoRendaFixa() {
        double valorInvestido = 1000.0;
        double valorFinal = investimentoRendaFixa.realizarInvestimento(valorInvestido);

        // Verificando se o valor final é calculado corretamente
        double rendimentoEsperado = valorInvestido * (5.0 / 100);
        double valorEsperado = valorInvestido + rendimentoEsperado;

        assertEquals(valorEsperado, valorFinal, 0.01);  // tolerância de 0.01 para comparação de valores decimais
    }

    @Test
    void testRealizarInvestimentoRendaVariavel() {
        double valorInvestido = 1000.0;
        double valorFinal = investimentoRendaVariavel.realizarInvestimento(valorInvestido);

        // O valor final é calculado de forma variável, então não podemos calcular exatamente
        // Mas podemos verificar se o valor final é diferente do valor original
        assertTrue(valorFinal != valorInvestido);
    }

    @Test
    void testRealizarInvestimentoTipoInvalido() {
        // Testando para garantir que o método lance exceção para tipo inválido
        Investimento investimentoInvalido = new Investimento("Investimento Desconhecido", 5.0);

        // Verificando se a exceção é lançada
        assertThrows(IllegalArgumentException.class, () -> {
            investimentoInvalido.realizarInvestimento(1000.0);
        });
    }

    @Test
    void testImprimirInvestimento() {
        // Testando o método imprimeInvestimento
        // Não podemos validar a impressão diretamente, então vamos apenas garantir que o método não lança exceções
        assertDoesNotThrow(() -> investimentoRendaFixa.imprimeInvestimento());
        assertDoesNotThrow(() -> investimentoRendaVariavel.imprimeInvestimento());
    }
}

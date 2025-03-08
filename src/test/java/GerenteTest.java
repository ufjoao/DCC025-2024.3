//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB

import banco.model.Gerente;
import banco.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GerenteTest {

    private Gerente gerente;

    @BeforeEach
    void setUp() {
        //Um gerente para os testes
        gerente = new Gerente("Carlos Silva", "12345678900", 1234);
    }

    @Test
    void testCriacaoGerente() {
        // Verificando se o gerente foi criado corretamente
        assertNotNull(gerente);
        assertEquals("Carlos Silva", gerente.getNome());
        assertEquals("12345678900", gerente.getCpf());
        assertEquals(1234, gerente.getSenha());
    }

    @Test
    void testTipoGerente() {
        // Verificando se o tipo retornado é "Gerente"
        assertEquals("Gerente", gerente.getTipo());
    }

    @Test
    void testHerancaUsuario() {
        // Verificando se a classe Gerente herda corretamente de Usuario
        assertTrue(gerente instanceof Usuario);
    }

    @Test
    void testAlterarSenha() {
        // Alterando a senha do gerente e verificando se a alteração ocorreu corretamente
        gerente.setSenha(4321);
        assertEquals(4321, gerente.getSenha());
    }
}

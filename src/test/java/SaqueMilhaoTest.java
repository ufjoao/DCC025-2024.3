//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB

import banco.model.Cliente;
import banco.model.Conta;
import banco.model.saqueMilhao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SaqueMilhaoTest {

    private Cliente cliente;
    private Conta conta;
    private saqueMilhao saqueMilhao;

    @BeforeEach
    void setUp() {
        // Configuração antes de cada teste
        cliente = new Cliente("João Silva", "12345678900", 147258);  // Criando cliente com dados fictícios
        conta = new Conta(741852, cliente.getId());  // Criando uma conta associada ao cliente com número fictício
        conta.deposito(1000f);  // Depositar um valor inicial na conta
    }
//erro getDono null
    @Test
    void testExecutarSaque() {
        // Valor de saque
        float valorSaque = 200f;

        // Criando o objeto 'saqueMilhao' com o cliente e conta configurados
        saqueMilhao = new saqueMilhao(cliente, conta, valorSaque);

        // Antes do saque, o saldo deve ser 1000
        assertEquals(1000f, conta.getSaldo(), "O saldo da conta não deve ser 1000 antes do saque");

        // Realizando o saque
        saqueMilhao.executarSaque();

        // Após o saque, o saldo deve ser reduzido
        assertEquals(800f, conta.getSaldo(), "O saldo da conta não foi atualizado corretamente após o saque");
    }

    //erro getDono null
    @Test
    void testSaqueComSaldoInsuficiente() {
        // Criando um saque de 1200, que é maior que o saldo inicial de 1000
        float valorSaque = 1200f;

        // Criando o objeto 'saqueMilhao' com o cliente e conta configurados
        saqueMilhao = new saqueMilhao(cliente, conta, valorSaque);

        // Antes do saque, o saldo deve ser 1000
        assertEquals(1000f, conta.getSaldo(), "O saldo da conta não deve ser 1000 antes do saque");

        // Realizando o saque
        saqueMilhao.executarSaque();

        // O saldo deve permanecer 1000 após uma tentativa de saque maior que o saldo disponível
        assertEquals(1000f, conta.getSaldo(), "O saldo da conta não foi mantido devido ao saque insuficiente");
    }
}

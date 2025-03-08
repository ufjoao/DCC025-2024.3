//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB

import banco.model.Cliente;
import banco.model.Conta;
import banco.model.movimentacaoMilhao;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MovimentacaoMilhaoTest {
//erro getDono null
    @Test
    void testExecutarTransferencia() {
        Cliente cliente1 = new Cliente("João", "12345678901", 1234);
        Cliente cliente2 = new Cliente("Maria", "12345678901", 1234);

        Conta contaOrigem = new Conta(741852, cliente1.getId());
        Conta contaDestino = new Conta(147258, cliente2.getId());
        
        contaOrigem.deposito(1000f);
        contaDestino.deposito(500f);

        movimentacaoMilhao transferencia = new movimentacaoMilhao(cliente1, contaOrigem, contaDestino, 200f);

        // Verifique os saldos antes da transferência
        float saldoOrigemAntes = contaOrigem.getSaldo();
        float saldoDestinoAntes = contaDestino.getSaldo();

        transferencia.executarTransferencia();

        // Assert
        // Verifique se o saldo foi ajustado corretamente
        assertEquals(saldoOrigemAntes - 200f, contaOrigem.getSaldo(), "O saldo da conta de origem está incorreto após a transferência.");
        assertEquals(saldoDestinoAntes + 200f, contaDestino.getSaldo(), "O saldo da conta de destino está incorreto após a transferência.");

        // Verifique se as movimentações foram registradas corretamente
        assertTrue(contaOrigem.getMovimentacoes().contains("Transferência enviada para Maria Oliveira no valor de: 200.0"),
                   "A movimentação na conta de origem não foi registrada corretamente.");
        assertTrue(contaDestino.getMovimentacoes().contains("Transferência recebida de João Silva no valor de: 200.0"),
                   "A movimentação na conta de destino não foi registrada corretamente.");
    }

    //erro getDono null
    @Test
    void testExecutarTransferencia_SaldoInsuficiente() {
        // Arrange
        Cliente cliente1 = new Cliente("João", "12345678901", 1234);
        Cliente cliente2 = new Cliente("Maria", "12345678901", 1234);

        Conta contaOrigem = new Conta(741852, cliente1.getId());
        Conta contaDestino = new Conta(147258, cliente2.getId());
        
        contaOrigem.deposito(100f);
        contaDestino.deposito(500f);

        movimentacaoMilhao transferencia = new movimentacaoMilhao(cliente1, contaOrigem, contaDestino, 200f);

        transferencia.executarTransferencia();

        // Assert
        // Verifique se o saldo da conta origem e destino não foi alterado
        assertEquals(100f, contaOrigem.getSaldo(), "O saldo da conta de origem foi alterado erroneamente.");
        assertEquals(500f, contaDestino.getSaldo(), "O saldo da conta de destino foi alterado erroneamente.");
    }
}

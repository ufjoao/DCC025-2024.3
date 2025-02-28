package banco.model;

import banco.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Gerenciamento {
    
    // Método para realizar uma transferência
    public void realizarTransferencia(Cliente cliente, Conta contaOrigem, Conta contaDestino, float valor, int senha) {
        ArrayList<Conta> contas = Persistence.carregarContas(); // Carrega as contas do JSON
        List<TransferenciaMilhao> transferenciasPendentes = new ArrayList<>();
        
        if (!cliente.verificaSenha(senha)) {
            System.out.println("Senha incorreta. Transferência não realizada.");
            return;
        }

        // Buscar as instâncias corretas na lista carregada
        Conta origem = encontrarConta(contas, contaOrigem.getNumeroDaConta());
        Conta destino = encontrarConta(contas, contaDestino.getNumeroDaConta());

        if (origem == null || destino == null) {
            System.out.println("Conta de origem ou destino não encontrada.");
            return;
        }

        if (cliente.verificaSenha(senha)) {
            if (origem.getSaldo() >= valor) {
               if(valor >= 1000000)
               {
                   transferenciasPendentes.add(new TransferenciaMilhao(cliente, origem, destino, valor));
                   return;
               }
               origem.saque(valor);
               origem.adicionarMovimentacao("Transferência enviada para " + destino.getDono().getNome() + " no valor de: " + valor);
               destino.deposito(valor);
               destino.adicionarMovimentacao("Transferência recebida de " + cliente.getNome() + " no valor de: " + valor);

                // Salva os clientes atualizados no JSON
               Persistence.salvarContas(contas);
               System.out.println("Transferência realizada com sucesso!"); 
            } else {
                System.out.println("Saldo insuficiente para realizar a transferência.");
            }
        } else {
            System.out.println("Senha incorreta. Transferência não realizada.");
        }
    }

    private Conta encontrarConta(ArrayList<Conta> contas, int numeroConta) {
        for (Conta conta : contas) {
            if (conta.getNumeroDaConta() == numeroConta) {
                return conta;
            }
        }
        return null; // Retorna null se a conta não for encontrada
    }

    // Método para realizar um saque
    public void realizarSaque(Caixa caixa, Cliente cliente, Conta conta, float valor, int senha) {
        if (cliente.verificaSenha(senha)) {
            if (conta.getSaldo() >= valor) {
                conta.saque(valor);
                System.out.println("Saque realizado com sucesso!");
            } else {
                System.out.println("Saldo insuficiente.");
            }
        } else {
            System.out.println("Senha incorreta. Saque não autorizado.");
        }
    }

    // Método para realizar um depósito
    public void realizarDeposito(Caixa caixa, Conta conta, float valor) {
        conta.deposito(valor);
        System.out.println("Depósito realizado com sucesso!");
    }

    // Método para o gerente aprovar crédito
    public void aprovarCredito(Gerente gerente, Cliente cliente, float valor, int senha) {
        if (gerente.verificaSenha(senha)) {
            // Aqui a lógica de aprovação pode envolver verificação de histórico de crédito
            System.out.println("Crédito aprovado para o cliente " + cliente.getNome());
        } else {
            System.out.println("Senha incorreta. Aprovação de crédito não realizada.");
        }
    }

    // Método para cadastrar uma nova opção de investimento (pode ser realizado pelo gerente)
    public void cadastrarInvestimento(Gerente gerente, String tipoInvestimento, double taxaRendimento, int senha) {
        if (gerente.verificaSenha(senha)) {
            // Lógica para cadastrar novo investimento no sistema
            System.out.println("Investimento de tipo " + tipoInvestimento + " com taxa " + taxaRendimento + "% cadastrado.");
        } else {
            System.out.println("Senha incorreta. Investimento não cadastrado.");
        }
    }
    
    public void apoioMovimentacao(Gerente gerente, int senha) {
        Scanner teclado = new Scanner(System.in);
        List<TransferenciaMilhao> transferenciasPendentes = new ArrayList<>();
        if(gerente.verificaSenha(senha)) {
           for (int i = 0; i < transferenciasPendentes.size(); i++) {
              TransferenciaMilhao atual = transferenciasPendentes.get(i);
              System.out.println("O cliente " + atual.getNomeCliente() + " deseja transferir " + atual.getValor() + " reais da sua conta " + atual.getNumeroOrigem() + " para a conta " + atual.getNumeroDestino() + " do cliente " + atual.getNomeDestino()+ "\n");
              System.out.println("O cliente possui " + atual.getSaldoCliente() + " reais na respectiva conta\n");
              while (true) {
                 System.out.println("Você deseja permitir a transação? (sim/não)");
                 String resposta = teclado.nextLine().trim().toLowerCase();

                 if(resposta.equals("sim")) {
                     atual.executarTransferencia();
                     System.out.println("Permissão Concedida");
                     break;
                 } 
                 else if(resposta.equals("não")) {
                     System.out.println("Permissão Negada");
                     break;
                 } 
                 else  {
                     System.out.println("Resposta inválida. Por favor, responda com 'sim' ou 'não'.");
                 }
              } 
           }
        }
    }
}



    // Outros métodos para outras operações podem ser adicionados aqui


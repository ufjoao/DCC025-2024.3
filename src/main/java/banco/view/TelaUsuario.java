package banco.view;

import banco.model.Caixa;
import banco.model.Cliente;
import banco.model.Conta;
import banco.model.Gerenciamento;
import banco.model.Gerente;
import banco.persistence.Persistence;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TelaUsuario extends JFrame {

    private String tipoUsuario;  // Cliente, Caixa ou Gerente
    private int id;
    private Gerenciamento gerenciamento;

    public TelaUsuario(String tipoUsuario, int id) {
        this.tipoUsuario = tipoUsuario;
        this.id = id;
        this.gerenciamento = new Gerenciamento();

        setTitle(tipoUsuario + " - Banco");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel de boas-vindas
        JPanel painelBoasVindas = new JPanel();
        painelBoasVindas.add(new JLabel("Bem-vindo, " + tipoUsuario + "!"));
        add(painelBoasVindas, BorderLayout.NORTH);

        // Painel de ações (botões)
        JPanel painelAcoes = new JPanel();
        painelAcoes.setLayout(new GridLayout(0, 2, 10, 10));
        add(painelAcoes, BorderLayout.CENTER); // Layout dinâmico com espaçamento
        // Adiciona os botões conforme o tipo de usuário
        switch (tipoUsuario) {
            case "Cliente" -> {
                Conta contaAtual = Persistence.buscarContaPorNumero(id);
                Cliente atual = contaAtual.getDono();
                adicionarBotao(painelAcoes, "Consultar Saldo", e -> consultarSaldo(atual, contaAtual));
                adicionarBotao(painelAcoes, "Extrato", e -> consultarExtrato(atual));
                adicionarBotao(painelAcoes, "Depositar", e -> realizarDeposito(atual, id));
                adicionarBotao(painelAcoes, "Sacar", e -> realizarSaque(atual, id));
                adicionarBotao(painelAcoes, "Transferir", e -> realizarTransferencia(atual, id));
            }
            case "Caixa" -> {
                Caixa atual = Persistence.buscarCaixaPorId(id);
                adicionarBotao(painelAcoes, "Depósitos", e -> JOptionPane.showMessageDialog(this, "Função de depósito."));
                adicionarBotao(painelAcoes, "Saques", e -> JOptionPane.showMessageDialog(this, "Função de saque."));
                adicionarBotao(painelAcoes, "Transferências", e -> JOptionPane.showMessageDialog(this, "Função de transferência."));
            }
            case "Gerente" -> {
                Gerente atual = Persistence.buscarGerentePorId(id);
                adicionarBotao(painelAcoes, "Gerenciar Usuários", e -> JOptionPane.showMessageDialog(this, "Gerenciamento de usuários."));
                adicionarBotao(painelAcoes, "Aprovar Crédito", e -> JOptionPane.showMessageDialog(this, "Aprovação de crédito."));
                adicionarBotao(painelAcoes, "Relatórios Financeiros", e -> JOptionPane.showMessageDialog(this, "Relatórios financeiros."));
            }
            default -> {

            }
        }
        setVisible(true);
    }

    private void adicionarBotao(JPanel painel, String texto, ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.addActionListener(acao);
        painel.add(botao);
    }

    private void consultarSaldo(Cliente cliente, Conta conta) {
        conta = Persistence.buscarContaPorNumero(conta.getNumeroDaConta());
        if (conta == null) {
            JOptionPane.showMessageDialog(this, "Conta não encontrada!");
            return;
        }
        float saldoAtualizado = conta.getSaldo(); // Método correto para obter saldo atualizado
        JOptionPane.showMessageDialog(this, "Saldo Atualizado: R$ " + saldoAtualizado);
    }

    private void consultarExtrato(Cliente cliente) 
    {
        String extrato = cliente.gerarExtrato();
        JOptionPane.showMessageDialog(this, extrato, "Extrato", JOptionPane.INFORMATION_MESSAGE);
    }

    public void realizarDeposito(Cliente cliente, int id) 
{
    Conta contaAtual = Persistence.buscarContaPorNumero(id);
    if (contaAtual == null) 
    {
        JOptionPane.showMessageDialog(this, "Conta não encontrada!");
        return;
    }
    
    String valorStr = JOptionPane.showInputDialog("Digite o valor do depósito:");
    if (valorStr != null) {
        try {
            // Converte o valor para float apenas uma vez
            float valor = Float.parseFloat(valorStr);

            // Verifica se o valor é válido
            if (valor <= 0) 
            {
                JOptionPane.showMessageDialog(this, "O valor do depósito deve ser positivo!");
                return;
            }

            // Adiciona a movimentação de depósito
            cliente.registrarMovimentacao("Depósito", valor);

            // Realiza o depósito
            gerenciamento.realizarDeposito(cliente, valor, id);

            // Atualiza os dados do cliente no Persistence
            Persistence.salvarCliente(cliente);

            // Confirma o depósito com uma mensagem
            JOptionPane.showMessageDialog(this, "Depósito realizado com sucesso!");
        } catch (NumberFormatException e) {
            // Caso o valor seja inválido, mostra a mensagem de erro
            JOptionPane.showMessageDialog(this, "Valor inválido!");
        }
    }
}



    private void realizarSaque(Cliente cliente, int numeroDaConta) {
        Conta conta = Persistence.buscarContaPorNumero(numeroDaConta);
        String valorStr = JOptionPane.showInputDialog("Digite o valor do saque:");
        if (valorStr != null) {
            try {
                float valor = Float.parseFloat(valorStr);
                if (valor > conta.getSaldo()) {
                    JOptionPane.showMessageDialog(this, "O valor do Saque é superior ao Saldo!");
                    return;
                }

                // Busca a conta atualizada
                conta = Persistence.buscarContaPorNumero(numeroDaConta);
                if (conta == null) {
                    JOptionPane.showMessageDialog(this, "Conta não encontrada!");
                    return;
                }

                gerenciamento.realizarSaque(cliente, valor, id);

                // Atualiza os dados do cliente e salva no JSON
                Persistence.salvarCliente(cliente);

                // Atualiza os dados carregados na interface
                cliente = Persistence.buscarClientePorId(cliente.getId());

                // Exibe a confirmação do depósito
                JOptionPane.showMessageDialog(this, "Saque realizado com sucesso!");

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valor inválido!");
            }
        }
    }

    private void realizarTransferencia(Cliente cliente, int numeroDaConta) {
        Conta conta = Persistence.buscarContaPorNumero(numeroDaConta);
        String destinoStr = JOptionPane.showInputDialog("Digite o número da conta de destino:");
        String valorStr = JOptionPane.showInputDialog("Digite o valor da transferência:");
        if (destinoStr != null && valorStr != null) {
            int destino = Integer.parseInt(destinoStr);
            float valor = Float.parseFloat(valorStr);
            if(valor > 0){
                Conta contaDestino = Persistence.buscarContaPorNumero(destino);
                if (conta == null || contaDestino == null) {
                    JOptionPane.showMessageDialog(this, "Conta não encontrada!");
                    return;
                }
                float saldoAT = conta.getSaldo();
                gerenciamento.realizarTransferencia(numeroDaConta, valor, destino);
                if(saldoAT == (saldoAT - valor)){
                    JOptionPane.showMessageDialog(this, "Transferência realizada com sucesso!");
                }
                else
                    JOptionPane.showMessageDialog(this, "Transferência não realizada");
            }
        }
    }
}

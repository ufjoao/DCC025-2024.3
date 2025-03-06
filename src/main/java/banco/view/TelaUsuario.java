package banco.view;

import banco.model.Caixa;
import banco.model.Cliente;
import banco.model.Conta;
import banco.model.Gerenciamento;
import banco.model.Gerente;
import banco.persistence.Persistence;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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
                adicionarBotao(painelAcoes, "Consultar Saldo", e -> consultarSaldo(atual));
                adicionarBotao(painelAcoes, "Extrato", e -> consultarExtrato(atual));
                adicionarBotao(painelAcoes, "Depositar", e -> realizarDeposito(atual));
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

    private void consultarSaldo(Cliente cliente) {
        JOptionPane.showMessageDialog(this, "Saldo: R$ " + cliente.consultarSaldo());
    }

    private void consultarExtrato(Cliente cliente) {
        String extrato = cliente.gerarExtrato();
        JOptionPane.showMessageDialog(this, extrato, "Extrato", JOptionPane.INFORMATION_MESSAGE);
    }

    private void realizarDeposito(Cliente cliente) {
        String valorStr = JOptionPane.showInputDialog("Digite o valor do depósito:");
        if (valorStr != null) {
            float valor = Float.parseFloat(valorStr);
            gerenciamento.realizarDeposito(cliente, valor);
            JOptionPane.showMessageDialog(this, "Depósito realizado com sucesso!");
        }
    }

    private void realizarSaque(Cliente cliente, int numeroDaConta) {
        String valorStr = JOptionPane.showInputDialog("Digite o valor do saque:");
        if (valorStr != null) {
            float valor = Float.parseFloat(valorStr);
            gerenciamento.realizarSaque(cliente, valor, cliente.getSenha(), numeroDaConta);
            JOptionPane.showMessageDialog(this, "Saque realizado com sucesso!");
        }
    }

    private void realizarTransferencia(Cliente cliente, int numeroDaConta) {
        String destinoStr = JOptionPane.showInputDialog("Digite o número da conta de destino:");
        String valorStr = JOptionPane.showInputDialog("Digite o valor da transferência:");
        if (destinoStr != null && valorStr != null) {
            int destino = Integer.parseInt(destinoStr);
            float valor = Float.parseFloat(valorStr);
            gerenciamento.realizarTransferencia(numeroDaConta, valor, cliente.getSenha());
            JOptionPane.showMessageDialog(this, "Transferência realizada com sucesso!");
        }
    }
}

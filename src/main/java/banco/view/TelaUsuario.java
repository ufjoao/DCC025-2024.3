package banco.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaUsuario extends JFrame {
    private String tipoUsuario;  // Cliente, Caixa ou Gerente

    public TelaUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
        
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
        painelAcoes.setLayout(new GridLayout(0, 2, 10, 10));  // Layout dinâmico com espaçamento

        // Adiciona os botões conforme o tipo de usuário
        if (tipoUsuario.equals("Cliente")) {
            adicionarBotao(painelAcoes, "Consultar Saldo");
            adicionarBotao(painelAcoes, "Extrato");
            adicionarBotao(painelAcoes, "Investimentos");
            adicionarBotao(painelAcoes, "Empréstimos");
            adicionarBotao(painelAcoes, "Financiamentos");
        } else if (tipoUsuario.equals("Caixa")) {
            adicionarBotao(painelAcoes, "Depósitos");
            adicionarBotao(painelAcoes, "Saques");
            adicionarBotao(painelAcoes, "Transferências");
        } else if (tipoUsuario.equals("Gerente")) {
            adicionarBotao(painelAcoes, "Gerenciar Usuários");
            adicionarBotao(painelAcoes, "Aprovar Crédito");
            adicionarBotao(painelAcoes, "Relatórios Financeiros");
        }

        // Botão de sair (comum a todos)
        JButton botaoSair = new JButton("Sair");
        botaoSair.addActionListener(e -> dispose());
        painelAcoes.add(botaoSair);

        add(painelAcoes, BorderLayout.CENTER);

        setVisible(true);
    }

    private void adicionarBotao(JPanel painel, String texto) {
        JButton botao = new JButton(texto);
        painel.add(botao);
        
        botao.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Ação: " + texto);
            // Aqui você pode chamar outras telas, dependendo da ação
        });
    }
}

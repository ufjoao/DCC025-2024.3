package banco.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame {
    private JTextField campoCpf;
    private JPasswordField campoSenha;
    private JButton botaoEntrar, botaoCadastrar;

    public TelaLogin() {
        setTitle("Login - Banco");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        // Componentes da tela
        add(new JLabel("CPF:"));
        campoCpf = new JTextField();
        add(campoCpf);

        add(new JLabel("Senha:"));
        campoSenha = new JPasswordField();
        add(campoSenha);

        botaoEntrar = new JButton("Entrar");
        botaoCadastrar = new JButton("Cadastrar");
        add(botaoEntrar);
        add(botaoCadastrar);

        // Ação do botão "Entrar"
        botaoEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Simula o login e abre a tela de boas-vindas
                abrirTelaBoasVindas();
            }
        });

        setVisible(true);  // Tornar a janela visível
    }

    // Método para abrir a tela de boas-vindas após o login
    private void abrirTelaBoasVindas() {
        // Fechar a tela de login
        this.dispose();

        // Criar e mostrar a tela de boas-vindas
        JFrame telaBoasVindas = new JFrame("Boas-vindas");
        telaBoasVindas.setSize(300, 150);
        telaBoasVindas.setLocationRelativeTo(null); // Janela centralizada
        telaBoasVindas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Adiciona um painel simples com uma mensagem de boas-vindas
        JPanel painelBoasVindas = new JPanel();
        painelBoasVindas.add(new JLabel("Bem-vindo ao Banco!"));
        telaBoasVindas.add(painelBoasVindas);

        telaBoasVindas.setVisible(true);
    }
}

package banco.view;

import banco.model.Cliente;
import banco.model.Caixa;
import banco.model.Gerente;
import banco.persistence.Persistence;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

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
        botaoEntrar.addActionListener((ActionEvent e) -> autenticarUsuario());

        // Ação do botão "Cadastrar"
        botaoCadastrar.addActionListener((ActionEvent e) -> cadastrarUsuario());

        setVisible(true);
    }

    private void autenticarUsuario() {
        String cpf = campoCpf.getText();
        String senhaDigitada = new String(campoSenha.getPassword());

        // Carregar lista de usuários (Clientes, Caixas, Gerentes)
        List<Cliente> clientes = Persistence.carregarClientes();
        List<Caixa> caixas = Persistence.carregarCaixas();
        List<Gerente> gerentes = Persistence.carregarGerentes();

        // Verifica se é Cliente
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf) && String.valueOf(c.getSenha()).equals(senhaDigitada)) {
                abrirTelaSelecaoConta(c, "Cliente");
                return;
            }
        }

        // Verifica se é Caixa
        for (Caixa cx : caixas) {
            if (cx.getCpf().equals(cpf) && String.valueOf(cx.getSenha()).equals(senhaDigitada)) {
                abrirTelaUsuario(cx.getId(), "Caixa");
                return;
            }
        }

        // Verifica se é Gerente
        for (Gerente g : gerentes) {
            if (g.getCpf().equals(cpf) && String.valueOf(g.getSenha()).equals(senhaDigitada)) {
                abrirTelaUsuario(g.getId(), "Gerente");
                return;
            }
        }

        // Se não encontrou nenhum usuário válido
        JOptionPane.showMessageDialog(this, "CPF ou senha incorretos!", "Erro de Login", JOptionPane.ERROR_MESSAGE);
    }

    private void cadastrarUsuario() {
        new TelaSelecaoCadastro();
        this.dispose();
    }

    private void abrirTelaUsuario(int id, String tipoUsuario) {
        JOptionPane.showMessageDialog(this, "Login bem-sucedido! Tipo: " + tipoUsuario);
        this.dispose(); // Fecha a tela de login
        new TelaUsuario(tipoUsuario, id); // Abre a tela do usuário correspondente
    }
    
        private void abrirTelaSelecaoConta(Cliente cliente, String tipoUsuario) {
        JOptionPane.showMessageDialog(this, "Login bem-sucedido! Tipo: " + tipoUsuario);
        this.dispose(); // Fecha a tela de login
        new TelaSelecaoConta(cliente); // Abre a tela do usuário correspondente
    }
}

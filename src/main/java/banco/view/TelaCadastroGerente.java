package banco.view;

import banco.model.Gerente;
import banco.persistence.Persistence;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class TelaCadastroGerente extends JFrame {
    
    private JTextField campoNome;
    private JTextField campoCpf;
    private JPasswordField campoSenha;

    public TelaCadastroGerente() {
        setTitle("Cadastro de Gerente");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout e componentes
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        campoNome = new JTextField(20);
        campoCpf = new JTextField(20);
        campoSenha = new JPasswordField(20);

        JButton botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.addActionListener(this::cadastrarGerente);

        panel.add(new JLabel("Nome:"));
        panel.add(campoNome);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(new JLabel("CPF:"));
        panel.add(campoCpf);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(new JLabel("Senha:"));
        panel.add(campoSenha);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(botaoCadastrar);

        add(panel);
        setVisible(true);
    }

    private void cadastrarGerente(ActionEvent e) {
        String nome = campoNome.getText();
        String cpf = campoCpf.getText();
        String senha = new String(campoSenha.getPassword());

        Gerente novoGerente = new Gerente(nome, cpf, Integer.parseInt(senha));
        Persistence.addGerente(novoGerente);

        JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
        new TelaLogin();
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaCadastroCliente::new);
    }
}
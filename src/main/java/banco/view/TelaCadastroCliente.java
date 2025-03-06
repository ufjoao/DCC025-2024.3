package banco.view;

import banco.exception.Validador;
import banco.model.Cliente;
import banco.persistence.Persistence;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaCadastroCliente extends JFrame {

    private JTextField campoNome;
    private JTextField campoCpf;
    private JPasswordField campoSenha;
    private JTextField campoNumeroConta;
    private JTextField campoSaldoConta;

    public TelaCadastroCliente() {
        setTitle("Cadastro de Cliente");
        setSize(400, 350); // Ajustei o tamanho para caber todos os campos
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout e componentes
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        campoNome = new JTextField(20);
        campoCpf = new JTextField(20);
        campoSenha = new JPasswordField(20);
        campoNumeroConta = new JTextField(20);

        JButton botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.addActionListener(this::cadastrarCliente);

        panel.add(new JLabel("Nome:"));
        panel.add(campoNome);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(new JLabel("CPF:"));
        panel.add(campoCpf);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(new JLabel("Senha:"));
        panel.add(campoSenha);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(new JLabel("Número da Conta:"));
        panel.add(campoNumeroConta);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(botaoCadastrar);

        add(panel);
        setVisible(true);
    }

    private void cadastrarCliente(ActionEvent e) {
        String cpf = " ";
        if(Validador.cpfValido(campoCpf.getText())){
            cpf = campoCpf.getText(); 
        }                       
        else
            JOptionPane.showMessageDialog(this, "CPF inválido!", "Erro de Login", JOptionPane.ERROR_MESSAGE);
        String nome = campoNome.getText();
        
        String senha = new String(campoSenha.getPassword());
        int numeroDaConta = Integer.parseInt(campoNumeroConta.getText());

        // Criar o novo cliente
        Cliente novoCliente = new Cliente(nome, cpf, Integer.parseInt(senha));

        // Criar e vincular a conta ao cliente
        novoCliente.criarConta(numeroDaConta);

        // Adicionar o cliente à persistência
        Persistence.addCliente(novoCliente);

        JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
        new TelaLogin();
        this.dispose();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaCadastroCliente::new);
    }
}

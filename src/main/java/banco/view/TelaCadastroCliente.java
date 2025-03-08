//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB

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
        
        
        String nome = " ";
        if(Validador.nomeValido(campoNome.getText()))
            nome = campoNome.getText();
        else
            JOptionPane.showMessageDialog(this, "Nome inválido!", "Erro de Login", JOptionPane.ERROR_MESSAGE);
        
        char[] senha = null;
        
        if(Validador.senhaValida(campoSenha.getPassword())) 
            senha = (campoSenha.getPassword());
        else
            JOptionPane.showMessageDialog(this, "Senha inválida!", "Erro de Login", JOptionPane.ERROR_MESSAGE);
        
        
        int numeroDaConta = 0;
        int numeroDaContaAux = Integer.parseInt(campoNumeroConta.getText());
        if(Validador.numeroContaValido(numeroDaContaAux))
            numeroDaConta = numeroDaContaAux;
        else
            JOptionPane.showMessageDialog(this, "Número da conta inválido!", "Erro de Login", JOptionPane.ERROR_MESSAGE);

        // Criar o novo cliente
        String senhaStr = new String(senha);
        Cliente novoCliente = new Cliente(nome, cpf, Integer.parseInt(senhaStr));

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

package banco.view;

import banco.exception.Validador;
import banco.model.Caixa;
import banco.persistence.Persistence;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaCadastroCaixa extends JFrame {
    
    private JTextField campoNome;
    private JTextField campoCpf;
    private JPasswordField campoSenha;

    public TelaCadastroCaixa() {
        setTitle("Cadastro de Caixa");
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
        botaoCadastrar.addActionListener(this::cadastrarCaixa);

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

    private void cadastrarCaixa(ActionEvent e) 
    {
        String nome = " ";
        if(Validador.nomeValido(campoNome.getText()))
            nome = campoNome.getText();
        else
            JOptionPane.showMessageDialog(this, "Nome inválido!", "Erro de Login", JOptionPane.ERROR_MESSAGE);
        
        String cpf = " ";
        if(Validador.cpfValido(campoCpf.getText())){
            cpf = campoCpf.getText(); 
        }                       
        else
            JOptionPane.showMessageDialog(this, "CPF inválido!", "Erro de Login", JOptionPane.ERROR_MESSAGE);
        
       
        char[] senha = null;
        
        if(Validador.senhaValida(campoSenha.getPassword())) 
            senha = (campoSenha.getPassword());
        else
            JOptionPane.showMessageDialog(this, "Senha inválida!", "Erro de Login", JOptionPane.ERROR_MESSAGE);
        
        String senhaStr = new String(senha);
        Caixa novoCaixa = new Caixa(nome, cpf, Integer.parseInt(senhaStr));
        Persistence.addCaixa(novoCaixa);

        JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
        new TelaLogin();
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaCadastroCliente::new);
    }
}

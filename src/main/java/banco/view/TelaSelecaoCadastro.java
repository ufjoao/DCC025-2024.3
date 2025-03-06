package banco.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaSelecaoCadastro extends JFrame {
    
    public TelaSelecaoCadastro() {
        setTitle("Bem-vindo ao Cadastro de Usuários!");
        setSize(400, 300);
        setLocationRelativeTo(null);  // Centraliza a janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout e componentes
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel label = new JLabel("Selecione o tipo de usuário");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton botaoCliente = new JButton("Cadastrar Cliente");
        botaoCliente.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoCliente.addActionListener(this::abrirCadastroCliente);

        JButton botaoCaixa = new JButton("Cadastrar Caixa");
        botaoCaixa.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoCaixa.addActionListener(this::abrirCadastroCaixa);

        JButton botaoGerente = new JButton("Cadastrar Gerente");
        botaoGerente.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoGerente.addActionListener(this::abrirCadastroGerente);

        // Adicionando componentes ao painel
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        panel.add(botaoCliente);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(botaoCaixa);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(botaoGerente);

        // Exibindo a janela
        add(panel);
        setVisible(true);
    }

    private void abrirCadastroCliente(ActionEvent e) {
        new TelaCadastroCliente();
        this.dispose();
    }

    private void abrirCadastroCaixa(ActionEvent e) {
        new TelaCadastroCaixa();
        this.dispose();
    }

    private void abrirCadastroGerente(ActionEvent e) {
        new TelaCadastroGerente();
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaSelecaoCadastro::new);
    }
}

//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB

package banco.view;

import banco.model.Cliente;
import banco.model.Conta;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class TelaSelecaoConta extends JFrame {

    private Cliente cliente;

    public TelaSelecaoConta(Cliente cliente) {
        this.cliente = cliente;
        setTitle("Seleção de Conta");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Título
        panel.add(new JLabel("Selecione a conta que deseja usar"));

        // Adicionar os números das contas em uma lista
        JList<String> listaContas = new JList<>();
        DefaultListModel<String> model = new DefaultListModel<>();

        // Adicionar os números das contas à lista
        for (Conta conta : cliente.getContas()) {
            model.addElement(String.valueOf(conta.getNumeroDaConta()));
        }
        listaContas.setModel(model);

        JScrollPane scrollPane = new JScrollPane(listaContas);
        panel.add(scrollPane);

        // Botão para selecionar a conta
        JButton botaoSelecionar = new JButton("Selecionar Conta");
        botaoSelecionar.addActionListener((ActionEvent e) -> selecionarConta(listaContas));

        panel.add(botaoSelecionar);

        add(panel);
        setVisible(true);
    }

    private void selecionarConta(JList<String> listaContas) {
        String contaSelecionada = listaContas.getSelectedValue();

        if (contaSelecionada != null) {
            // Convertendo o número da conta de String para int
            int numeroConta = Integer.parseInt(contaSelecionada);
            Conta conta = buscarContaPeloNumero(numeroConta);

            // Exemplo: Exibir um JOptionPane com a conta selecionada
            JOptionPane.showMessageDialog(this, "Você selecionou a conta número: " + conta.getNumeroDaConta());
            abrirTelaUsuario(conta.getNumeroDaConta(), "Cliente");
            this.dispose();

            // Aqui, você pode fazer o que for necessário com a conta, por exemplo, redirecionar para outra tela
            // ou permitir ao cliente realizar operações na conta.
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma conta!");
        }
    }

    private Conta buscarContaPeloNumero(int numeroConta) {
        for (Conta conta : cliente.getContas()) {
            if (conta.getNumeroDaConta() == numeroConta) {
                return conta;
            }
        }
        return null;
    }

    private void abrirTelaUsuario(int id, String tipoUsuario) {
        JOptionPane.showMessageDialog(this, "Login bem-sucedido! Tipo: " + tipoUsuario);
        new TelaUsuario(tipoUsuario, id);
        this.dispose();
    }
}

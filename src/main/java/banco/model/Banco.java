//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package banco.model;

import banco.persistence.Persistence;
import banco.view.TelaLogin;
import javax.swing.SwingUtilities;

/**
 *
 * @author joao
 */
public class Banco {

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> new TelaLogin());

//        o teste deverá ser feito dentro da gerenciamento, já que realizarTransferencia agora recebe
//        o número das contas, o valor e uma senha
//        gerenciador.realizarTransferencia(c1, conta1, conta2, 150.50f, 340725);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package banco.model;

import banco.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joao
 */
public class Banco {

    public static void main(String[] args) {
        // Exemplo de criação de uma nova conta
        Conta conta1 = new Conta(598764, 135498);
        Cliente c1 = new Cliente("Joao", "14397509638", 340725);
        Conta conta2 = new Conta(487596, 152436);
        Gerenciamento gerenciador = new Gerenciamento();
        gerenciador.realizarTransferencia(c1, conta1, conta2, 150.50, 135498);

        // Outra conta
    }
}

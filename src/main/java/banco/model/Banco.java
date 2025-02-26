/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package banco.model;

import banco.persistence.Persistence;

/**
 *
 * @author joao
 */
public class Banco {

    public static void main(String[] args) {
        // Exemplo de criação de uma nova conta
        Cliente c1 = new Cliente("Joao", "14397509638", 340725);
        Conta conta1 = new Conta(598764, 135498, c1);
        conta1.deposito(151.00f);
        Cliente c2 = new Cliente("Rodrigo", "12267645688", 250734);
        Conta conta2 = new Conta(487596, 152436, c2);
        // Adicionando as contas ao JSON
        Persistence.addConta(conta1);
        Persistence.addConta(conta2);

        // Agora podemos operar nas contas
        conta1.deposito(151.00f);
        Persistence.addConta(conta1);
        Persistence.salvarContas(Persistence.carregarContas());
        Gerenciamento gerenciador = new Gerenciamento();
        gerenciador.realizarTransferencia(c1, conta1, conta2, 150.50f, 340725);
    }
}

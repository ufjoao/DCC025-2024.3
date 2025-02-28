/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco.model;

/**
 *
 * @author rodri
 */
public class Gerente extends Usuario {
    
    public Gerente(String nome, String cpf, int senha) {
        super(nome, cpf, senha);
    }

    public void adicionarCliente(Cliente cliente) {
        System.out.println("Cliente " + cliente.getNome() + " adicionado ao banco.");
    }

    public void removerCliente(Cliente cliente) {
        System.out.println("Cliente " + cliente.getNome() + " removido do banco.");
    }    
}

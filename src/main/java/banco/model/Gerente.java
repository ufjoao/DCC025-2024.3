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

    @Override
    public String getTipo() {
        return "Gerente";
    }
}
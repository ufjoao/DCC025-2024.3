/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco.model;

public class Movimentacao {
    private String descricao;
    private float valor;

    public Movimentacao(String descricao, float valor) {
        this.descricao = descricao;
        this.valor = valor;
    }
    
    public String getDescricao() {
        return descricao;
    }

    public float getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return descricao + " de R$ " + valor;
    }
}

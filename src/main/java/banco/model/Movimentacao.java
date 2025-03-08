//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco.model;

import java.io.Serializable;

public class Movimentacao implements Serializable {
    private static final long serialVersionUID = 1L;
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

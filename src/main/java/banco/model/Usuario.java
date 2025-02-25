/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco.model;

/**
 *
 * @author rodri
 */
public class Usuario 
{
    private String nome;
    private String documento;
    int id;
    String senha;
    
    public Usuario(String nome, String documento, int id, String senha)
    {
        this.nome = nome;
        this.documento = documento;
        this.id = id;
        this.senha = senha;
    }
    
    public String getNome()
    {
        return nome;
    }
    
    public String getDocumento()
    {
        return documento;
    }
    
    public boolean verificaSenha(String senha)
    {
        return senha.equals(this.senha);
    }
}

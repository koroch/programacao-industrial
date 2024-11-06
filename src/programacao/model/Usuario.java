/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programacao.model;

import java.util.List;
import static view.Programacao.ultimoIdUser;

/**
 *
 * @author Koroch
 */
public class Usuario {
    private int id;
    private String nome;
    private String nomeDeGuerra;
    private String funcao;
    private boolean carteiraDeCarro;

    public Usuario(int id, String nome, String nomeDeGuerra, String funcao, boolean carteiraDeCarro) {
        this.id = id;
        this.nome = nome;
        this.nomeDeGuerra = nomeDeGuerra;
        this.funcao = funcao;
        this.carteiraDeCarro = carteiraDeCarro;
    }
    
    public Usuario(String nome, String nomeDeGuerra, String funcao, boolean carteiraDeCarro) {
        this.id = ultimoIdUser + 1;
        ultimoIdUser++;
        this.nome = nome;
        this.nomeDeGuerra = nomeDeGuerra;
        this.funcao = funcao;
        this.carteiraDeCarro = carteiraDeCarro;
    }

    public Usuario() {}

    public Usuario getByName(String nome, List<Usuario> users){
        return users.stream().filter(x -> x.getNome().equals(nome)).findFirst().orElse(null);
    }
    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getNomeDeGuerra() {
        return nomeDeGuerra;
    }

    public String getFuncao() {
        return funcao;
    }

    public boolean isCarteiraDeCarro() {
        return carteiraDeCarro;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNomeDeGuerra(String nomeDeGuerra) {
        this.nomeDeGuerra = nomeDeGuerra;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public void setCarteiraDeCarro(boolean carteiraDeCarro) {
        this.carteiraDeCarro = carteiraDeCarro;
    }
    
    @Override
    public String toString() {
        return this.id+","+this.nome+","+this.nomeDeGuerra+","+this.funcao+","+this.carteiraDeCarro;
    }
    
    
}
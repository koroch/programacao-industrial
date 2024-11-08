/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programacao.model;

import java.util.List;
import static view.Programacao.ultimoIdCliente;

/**
 *
 * @author Koroch
 */
public class Cliente {
    private int id;
    private String nome;
    private String cidade;
    private Estado estado;

    public Cliente(int id, String nome, String cidade, Estado estado) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Cliente(String nome, String cidade, Estado estado) {
        this.id = ultimoIdCliente + 1;
        ultimoIdCliente++;
        this.nome = nome;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Cliente() {}
    
    public Cliente getById(int id, List<Cliente> clientes){
        return clientes.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }
    
    public Cliente getByNameEmpresa(String nomeEmpresa, List<Cliente> clientes){
        return clientes.stream().filter(x -> x.getNome().equals(nomeEmpresa)).findFirst().orElse(null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return this.id+"|"+this.nome+"|"+this.cidade+"|"+this.estado;
    }
    
    
    
}

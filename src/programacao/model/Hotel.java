/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programacao.model;

import java.util.List;
import static view.Programacao.ultimoIdHotel;

/**
 *
 * @author Koroch
 */
public class Hotel {
    private int id;
    private String nome;
    private String cidade;
    private Estado estado;
    private String endereco;

    public Hotel(int id, String nome, String cidade, Estado estado, String endereco) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.estado = estado;
        if(!endereco.equals("")){
            this.endereco = endereco;
        }
    }

    public Hotel(String nome, String cidade, Estado estado, String endereco) {
        ultimoIdHotel++;
        this.id = ultimoIdHotel;
        this.nome = nome;
        this.cidade = cidade;
        this.estado = estado;
        if(!endereco.equals("")){
            this.endereco = endereco;
        }
    }

    public Hotel() {}
    
    public Hotel getById(int id, List<Hotel> hoteis){
        return hoteis.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }
    
    public Hotel getByNameHotel(String nome, List<Hotel> hoteis){
        return hoteis.stream().filter(x -> x.getNome().equals(nome)).findFirst().orElse(null);
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
    
    public String getNomeComCidadeEEstado() {
        return nome+", "+cidade+", "+estado;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
   
    @Override
    public String toString() {
        return this.id+"|"+this.nome+"|"+this.cidade+"|"+this.estado+"|"+this.endereco;
    }
    
    
    
}

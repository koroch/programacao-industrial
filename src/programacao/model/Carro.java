/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programacao.model;

import java.util.List;
import static view.Programacao.ultimoIdCarro;

/**
 *
 * @author Koroch
 */
public class Carro {
    private int id;
    private String nome;
    private String marca;
    private String placa;

    public Carro(String nome, String marca, String placa) {
        this.id = ultimoIdCarro + 1;
        ultimoIdCarro++;
        this.nome = nome;
        this.marca = marca;
        this.placa = placa;
    }

    public Carro(int id, String nome, String marca, String placa) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.placa = placa;
    }

    public Carro() {}

    public Carro getById(int id, List<Carro> carros){
        return carros.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }
    
    public Carro getByName(String nome, List<Carro> carros){
        return carros.stream().filter(x -> x.getNome().equals(nome)).findFirst().orElse(null);
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
    
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }    
    
    @Override
    public String toString() {
        return this.id+"|"+this.nome+"|"+this.marca+"|"+this.placa;
    }
}

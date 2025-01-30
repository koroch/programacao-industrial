/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programacao.model;

import java.util.List;
import utils.RoutesGoogleMaps;
import view.GerenciamentoHorasEKms;
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
    private double distanciaEmKm;

    public Cliente(int id, String nome, String cidade, Estado estado, double distanciaEmKm) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.estado = estado;
        this.distanciaEmKm = distanciaEmKm;
    }

    public Cliente(String nome, String cidade, Estado estado) {
        ultimoIdCliente++;
        this.id = ultimoIdCliente;
        this.nome = nome;
        this.cidade = cidade;
        this.estado = estado;

        RoutesGoogleMaps routesGM = new RoutesGoogleMaps(
            GerenciamentoHorasEKms.EMPRESA,
            (nome +" | "+ cidade +" | "+ estado)
        );
        this.distanciaEmKm = (Math.round(routesGM.getKms() * 100.0) / 100.0);
        
    }

    public Cliente() {}

    public double getDistanciaEmKm() {
        return distanciaEmKm;
    }

    public void setDistanciaEmKm(double distanciaEmKm) {
        this.distanciaEmKm = distanciaEmKm;
    }
    
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

    public String getBuscaEndereco() {
        return this.nome+", "+this.cidade+" - "+this.estado;
    }
    
    public String getCidadeEEstado() {
        return this.cidade+" - "+this.estado;
    }
    
    @Override
    public String toString() {
        return this.id+"|"+this.nome+"|"+this.cidade+"|"+this.estado+"|"+this.distanciaEmKm;
    }
    
    
    
}

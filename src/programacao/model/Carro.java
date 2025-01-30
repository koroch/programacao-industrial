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
    private String imei_rastreador;
    private int km_atual_gps;
    private String data_km_atual;
    private int km_ultima_troca;
    private String data_km_atual_automatica_gps;
    private int multiploProximaTroca;
    private int km_atual_maps;
    private String data_km_atual_automatica_maps;
    
    public Carro(String nome, String marca, String placa, String imei_rastreador, int km_atual, String data_km_atual, int km_ultima_troca, String data_km_atual_automatica, int multiploProximaTroca) {
        this.id = ultimoIdCarro + 1;
        ultimoIdCarro++;
        this.nome = nome;
        this.marca = marca;
        this.placa = placa;
        this.imei_rastreador = imei_rastreador;
        this.km_atual_gps = km_atual;
        this.data_km_atual = data_km_atual;
        this.km_ultima_troca = km_ultima_troca;
        this.data_km_atual_automatica_gps = data_km_atual_automatica;
        this.multiploProximaTroca = multiploProximaTroca;
        this.km_atual_maps = km_atual;
        this.data_km_atual_automatica_maps = data_km_atual;
    }

    public Carro(int id, String nome, String marca, String placa, String imei_rastreador, int km_atual, String data_km_atual, int km_ultima_troca, String data_km_atual_automatica, int multiploProximaTroca, int km_atual_maps, String data_km_atual_automatica_maps) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.placa = placa;
        this.imei_rastreador = imei_rastreador;
        this.km_atual_gps = km_atual;
        this.data_km_atual = data_km_atual;
        this.km_ultima_troca = km_ultima_troca;
        this.data_km_atual_automatica_gps = data_km_atual_automatica;
        this.multiploProximaTroca = multiploProximaTroca;
        this.km_atual_maps = km_atual_maps;
        this.data_km_atual_automatica_maps = data_km_atual_automatica_maps;
    }

    public Carro() {}

    public int getKm_atual_gps() {
        return km_atual_gps;
    }

    public void setKm_atual_gps(int km_atual_gps) {
        this.km_atual_gps = km_atual_gps;
    }

    public String getData_km_atual_automatica_gps() {
        return data_km_atual_automatica_gps;
    }

    public void setData_km_atual_automatica_gps(String data_km_atual_automatica_gps) {
        this.data_km_atual_automatica_gps = data_km_atual_automatica_gps;
    }

    public int getKm_atual_maps() {
        return km_atual_maps;
    }

    public void setKm_atual_maps(int km_atual_maps) {
        this.km_atual_maps = km_atual_maps;
    }

    public String getData_km_atual_automatica_maps() {
        return data_km_atual_automatica_maps;
    }

    public void setData_km_atual_automatica_maps(String data_km_atual_automatica_maps) {
        this.data_km_atual_automatica_maps = data_km_atual_automatica_maps;
    }

    
    
    public int getMultiploProximaTroca() {
        return multiploProximaTroca;
    }

    public void setMultiploProximaTroca(int multiploProximaTroca) {
        this.multiploProximaTroca = multiploProximaTroca;
    }

    public String getData_km_atual_automatica() {
        return data_km_atual_automatica_gps;
    }

    public void setData_km_atual_automatica(String data_km_atual_automatica) {
        this.data_km_atual_automatica_gps = data_km_atual_automatica;
    }

    public String getImei_rastreador() {
        return imei_rastreador;
    }

    public void setImei_rastreador(String imei_rastreador) {
        this.imei_rastreador = imei_rastreador;
    }

    public int getKm_atual() {
        return km_atual_gps;
    }

    public void setKm_atual(int km_atual) {
        this.km_atual_gps = km_atual;
    }

    public String getData_km_atual() {
        return data_km_atual;
    }

    public void setData_km_atual(String data_km_atual) {
        this.data_km_atual = data_km_atual;
    }

    public int getKm_proxima_troca() {
        return km_ultima_troca+(1000*this.multiploProximaTroca);
    }
    
    public int getKm_ultima_troca() {
        return km_ultima_troca;
    }

    public void setKm_ultima_troca(int km_ultima_troca) {
        this.km_ultima_troca = km_ultima_troca;
    }

    
    
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
        return this.id+"|"+this.nome+"|"+this.marca+"|"+this.placa+"|"+this.imei_rastreador+"|"+this.km_atual_gps+"|"+this.data_km_atual+"|"+this.km_ultima_troca+"|"+this.data_km_atual_automatica_gps+"|"+this.multiploProximaTroca;
    }
}

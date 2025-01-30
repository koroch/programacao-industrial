/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programacao.model;

/**
 *
 * @author Koroch
 */
public class DadosAcumuladosPorCarro {
    private String placaCarro;
    private double totalKmPercorrido = 0;

    public DadosAcumuladosPorCarro(String placaCarro) {
        this.placaCarro = placaCarro;
        this.totalKmPercorrido = 0;
    }
    
    public void resetarValores() {
        totalKmPercorrido = 0;
    }
    
    public void adicionarKm(double km) {
        this.totalKmPercorrido += km;
    }

    public String getPlacaCarro() {
        return placaCarro;
    }

    public double getTotalKmPercorrido() {
        return totalKmPercorrido;
    }
}

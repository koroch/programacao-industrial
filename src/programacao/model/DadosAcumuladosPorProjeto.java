/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programacao.model;

/**
 *
 * @author Koroch
 */
public class DadosAcumuladosPorProjeto {
    private String numeroProjeto;
    private String nomeCliente;
    private double totalKmPercorrido = 0;
    private double totalHorasEngenharia = 0;
    private double totalHorasExecucao = 0;

    public void resetarValores() {
        this.totalKmPercorrido = 0;
        this.totalHorasExecucao = 0;
        this.totalHorasEngenharia = 0;
    }
    
    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
    
    public DadosAcumuladosPorProjeto(String numeroProjeto) {
        this.numeroProjeto = numeroProjeto;
        resetarValores();
    }

    public void adicionarKm(double km) {
        this.totalKmPercorrido += km;
    }

    public void adicionarHorasExecucao(double horas) {
        this.totalHorasExecucao += horas;
    }
    
    public void adicionarHorasEngenharia(double horas) {
        this.totalHorasEngenharia += horas;
    }

    public String getNumeroProjeto() {
        return numeroProjeto;
    }

    public double getTotalKmPercorrido() {
        return totalKmPercorrido;
    }

    public double getTotalHorasExecucao() {
        return totalHorasExecucao;
    }
    
    public double getTotalHorasEngenharia() {
        return totalHorasEngenharia;
    }
}

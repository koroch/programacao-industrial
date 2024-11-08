/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programacao.model;

import java.util.List;
import static view.Programacao.ultimoIdDemaisInfos;

/**
 *
 * @author Koroch
 */
public class DemaisInfos {
    private int id;
    private String dataProgramacao;
    private List<Usuario> mecanicosInternos;
    private List<Usuario> eletricistasInternos;
    private List<Usuario> folgas;
    private List<Usuario> ferias;

    public DemaisInfos(int id, String dataProgramacao, List<Usuario> mecanicosInternos, List<Usuario> eletricistasInternos, List<Usuario> folgas, List<Usuario> ferias) {
        this.id = id;
        this.dataProgramacao = dataProgramacao;
        this.mecanicosInternos = mecanicosInternos;
        this.eletricistasInternos = eletricistasInternos;
        this.folgas = folgas;
        this.ferias = ferias;
    }

    public DemaisInfos(String dataProgramacao, List<Usuario> mecanicosInternos, List<Usuario> eletricistasInternos, List<Usuario> folgas, List<Usuario> ferias) {
        this.id = ultimoIdDemaisInfos + 1;
        ultimoIdDemaisInfos++;
        this.dataProgramacao = dataProgramacao;
        this.mecanicosInternos = mecanicosInternos;
        this.eletricistasInternos = eletricistasInternos;
        this.folgas = folgas;
        this.ferias = ferias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataProgramacao() {
        return dataProgramacao;
    }

    public void setDataProgramacao(String dataProgramacao) {
        this.dataProgramacao = dataProgramacao;
    }

    public List<Usuario> getMecanicosInternos() {
        return mecanicosInternos;
    }

    public void setMecanicosInternos(List<Usuario> mecanicosInternos) {
        this.mecanicosInternos = mecanicosInternos;
    }

    public List<Usuario> getEletricistasInternos() {
        return eletricistasInternos;
    }

    public void setEletricistasInternos(List<Usuario> eletricistasInternos) {
        this.eletricistasInternos = eletricistasInternos;
    }

    public List<Usuario> getFolgas() {
        return folgas;
    }

    public void setFolgas(List<Usuario> folgas) {
        this.folgas = folgas;
    }

    public List<Usuario> getFerias() {
        return ferias;
    }

    public void setFerias(List<Usuario> ferias) {
        this.ferias = ferias;
    }

    @Override
    public String toString() {
        return id+","+dataProgramacao+","+mecanicosInternos+","+eletricistasInternos+","+folgas+","+ferias;
    }
}
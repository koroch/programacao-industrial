/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programacao.model;

import java.util.List;
import static view.Programacao.ultimoIdBloco;

/**
 *
 * @author Koroch
 */
public class Bloco {
    private int Id;
    private String dataProgramacao;
    private String projeto;
    private Cliente cliente;
    private String finalidade;
    private List<Usuario> equipe;
    private Usuario responsavelDoTrabalho;
    private Carro carro;
    private String carretao;
    private String dataDeSaida;
    private String dataDeRetorno;
    private String horarioDeSaida;
    private String horarioDeTrabalhoInicio;
    private String horarioDeTrabalhoFimMeioDia;
    private String horarioDeTrabalhoInicioMeioDia;
    private String horarioDeTrabalhoFim;
    private String almoco;
    private String janta;
    private Hotel hospedagem;

    public Bloco(int Id, String dataProgramacao, String projeto, Cliente cliente, String finalidade, List<Usuario> equipe, Usuario responsavelDoTrabalho, Carro carro, String carretao, String dataDeSaida, String dataDeRetorno, String horarioDeSaida, String horarioDeTrabalhoInicio, String horarioDeTrabalhoFimMeioDia, String horarioDeTrabalhoInicioMeioDia, String horarioDeTrabalhoFim, String almoco, String janta, Hotel hospedagem) {
        this.Id = Id;
        this.dataProgramacao = dataProgramacao;
        this.projeto = projeto;
        this.cliente = cliente;
        this.finalidade = finalidade;
        this.equipe = equipe;
        this.responsavelDoTrabalho = responsavelDoTrabalho;
        this.carro = carro;
        this.carretao = carretao;
        this.dataDeSaida = dataDeSaida;
        this.dataDeRetorno = dataDeRetorno;
        this.horarioDeSaida = horarioDeSaida;
        this.horarioDeTrabalhoInicio = horarioDeTrabalhoInicio;
        this.horarioDeTrabalhoFimMeioDia = horarioDeTrabalhoFimMeioDia;
        this.horarioDeTrabalhoInicioMeioDia = horarioDeTrabalhoInicioMeioDia;
        this.horarioDeTrabalhoFim = horarioDeTrabalhoFim;
        this.almoco = almoco;
        this.janta = janta;
        this.hospedagem = hospedagem;
    }

    public Bloco(String dataProgramacao, String projeto, Cliente cliente, String finalidade, List<Usuario> equipe, Usuario responsavelDoTrabalho, Carro carro, String carretao, String dataDeSaida, String dataDeRetorno, String horarioDeSaida, String horarioDeTrabalhoInicio, String horarioDeTrabalhoFimMeioDia, String horarioDeTrabalhoInicioMeioDia, String horarioDeTrabalhoFim, String almoco, String janta, Hotel hospedagem) {
        this.Id = ultimoIdBloco + 1;
        ultimoIdBloco++;
        this.dataProgramacao = dataProgramacao;
        this.projeto = projeto;
        this.cliente = cliente;
        this.finalidade = finalidade;
        this.equipe = equipe;
        this.responsavelDoTrabalho = responsavelDoTrabalho;
        this.carro = carro;
        this.carretao = carretao;
        this.dataDeSaida = dataDeSaida;
        this.dataDeRetorno = dataDeRetorno;
        this.horarioDeSaida = horarioDeSaida;
        this.horarioDeTrabalhoInicio = horarioDeTrabalhoInicio;
        this.horarioDeTrabalhoFimMeioDia = horarioDeTrabalhoFimMeioDia;
        this.horarioDeTrabalhoInicioMeioDia = horarioDeTrabalhoInicioMeioDia;
        this.horarioDeTrabalhoFim = horarioDeTrabalhoFim;
        this.almoco = almoco;
        this.janta = janta;
        this.hospedagem = hospedagem;
    }

    public Bloco getById(int id, List<Bloco> blocos){
        return blocos.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }
    
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getDataProgramacao() {
        return dataProgramacao;
    }

    public void setDataProgramacao(String dataProgramacao) {
        this.dataProgramacao = dataProgramacao;
    }

    public String getProjeto() {
        return projeto;
    }

    public void setProjeto(String projeto) {
        this.projeto = projeto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Usuario> getEquipe() {
        return equipe;
    }

    public void setEquipe(List<Usuario> equipe) {
        this.equipe = equipe;
    }

    public Usuario getResponsavelDoTrabalho() {
        return responsavelDoTrabalho;
    }

    public void setResponsavelDoTrabalho(Usuario responsavelDoTrabalho) {
        this.responsavelDoTrabalho = responsavelDoTrabalho;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public String getDataDeSaida() {
        return dataDeSaida;
    }

    public void setDataDeSaida(String dataDeSaida) {
        this.dataDeSaida = dataDeSaida;
    }

    public String getDataDeRetorno() {
        return dataDeRetorno;
    }

    public void setDataDeRetorno(String dataDeRetorno) {
        this.dataDeRetorno = dataDeRetorno;
    }

    public String getHorarioDeSaida() {
        return horarioDeSaida;
    }

    public void setHorarioDeSaida(String horarioDeSaida) {
        this.horarioDeSaida = horarioDeSaida;
    }

    public String getHorarioDeTrabalhoInicio() {
        return horarioDeTrabalhoInicio;
    }

    public void setHorarioDeTrabalhoInicio(String horarioDeTrabalhoInicio) {
        this.horarioDeTrabalhoInicio = horarioDeTrabalhoInicio;
    }

    public String getHorarioDeTrabalhoFimMeioDia() {
        return horarioDeTrabalhoFimMeioDia;
    }

    public void setHorarioDeTrabalhoFimMeioDia(String horarioDeTrabalhoFimMeioDia) {
        this.horarioDeTrabalhoFimMeioDia = horarioDeTrabalhoFimMeioDia;
    }

    public String getHorarioDeTrabalhoInicioMeioDia() {
        return horarioDeTrabalhoInicioMeioDia;
    }

    public void setHorarioDeTrabalhoInicioMeioDia(String horarioDeTrabalhoInicioMeioDia) {
        this.horarioDeTrabalhoInicioMeioDia = horarioDeTrabalhoInicioMeioDia;
    }

    public String getHorarioDeTrabalhoFim() {
        return horarioDeTrabalhoFim;
    }

    public void setHorarioDeTrabalhoFim(String horarioDeTrabalhoFim) {
        this.horarioDeTrabalhoFim = horarioDeTrabalhoFim;
    }

    public String getAlmoco() {
        return almoco;
    }

    public void setAlmoco(String almoco) {
        this.almoco = almoco;
    }

    public String getJanta() {
        return janta;
    }

    public void setJanta(String janta) {
        this.janta = janta;
    }

    public Hotel getHospedagem() {
        return hospedagem;
    }

    public void setHospedagem(Hotel hospedagem) {
        this.hospedagem = hospedagem;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade;
    }

    public String getCarretao() {
        return carretao;
    }

    public void setCarretao(String carretao) {
        this.carretao = carretao;
    }
    
    

    @Override
    public String toString() {
        return Id + "|" + dataProgramacao + "|" + projeto + "|" + cliente + "|" + equipe + "|" + responsavelDoTrabalho + "|" + carro + "|" + dataDeSaida + "|" + dataDeRetorno + "|" + horarioDeSaida + "|" + horarioDeTrabalhoInicio + "|" + horarioDeTrabalhoFimMeioDia + "|" + horarioDeTrabalhoInicioMeioDia + "|" + horarioDeTrabalhoFim + "|" + almoco + "|" + janta + "|" + hospedagem;
    }
}

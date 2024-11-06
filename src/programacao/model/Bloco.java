/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programacao.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author Koroch
 */
public class Bloco {
    private int Id;
    private Cliente cliente;
    private List<Usuario> equipe;
    private String projeto;
    private Usuario responsavelDoTrabalho;
    private Carro carro;
    private LocalDate dataDeSaida;
    private LocalDate dataDeRetorno;
    private LocalTime horarioDeSaida;
    private LocalTime horarioDeTrabalhoInicio;
    private LocalTime horarioDeTrabalhoFimMeioDia;
    private LocalTime horarioDeTrabalhoInicioMeioDia;
    private LocalTime horarioDeTrabalhoFim;
    private String almoco;
    private String janta;
    private String hospedagem;

    public Bloco(Cliente cliente, List<Usuario> equipe, String projeto, Usuario responsavelDoTrabalho, Carro carro, LocalDate dataDeSaida, LocalDate dataDeRetorno, LocalTime horarioDeSaida, LocalTime horarioDeTrabalhoInicio, LocalTime horarioDeTrabalhoFimMeioDia, LocalTime horarioDeTrabalhoInicioMeioDia, LocalTime horarioDeTrabalhoFim, String almoco, String janta, String hospedagem) {
        this.cliente = cliente;
        this.equipe = equipe;
        this.projeto = projeto;
        this.responsavelDoTrabalho = responsavelDoTrabalho;
        this.carro = carro;
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

    public Bloco(Cliente cliente, List<Usuario> equipe, String projeto, Usuario responsavelDoTrabalho, Carro carro, LocalDate dataDeSaida, LocalDate dataDeRetorno, LocalTime horarioDeSaida, LocalTime horarioDeTrabalhoInicio, LocalTime horarioDeTrabalhoFimMeioDia, LocalTime horarioDeTrabalhoInicioMeioDia, LocalTime horarioDeTrabalhoFim, String almoco, String hospedagem) {
        this.cliente = cliente;
        this.equipe = equipe;
        this.projeto = projeto;
        this.responsavelDoTrabalho = responsavelDoTrabalho;
        this.carro = carro;
        this.dataDeSaida = dataDeSaida;
        this.dataDeRetorno = dataDeRetorno;
        this.horarioDeSaida = horarioDeSaida;
        this.horarioDeTrabalhoInicio = horarioDeTrabalhoInicio;
        this.horarioDeTrabalhoFimMeioDia = horarioDeTrabalhoFimMeioDia;
        this.horarioDeTrabalhoInicioMeioDia = horarioDeTrabalhoInicioMeioDia;
        this.horarioDeTrabalhoFim = horarioDeTrabalhoFim;
        this.almoco = almoco;
        this.hospedagem = hospedagem;
    }

    public Bloco(Cliente cliente, List<Usuario> equipe, String projeto, Usuario responsavelDoTrabalho, Carro carro, LocalDate dataDeSaida, LocalDate dataDeRetorno, LocalTime horarioDeSaida, LocalTime horarioDeTrabalhoInicio, LocalTime horarioDeTrabalhoFimMeioDia, LocalTime horarioDeTrabalhoInicioMeioDia, LocalTime horarioDeTrabalhoFim, String almoco) {
        this.cliente = cliente;
        this.equipe = equipe;
        this.projeto = projeto;
        this.responsavelDoTrabalho = responsavelDoTrabalho;
        this.carro = carro;
        this.dataDeSaida = dataDeSaida;
        this.dataDeRetorno = dataDeRetorno;
        this.horarioDeSaida = horarioDeSaida;
        this.horarioDeTrabalhoInicio = horarioDeTrabalhoInicio;
        this.horarioDeTrabalhoFimMeioDia = horarioDeTrabalhoFimMeioDia;
        this.horarioDeTrabalhoInicioMeioDia = horarioDeTrabalhoInicioMeioDia;
        this.horarioDeTrabalhoFim = horarioDeTrabalhoFim;
        this.almoco = almoco;
    }

    public Bloco(Cliente cliente, List<Usuario> equipe, String projeto, Usuario responsavelDoTrabalho, Carro carro, LocalDate dataDeSaida, LocalDate dataDeRetorno, LocalTime horarioDeSaida) {
        this.cliente = cliente;
        this.equipe = equipe;
        this.projeto = projeto;
        this.responsavelDoTrabalho = responsavelDoTrabalho;
        this.carro = carro;
        this.dataDeSaida = dataDeSaida;
        this.dataDeRetorno = dataDeRetorno;
        this.horarioDeSaida = horarioDeSaida;
    }

    

    
    
    
}

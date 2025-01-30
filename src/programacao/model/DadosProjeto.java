package programacao.model;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDateTime;

public class DadosProjeto {
    @SerializedName("Carimbo de data/hora")
    LocalDateTime datahora;
    
    @SerializedName("Número do Projeto existente (Se não criou, use a outra planilha para criar primeiro)")
    String projeto;
    
    @SerializedName("Informe a quantidade de horas realizadas para somar nas horas da Engenharia")
    int horasEngenharia;
    
    @SerializedName("Informe a quantidade de horas realizadas para somar nas horas da Execução")
    int horasExecucao;
    
    @SerializedName("Informe o valor de dinheiro gasto para somar nos gastos com Material")
    int gastosComMaterial;
    
    @SerializedName("Informe o total de deslocamentos, em KM, realizado no período")
    int quilometrosDeDeslocamento;
    
    @SerializedName("Informe o valor de dinheiro gasto para somar nos gastos \"Outros\"")
    int gastosOutros;
    
    @SerializedName("Sugestões ou comentários:")
    String sugestoes;
    
    @SerializedName("Endereço de e-mail")
    String email;

    public DadosProjeto(String projeto, int horasEngenharia, int horasExecucao, int quilometrosDeDeslocamento) {
        this.datahora = LocalDateTime.now();
        this.projeto = projeto;
        this.horasEngenharia = horasEngenharia;
        this.horasExecucao = horasExecucao;
        this.gastosComMaterial = 0;
        this.quilometrosDeDeslocamento = quilometrosDeDeslocamento;
        this.gastosOutros = 0;
        this.sugestoes = "Criado Automaticamente";
        this.email = "tiautomationva@gmail.com";
    }

}
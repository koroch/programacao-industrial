/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programacao.model;

/**
 *
 * @author Koroch
 */
public class Historico {
    String placaCarro;
    int kmAtt;
    String dataAtt;
    String observacao;

    public Historico(String placaCarro, int kmAtt, String dataAtt, String observacao) {
        this.placaCarro = placaCarro;
        this.kmAtt = kmAtt;
        this.dataAtt = dataAtt;
        this.observacao = observacao;
    }

    public String getPlacaCarro() {
        return placaCarro;
    }

    public int getKmAtt() {
        return kmAtt;
    }

    public void setKmAtt(int kmAtt) {
        this.kmAtt = kmAtt;
    }

    public String getDataAtt() {
        return dataAtt;
    }

    public void setDataAtt(String dataAtt) {
        this.dataAtt = dataAtt;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return this.placaCarro+"|"+this.kmAtt+"|"+this.dataAtt+"|"+this.observacao;
    }
    
}

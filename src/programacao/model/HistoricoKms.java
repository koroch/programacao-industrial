package programacao.model;

/**
 *
 * @author Koroch
 */
public class HistoricoKms {
    String dataProgramacao;
    String placaCarro;
    double kmPercorrido;
    String numeroProjeto;
    Cliente cliente;
    int quantidadeEquipeEngenharia;
    int quantidadeEquipeExecucao;
    double horasPorPessoa;

    public HistoricoKms(String dataProgramacao, String placaCarro, double kmPercorrido, String numeroProjeto, Cliente cliente, int quantidadeEquipeEngenharia, int quantidadeEquipeExecucao, double horasPorPessoa) {
        this.dataProgramacao = dataProgramacao;
        this.placaCarro = placaCarro;
        this.kmPercorrido = kmPercorrido;
        this.numeroProjeto = numeroProjeto;
        this.cliente = cliente;
        this.quantidadeEquipeEngenharia = quantidadeEquipeEngenharia;
        this.quantidadeEquipeExecucao = quantidadeEquipeExecucao;
        this.horasPorPessoa = horasPorPessoa;
    }
    
    public double getHorasPorPessoa() {
        return horasPorPessoa;
    }

    public void setHorasPorPessoa(double horasPorPessoa) {
        this.horasPorPessoa = horasPorPessoa;
    }
    
    public int getQuantidadeEquipeExecucao() {
        return quantidadeEquipeExecucao;
    }

    public void setQuantidadeEquipeExecucao(int quantidadeEquipe) {
        this.quantidadeEquipeExecucao = quantidadeEquipe;
    }
    
    public int getQuantidadeEquipeEngenharia() {
        return quantidadeEquipeEngenharia;
    }

    public void setQuantidadeEquipeEngenharia(int quantidadeEquipe) {
        this.quantidadeEquipeEngenharia = quantidadeEquipe;
    }
    
    public String getDataProgramacao() {
        return dataProgramacao;
    }

    public void setDataProgramacao(String dataProgramacao) {
        this.dataProgramacao = dataProgramacao;
    }

    public String getPlacaCarro() {
        return placaCarro;
    }

    public void setPlacaCarro(String placaCarro) {
        this.placaCarro = placaCarro;
    }

    public double getKmPercorrido() {
        return kmPercorrido;
    }

    public void setKmPercorrido(double kmPercorrido) {
        this.kmPercorrido = kmPercorrido;
    }

    public String getNumeroProjeto() {
        return numeroProjeto;
    }

    public void setNumeroProjeto(String numeroProjeto) {
        this.numeroProjeto = numeroProjeto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        int idCliente = this.getCliente() != null? this.getCliente().getId() : -1;
        return this.getDataProgramacao()+"|"+this.placaCarro+"|"+this.kmPercorrido+"|"+this.getNumeroProjeto()+"|"+idCliente+"|"+this.quantidadeEquipeEngenharia+"|"+this.quantidadeEquipeExecucao+"|"+this.getHorasPorPessoa();
    }
    
}

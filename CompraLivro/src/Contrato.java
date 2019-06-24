public class Contrato {

    String tipoContrato;
    PropostaContratacao propostaContratacao;
    String nomeFuncionario;

    public Contrato(String tipoContrato, PropostaContratacao propostaContratacao, String nomeFuncionario) {
        this.tipoContrato = tipoContrato;
        this.propostaContratacao = propostaContratacao;
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public PropostaContratacao getPropostaContratacao() {
        return propostaContratacao;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }
}

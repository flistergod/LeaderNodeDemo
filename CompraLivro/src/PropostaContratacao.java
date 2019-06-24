import java.time.LocalDate;

public class PropostaContratacao {

LocalDate data;
String justificacao;
Boolean aceite;


    public PropostaContratacao( LocalDate data, String justificacao) {

        this.data = data;
        this.justificacao = justificacao;
    }




    public LocalDate getData() {
        return data;
    }

    public String getJustificacao() {
        return justificacao;
    }

    public  void setAceite(boolean validacao) {
        aceite=validacao;
    }
}

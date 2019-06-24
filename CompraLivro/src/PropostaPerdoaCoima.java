import java.time.LocalDate;

public class PropostaPerdoaCoima {
    Utilizador utilizador;
    LocalDate localDate;
    String justificacao;
    Boolean aceite;
Livro naoEntregue;


    public PropostaPerdoaCoima(Utilizador utilizador, LocalDate localDate, String justificacao, Livro naoEntregue) {
        this.utilizador = utilizador;
        this.localDate = localDate;
        this.justificacao = justificacao;
        this.naoEntregue = naoEntregue;
    }

    public Utilizador getUtilizador() {
        return utilizador;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public String getJustificacao() {
        return justificacao;
    }

    public Boolean getAceite() {
        return aceite;
    }

    public Livro getNaoEntregue() {
        return naoEntregue;
    }

    public void setAceite(Boolean aceite) {
        this.aceite = aceite;
    }
}

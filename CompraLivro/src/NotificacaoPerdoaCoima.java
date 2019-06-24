import java.time.LocalDate;

public class NotificacaoPerdoaCoima {
    boolean aceite;
    LocalDate data;
    Utilizador utilizador;

    public NotificacaoPerdoaCoima(boolean aceite, LocalDate data, Utilizador utilizador) {
        this.aceite = aceite;
        this.data = data;
        this.utilizador=utilizador;
    }

    public boolean isAceite() {
        return aceite;
    }

    public LocalDate getData() {
        return data;
    }

    public Utilizador getUtilizador() {
        return utilizador;
    }
}



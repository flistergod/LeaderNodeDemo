import java.time.LocalDate;

public class Emprestimo {
    LocalDate data;
    Requisicao requisicao;

    public Emprestimo(LocalDate data, Requisicao requisicao) {
        this.data = data;
        this.requisicao = requisicao;
    }

    public LocalDate getData() {
        return data;
    }

    public Requisicao getRequisicao() {
        return requisicao;
    }
}

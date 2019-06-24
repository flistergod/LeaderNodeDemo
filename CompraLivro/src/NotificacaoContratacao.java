import java.time.LocalDate;

public class NotificacaoContratacao {
    boolean aceite;
    LocalDate data;
    int salario, horasSemanais;

    public NotificacaoContratacao( LocalDate data, int salario, int horasSemanais) {
        this.aceite = true;
        this.data = data;
        this.salario = salario;
        this.horasSemanais = horasSemanais;
    }

    public NotificacaoContratacao(LocalDate data) {
        this.aceite = false;
        this.data = data;
        this.salario = 0;
        this.horasSemanais = 0;
    }

    public boolean isAceite() {
        return aceite;
    }

    public LocalDate getData() {
        return data;
    }

    public int getSalario() {
        return salario;
    }

    public int getHorasSemanais() {
        return horasSemanais;
    }
}

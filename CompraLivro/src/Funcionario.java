public class Funcionario {

    int salario;
    int horasSemanais;
    Contrato contrato;


    public Funcionario(int salario, int horasSemanais, Contrato contrato) {
        this.salario = salario;
        this.horasSemanais = horasSemanais;
        this.contrato= contrato;
    }


    public int getSalario() {
        return salario;
    }

    public int getHorasSemanais() {
        return horasSemanais;
    }

    public Contrato getTipoContrato() {
        return contrato;
    }
}

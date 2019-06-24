public class Livro {

    private String  titulo, editora;
    Classificacao classificacao;

    public Livro(String titulo, String editora) {
        this.titulo = titulo;
        this.editora= editora;



    }

    public String getTitulo() {
        return titulo;
    }



    public String getEditora() {
        return editora;
    }


    public Classificacao getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(Classificacao classificacao) {
        this.classificacao = classificacao;
    }
}

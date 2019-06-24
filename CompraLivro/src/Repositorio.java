import java.util.ArrayList;

public class Repositorio {

    public Repositorio() {}

    ArrayList<Coima> coimas = new ArrayList<Coima>();
    ArrayList<Copia> copias = new ArrayList<Copia>();
    ArrayList<Devolucao> devolucoes = new ArrayList<Devolucao>();
    ArrayList<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
    ArrayList<Encomenda> encomendas = new ArrayList<Encomenda>();
    ArrayList<EntradaNovoLivro> entradasNovoLivro = new ArrayList<EntradaNovoLivro>();
    ArrayList<Livro> livros = new ArrayList<Livro>();
    ArrayList<Notificacao> notificacoes = new ArrayList<Notificacao>();
    ArrayList<PropostaAquisicao> propostasAquisicao = new ArrayList<PropostaAquisicao>();
    ArrayList<Requisicao> requisicoes = new ArrayList<Requisicao>();
    ArrayList<RequisicaoCompra> requisicoesCompra = new ArrayList<RequisicaoCompra>();
    ArrayList<TipoUtilizador> tipoUtilizadores = new ArrayList<TipoUtilizador>();
    ArrayList<Utilizador> utilizadores = new ArrayList<Utilizador>();
    ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();
    ArrayList<PropostaContratacao> propostasContratos = new ArrayList<PropostaContratacao>();
    ArrayList<Contrato> contratos = new ArrayList<Contrato>();
    ArrayList<Classificacao> classificacoes = new ArrayList<Classificacao>();
    ArrayList<PropostaPerdoaCoima> propostaPerdoaCoimas = new ArrayList<PropostaPerdoaCoima>();



    public void adicionaPropostaRequisicao(PropostaAquisicao propostaAquisicao){
        propostasAquisicao.add(propostaAquisicao);
    }

    public void adicionaRequisicaoCompra(RequisicaoCompra requisicaoCompra){
        requisicoesCompra.add(requisicaoCompra);
    }
    public void adicionaEncomenda(Encomenda encomenda){
        encomendas.add(encomenda);
    }

    public void adicionaEntradaNovoLivro(EntradaNovoLivro entradaNovoLivro){
        entradasNovoLivro.add(entradaNovoLivro);
    }


    public void adicionaUtilizador(Utilizador utilizador){
        utilizadores.add(utilizador);
    }

    public void adicionaTipoUtilizador(TipoUtilizador tipoUtilizador){
        tipoUtilizadores.add(tipoUtilizador);
    }

    public void adicionaRequisicao(Requisicao requisicao){
        requisicoes.add(requisicao);
    }

    public void adicionaCopia(Copia copia){
        copias.add(copia);
    }

    public void adicionaLivro(Livro livro){
        livros.add(livro);
    }

    public void adicionaEmprestimo(Emprestimo emprestimo){
        emprestimos.add(emprestimo);
    }

    public void adicionaNotificacao(Notificacao notificacao){
        notificacoes.add(notificacao);
    }

    public void adicionaDevolucao(Devolucao devolucao){
        devolucoes.add(devolucao);
    }

    public void adicionaCoima(Coima coima){
        coimas.add(coima);
    }


    public Emprestimo devolveEmprestimoDaRequisicao(Requisicao r){


        for(Emprestimo item: emprestimos){

            if(item.getRequisicao()==r){
                return item;
            }
        }
        return null;



    }

    public  void  adicionarFuncionario (Funcionario f){

        for(Contrato item: contratos){

            if(item==f.contrato){
                funcionarios.add(f);
            }
        }

    }

    public void adicionarPropostaContratacao (PropostaContratacao pc){propostasContratos.add(pc);}

    public  void  adicionaContrato (Contrato c){

        for(PropostaContratacao item: propostasContratos){

            if(item==c.propostaContratacao && item.aceite==true){
                contratos.add(c);
            }
        }
    }

    public  void  adicionarClassificacao (Classificacao c){ classificacoes.add(c);}

    public void adiconarClassificacaoEmLivro (Classificacao c,Livro l){

        for(Classificacao item: classificacoes){

            if(item==c){

                for(Livro item_livro: livros){

                    if(item_livro==l){

                        l.setClassificacao(c);

                    }
                }

            }
        }

    }

    public  void  adicionaPropostaPedroaCoima(PropostaPerdoaCoima pc){

        for(PropostaPerdoaCoima item: propostaPerdoaCoimas){

            if(item==c.propostaContratacao && item.aceite==true){
                contratos.add(c);
            }
        }

    }

public  void removeCoima (PropostaPerdoaCoima pc){

    for(PropostaPerdoaCoima item: propostasContratos){

        if(item==c.propostaContratacao && item.aceite==true){
            contratos.add(c);
        }
    }



}














}

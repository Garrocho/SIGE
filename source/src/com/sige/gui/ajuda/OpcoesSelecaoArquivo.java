public class OpcoesSelecaoArquivo {
    private Component componentePai;
    private String titulo;
    private String diretorioCorrente;
    private boolean opcaoTodosArquivos;
    private String nomeFiltro;
    private String[] extensao;

    public Component getComponentePai() {
        return componentePai;
    }

    public void setComponentePai(Component componentePai) {
        this.componentePai = componentePai;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDiretorioCorrente() {
        return diretorioCorrente;
    }

    public void setDiretorioCorrente(String diretorioCorrente) {
        this.diretorioCorrente = diretorioCorrente;
    }

    public boolean isOpcaoTodosArquivos() {
        return opcaoTodosArquivos;
    }

    public void setOpcaoTodosArquivos(boolean opcaoTodosArquivos) {
        this.opcaoTodosArquivos = opcaoTodosArquivos;
    }

    public String getNomeFiltro() {
        return nomeFiltro;
    }

    public void setNomeFiltro(String nomeFiltro) {
        this.nomeFiltro = nomeFiltro;
    }

    public String[] getExtensao() {
        return extensao;
    }

    public void setExtensao(String[] extensao) {
        this.extensao = extensao;
    }
}

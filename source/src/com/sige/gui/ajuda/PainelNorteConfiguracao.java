public class PainelNorteConfiguracao {
    private String numero;
    private String nome;
    private String partido;
    private String cargo;
    private TratadorEventosCadastroCandidato tratadorEventos;
    private int numLinhas;
    private int tamMaxNome;
    private int tamMaxDocumento;
    private int tamCampoCargo;

    public PainelNorteConfiguracao(String numero, String nome, String partido, String cargo,
                                   TratadorEventosCadastroCandidato tratadorEventos, int numLinhas,
                                   int tamMaxNome, int tamMaxDocumento, int tamCampoCargo) {
        this.numero = numero;
        this.nome = nome;
        this.partido = partido;
        this.cargo = cargo;
        this.tratadorEventos = tratadorEventos;
        this.numLinhas = numLinhas;
        this.tamMaxNome = tamMaxNome;
        this.tamMaxDocumento = tamMaxDocumento;
        this.tamCampoCargo = tamCampoCargo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public TratadorEventosCadastroCandidato getTratadorEventos() {
        return tratadorEventos;
    }

    public void setTratadorEventos(TratadorEventosCadastroCandidato tratadorEventos) {
        this.tratadorEventos = tratadorEventos;
    }

    public int getNumLinhas() {
        return numLinhas;
    }

    public void setNumLinhas(int numLinhas) {
        this.numLinhas = numLinhas;
    }

    public int getTamMaxNome() {
        return tamMaxNome;
    }

    public void setTamMaxNome(int tamMaxNome) {
        this.tamMaxNome = tamMaxNome;
    }

    public int getTamMaxDocumento() {
        return tamMaxDocumento;
    }

    public void setTamMaxDocumento(int tamMaxDocumento) {
        this.tamMaxDocumento = tamMaxDocumento;
    }

    public int getTamCampoCargo() {
        return tamCampoCargo;
    }

    public void setTamCampoCargo(int tamCampoCargo) {
        this.tamCampoCargo = tamCampoCargo;
    }
}
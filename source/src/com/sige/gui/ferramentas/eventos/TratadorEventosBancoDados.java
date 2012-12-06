package com.sige.gui.ferramentas.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.sige.gui.cargo.DialogoCadastrarCargo;
import com.sige.gui.ferramentas.DialogoBanco;
import com.sige.gui.recursos.DialogoErro;
import com.sige.persistencia.BancoDados;
import com.sige.persistencia.BancoDadosCandidato;
import com.sige.persistencia.BancoDadosCargo;
import com.sige.persistencia.BancoDadosPartido;
import com.sige.persistencia.BancoDadosPesquisa;
import com.sige.persistencia.BancoDadosPesquisaCandidatos;
import com.sige.persistencia.BancoDadosVotacao;
import com.sige.persistencia.BancoDadosVotacaoCandidatos;
import com.sige.persistencia.BancoDadosVotacaoCargos;

/**
 * Esta classe implementa um <code>ActionListener</code>. Esta classe trata os eventos da classe 
 * <code>DialogoBanco</code>.
 *  
 * @author Charles Garrocho
 * 
 * @see DialogoBanco
 */
public class TratadorEventosBancoDados implements ActionListener {

	private DialogoBanco dialogoBanco;
	private BancoDados dataBase;
	private BancoDadosPartido dataBasePartido;
	private BancoDadosCargo dataBaseCargo;
	private BancoDadosCandidato dataBaseCandidato;
	private BancoDadosPesquisa dataBasePesquisa;
	private BancoDadosPesquisaCandidatos dataBasePesquisaCandidato;
	private BancoDadosVotacao dataBaseVotacao;
	private BancoDadosVotacaoCandidatos dataBaseVotacaoCandidatos;
	private BancoDadosVotacaoCargos dataBaseVotacaoCargos;

	/**
	 * Este e o construtor. Ele recebe como argumento o <code>DialogoBanco</code> guarda a referencia dela em uma variavel 
	 * para poder tratar os eventos. O construtor instancia as classes de banco de dados necessarias para poder fazer as 
	 * consultas e insercoes no banco de dados.
	 * 
	 * @param dialogoBanco um <code>DialogoBanco</code>
	 * @see DialogoCadastrarCargo
	 * @see BancoDados
	 * @see BancoDadosPartido
	 * @see BancoDadosCargo
	 * @see BancoDadosCandidato
	 * @see BancoDadosPesquisa
	 * @see BancoDadosPesquisaCandidatos
	 * @see BancoDadosVotacao
	 * @see BancoDadosVotacaoCargos
	 * @see BancoDadosVotacaoCandidatos
	 */
	public TratadorEventosBancoDados(DialogoBanco dialogoBanco) {
		super();
		this.dialogoBanco = dialogoBanco;
		this.dataBase = new BancoDados();
		this.dataBasePartido = new BancoDadosPartido();
		this.dataBaseCargo = new BancoDadosCargo();
		this.dataBaseCandidato = new BancoDadosCandidato();
		this.dataBasePesquisa = new BancoDadosPesquisa();
		this.dataBasePesquisaCandidato = new BancoDadosPesquisaCandidatos();
		this.dataBaseVotacao = new BancoDadosVotacao();
		this.dataBaseVotacaoCargos = new BancoDadosVotacaoCargos();
		this.dataBaseVotacaoCandidatos = new BancoDadosVotacaoCandidatos();
	}


	/**
	 * Este metodo verifica se ha dados no banco de dados. Caso exista ele retorna a quantidade de registro. Caso contrario
	 * ele retorna 0.
	 * 
	 * @return um <code>int</code> com o codigo de verificacao.
	 */
	private int verificaBanco() {
		int banco = 0;
		try {
			dataBasePartido.iniciaConexao();
			banco = dataBasePartido.quantidadePartidos();
			dataBasePartido.fechaConexao();

			dataBaseCargo.iniciaConexao();
			banco += dataBaseCargo.quantidadeCargos();
			dataBaseCargo.fechaConexao();

			dataBaseCandidato.iniciaConexao();
			banco += dataBaseCandidato.quantidadeCandidatos();
			dataBaseCandidato.fechaConexao();

		} catch (Exception e) {
			new DialogoErro(dialogoBanco, "Erro", "Informe o Seguinte Erro ao Analista: " + e.toString());
		}
		return banco;
	}

	/**
	 * Este metodo apaga todos os dados das tabelas do banco de dados.
	 */
	private void limparBanco() {
		try {
			Thread.sleep(5);
			dataBase.iniciaConexao();
			dataBase.apagarBancoDados();

			Thread.sleep(5);
			dataBase.criaNovoBancoDados();

			dataBase.fechaConexao();
			Thread.sleep(5);
		} catch (Exception e) {
		}
	}

	/** Este metodo e implementado por que a classe <code>TratadorEventosBancoDados</code> implementa um ActionListener.
	 *  E por isso ela deve obrigatoriamente implementar os metodos abstratados dessa classe. Este metodo trata os eventos dos
	 *  botoes popular e limpar.
	 */
	public void actionPerformed(ActionEvent evento) {

		// Tratador de Evento do Botao Popular.
		int banco = verificaBanco();
		int op = 0;

		if (evento.getSource() == dialogoBanco.getBotaoPopular()) {

			dialogoBanco.getBotaoLimpar().setEnabled(false);
			dialogoBanco.getBotaoPopular().setEnabled(false);

			// Verifica se existe dados nas tabelas do banco de dados.
			if (banco > 0) 
				op = JOptionPane.showConfirmDialog(dialogoBanco, "O Banco Ja Esta Populado. Gostaria de Limpa-lo e Popula-lo?");

			// Verifica se o usuario confirmou a exclusao dos dados.
			if (op != 1) {
				dialogoBanco.getAreaInformacoes().setText("");
				limparBanco();
				try {
					int i = 1;

					// Populando a Tabela "cargo". Thread.sleep(5);
					Thread.sleep(5);
					dialogoBanco.getAreaInformacoes().append("Populando os Cargos\n");

					Thread.sleep(5);
					dataBaseCargo.iniciaConexao();

					Thread.sleep(5);
					dataBaseCargo.adicionarCargo(i++, "PRESIDENTE", 2);

					Thread.sleep(5);
					dataBaseCargo.adicionarCargo(i++, "GOVERNADOR", 2);

					Thread.sleep(5);
					dataBaseCargo.adicionarCargo(i++, "SENADOR", 3);

					Thread.sleep(5);
					dataBaseCargo.adicionarCargo(i++, "DEPUTADO FEDERAL", 4);

					Thread.sleep(5);
					dataBaseCargo.adicionarCargo(i++, "DEPUTADO ESTADUAL", 5);

					Thread.sleep(5);
					dataBaseCargo.adicionarCargo(i++, "VEREADOR", 5);

					Thread.sleep(5);
					dataBaseCargo.adicionarCargo(i++, "PREFEITO", 2);

					Thread.sleep(5);
					dataBaseCargo.fechaConexao();

					// Populando a Tabela "partido".
					Thread.sleep(5);
					dialogoBanco.getAreaInformacoes().append("Populando os Partidos\n");

					Thread.sleep(5);
					dataBasePartido.iniciaConexao();

					Thread.sleep(5);
					dataBasePartido.adicionarPartido(13, "Partido dos Trabalhadores", "PT");

					Thread.sleep(5);
					dataBasePartido.adicionarPartido(15, "Partido da Social Democracia Brasileira", "PSDB");

					Thread.sleep(5);
					dataBasePartido.adicionarPartido(45, "Partido do Movimento Democrático Brasileiro", "PMDB");

					Thread.sleep(5);
					dataBasePartido.adicionarPartido(43, "Partido Verde", "PV");

					Thread.sleep(5);
					dataBasePartido.adicionarPartido(50, "Partido Socialismo e Liberdade", "PSOL");

					Thread.sleep(5);
					dataBasePartido.adicionarPartido(65, "Partido Comunista do Brasil", "PCdoB");

					Thread.sleep(5);
					dataBasePartido.adicionarPartido(70, "Partido Trabalhista do Brasil", "PTdoB");

					Thread.sleep(5);
					dataBasePartido.fechaConexao();

					// Populando a Tabela "candidato".
					Thread.sleep(5);
					dialogoBanco.getAreaInformacoes().append("Populando os Candidatos\n");

					Thread.sleep(5);
					dataBaseCandidato.iniciaConexao();

					Thread.sleep(5);
					dataBaseCandidato.adicionarCandidato(13, "DILMA ROUSSEF", "PT", "PRESIDENTE", "Recursos//Imagens//Default//Candidatos//DILMA.jpg");

					Thread.sleep(5);
					dataBaseCandidato.adicionarCandidato(45, "JOSE SERRA", "PMDB", "PRESIDENTE", "Recursos//Imagens//Default//Candidatos//JOSE.jpg");

					Thread.sleep(5);
					dataBaseCandidato.adicionarCandidato(15, "AGUINALDO TERRA", "PSDB", "PRESIDENTE", "Recursos//Imagens//Default//Candidatos//AGUINALDO.jpg");

					Thread.sleep(5);
					dataBaseCandidato.adicionarCandidato(13, "ANTONIO ANASTASIA", "PT", "GOVERNADOR", "Recursos//Imagens//Default//Candidatos//ANTONIO.jpg");

					Thread.sleep(5);
					dataBaseCandidato.adicionarCandidato(45, "AECIO NEVES", "PMDB", "GOVERNADOR", "Recursos//Imagens//Default//Candidatos//AECIO.jpg");

					Thread.sleep(5);
					dataBaseCandidato.adicionarCandidato(13, "DANUZA BIAS FORTES", "PT", "PREFEITO", "Recursos//Imagens//Default//Candidatos//DANUZA.jpg");

					Thread.sleep(5);
					dataBaseCandidato.adicionarCandidato(45, "MARTIM ANDRADA", "PMDB", "PREFEITO", "Recursos//Imagens//Default//Candidatos//MARTIM.jpg");

					Thread.sleep(5);
					dataBaseCandidato.adicionarCandidato(13, "FRANK AGUIAR", "PT", "DEPUTADO FEDERAL", "Recursos//Imagens//Default//Candidatos//FRANK.jpg");

					Thread.sleep(5);
					dataBaseCandidato.adicionarCandidato(4555, "ROMARIO", "PMDB", "DEPUTADO FEDERAL", "Recursos//Imagens//Default//Candidatos//ROMARIO.jpg");

					Thread.sleep(5);
					dataBaseCandidato.adicionarCandidato(1555, "TIRIRICA", "PSDB", "DEPUTADO FEDERAL", "Recursos//Imagens//Default//Candidatos//TIRIRICA.jpg");

					Thread.sleep(5);
					dataBaseCandidato.fechaConexao();

					// Populando a Tabela "pesquisa".
					Thread.sleep(5);
					dialogoBanco.getAreaInformacoes().append("Populando as Pesquisas\n");

					Thread.sleep(5);
					dataBasePesquisa.iniciaConexao();

					Thread.sleep(5);
					//dataBasePesquisa.adicionarPesquisa(branco, indeciso, entrevistado, municipio)
					dataBasePesquisa.adicionarPesquisa(1, "2012-01-01", "2012-01-30", "PRESIDENTE", 10, 5, 100, 3);

					Thread.sleep(5);
					dataBasePesquisa.adicionarPesquisa(2, "2012-02-01", "2012-02-29", "PRESIDENTE", 10, 10, 140, 2);

					Thread.sleep(5);
					dataBasePesquisa.adicionarPesquisa(3, "2012-03-01", "2012-03-31", "PRESIDENTE", 14, 16, 200, 4);

					Thread.sleep(5);
					dataBasePesquisa.adicionarPesquisa(4, "2012-01-01", "2012-01-30", "PREFEITO", 10, 5, 100, 1);

					Thread.sleep(5);
					dataBasePesquisa.adicionarPesquisa(5, "2012-02-01", "2012-02-29", "PREFEITO", 5, 2, 150, 1);

					Thread.sleep(5);
					dataBasePesquisa.adicionarPesquisa(6, "2012-03-01", "2012-03-31", "PREFEITO", 14, 26, 290, 1);

					Thread.sleep(5);
					dataBasePesquisa.fechaConexao();

					// Populando a Tabela "PesquisaCandidato".
					Thread.sleep(5);
					dialogoBanco.getAreaInformacoes().append("Populando os Candidatos das Pesquisas\n");

					Thread.sleep(5);
					dataBasePesquisaCandidato.iniciaConexao();

					Thread.sleep(5);
					dataBasePesquisaCandidato.adicionarPesquisaCandidatos(1, "DILMA ROUSSEF", 40);

					Thread.sleep(5);
					dataBasePesquisaCandidato.adicionarPesquisaCandidatos(1, "JOSE SERRA", 30);

					Thread.sleep(5);
					dataBasePesquisaCandidato.adicionarPesquisaCandidatos(1, "AGUINALDO TERRA", 15);

					Thread.sleep(5);
					dataBasePesquisaCandidato.adicionarPesquisaCandidatos(2, "DILMA ROUSSEF", 30);

					Thread.sleep(5);
					dataBasePesquisaCandidato.adicionarPesquisaCandidatos(2, "JOSE SERRA", 50);

					Thread.sleep(5);
					dataBasePesquisaCandidato.adicionarPesquisaCandidatos(2, "AGUINALDO TERRA", 40);

					Thread.sleep(5);
					dataBasePesquisaCandidato.adicionarPesquisaCandidatos(3, "DILMA ROUSSEF", 70);

					Thread.sleep(5);
					dataBasePesquisaCandidato.adicionarPesquisaCandidatos(3, "JOSE SERRA", 55);

					Thread.sleep(5);
					dataBasePesquisaCandidato.adicionarPesquisaCandidatos(3, "AGUINALDO TERRA", 45);

					Thread.sleep(5);
					dataBasePesquisaCandidato.adicionarPesquisaCandidatos(4, "DANUZA BIAS FORTES", 30);

					Thread.sleep(5);
					dataBasePesquisaCandidato.adicionarPesquisaCandidatos(4, "MARTIM ANDRADA", 55);

					Thread.sleep(5);
					dataBasePesquisaCandidato.adicionarPesquisaCandidatos(5, "DANUZA BIAS FORTES", 60);

					Thread.sleep(5);
					dataBasePesquisaCandidato.adicionarPesquisaCandidatos(5, "MARTIM ANDRADA", 83);

					Thread.sleep(5);
					dataBasePesquisaCandidato.adicionarPesquisaCandidatos(6, "DANUZA BIAS FORTES", 140);

					Thread.sleep(5);
					dataBasePesquisaCandidato.adicionarPesquisaCandidatos(6, "MARTIM ANDRADA", 110);

					Thread.sleep(5);
					dataBasePesquisaCandidato.fechaConexao();

					// Populando a Tabela "votacao"
					Thread.sleep(5);
					dialogoBanco.getAreaInformacoes().append("Populando As Votacoes\n");

					Thread.sleep(5);
					dataBaseVotacao.iniciaConexao();

					Thread.sleep(5);
					dataBaseVotacao.adicionarVotacao("2012-01-01", 01, 23);

					Thread.sleep(5);
					dataBaseVotacao.adicionarVotacao("2012-01-02", 01, 23);

					Thread.sleep(5);
					dataBaseVotacao.fechaConexao();

					// Populando a Tabela "votacao_cargos"
					Thread.sleep(5);
					dialogoBanco.getAreaInformacoes().append("Populando Os Cargos das Votacoes\n");

					Thread.sleep(5);
					dataBaseVotacaoCargos.iniciaConexao();

					Thread.sleep(5);
					dataBaseVotacaoCargos.adicionarVotacaoCargos("2012-01-01", "PRESIDENTE", 2);

					Thread.sleep(5);
					dataBaseVotacaoCargos.adicionarVotacaoCargos("2012-01-01", "GOVERNADOR", 2);

					Thread.sleep(5);
					dataBaseVotacaoCargos.adicionarVotacaoCargos("2012-01-01", "DEPUTADO FEDERAL", 4);

					Thread.sleep(5);
					dataBaseVotacaoCargos.adicionarVotacaoCargos("2012-01-02", "PREFEITO", 2);

					Thread.sleep(5);
					dataBaseVotacaoCargos.atualizarEleitores(220, "PRESIDENTE", "2012-01-01");

					Thread.sleep(5);
					dataBaseVotacaoCargos.atualizarEleitores(150, "GOVERNADOR", "2012-01-01");

					Thread.sleep(5);
					dataBaseVotacaoCargos.atualizarEleitores(196, "DEPUTADO FEDERAL", "2012-01-01");

					Thread.sleep(5);
					dataBaseVotacaoCargos.atualizarNulo(10, "PRESIDENTE", "2012-01-01");

					Thread.sleep(5);
					dataBaseVotacaoCargos.atualizarNulo(5, "GOVERNADOR", "2012-01-01");

					Thread.sleep(5);
					dataBaseVotacaoCargos.atualizarNulo(3, "DEPUTADO FEDERAL", "2012-01-01");

					Thread.sleep(5);
					dataBaseVotacaoCargos.atualizarBranco(10, "PRESIDENTE", "2012-01-01");

					Thread.sleep(5);
					dataBaseVotacaoCargos.atualizarBranco(5, "GOVERNADOR", "2012-01-01");

					Thread.sleep(5);
					dataBaseVotacaoCargos.atualizarBranco(3, "DEPUTADO FEDERAL", "2012-01-01");

					Thread.sleep(5);
					dataBaseVotacaoCargos.atualizarEleitores(136, "PREFEITO", "2012-01-02");

					Thread.sleep(5);
					dataBaseVotacaoCargos.atualizarBranco(3, "PREFEITO", "2012-01-02");

					Thread.sleep(5);
					dataBaseVotacaoCargos.atualizarNulo(10, "PREFEITO", "2012-01-02");

					Thread.sleep(5);
					dataBaseVotacaoCargos.atualizarBranco(3, "PREFEITO", "2012-01-02");

					Thread.sleep(5);
					dataBaseVotacaoCargos.fechaConexao();

					// Ppulando a Tabela "votacao_candidatos"
					Thread.sleep(5);
					dialogoBanco.getAreaInformacoes().append("Populando Os Candidatos das Votacoes\n");

					Thread.sleep(5);
					dataBaseVotacaoCandidatos.iniciaConexao();

					Thread.sleep(5);
					dataBaseVotacaoCandidatos.adicionarVotacaoCandidatos("2012-01-01", "PRESIDENTE", "DILMA", 100);

					Thread.sleep(5);
					dataBaseVotacaoCandidatos.adicionarVotacaoCandidatos("2012-01-01", "PRESIDENTE", "JOSE", 70);

					Thread.sleep(5);
					dataBaseVotacaoCandidatos.adicionarVotacaoCandidatos("2012-01-01", "PRESIDENTE", "AGUINALDO", 30);

					Thread.sleep(5);
					dataBaseVotacaoCandidatos.adicionarVotacaoCandidatos("2012-01-01", "GOVERNADOR", "ANTONIO", 70);

					Thread.sleep(5);
					dataBaseVotacaoCandidatos.adicionarVotacaoCandidatos("2012-01-01", "GOVERNADOR", "AECIO", 70);

					Thread.sleep(5);
					dataBaseVotacaoCandidatos.adicionarVotacaoCandidatos("2012-01-01", "DEPUTADO FEDERAL", "TIRIRICA", 70);

					Thread.sleep(5);
					dataBaseVotacaoCandidatos.adicionarVotacaoCandidatos("2012-01-01", "DEPUTADO FEDERAL", "FRANK", 50);

					Thread.sleep(5);
					dataBaseVotacaoCandidatos.adicionarVotacaoCandidatos("2012-01-01", "DEPUTADO FEDERAL", "ROMARIO", 70);

					Thread.sleep(5);
					dataBaseVotacaoCandidatos.adicionarVotacaoCandidatos("2012-01-02", "PREFEITO", "DANUZA", 70);

					Thread.sleep(5);
					dataBaseVotacaoCandidatos.adicionarVotacaoCandidatos("2012-01-02", "PREFEITO", "MARTIM", 50);

					Thread.sleep(5);
					dataBaseVotacaoCandidatos.fechaConexao();

				} catch (Exception e1) {
					new DialogoErro(dialogoBanco, "Erro", "Informe o Seguinte Erro ao Analista: " + e1.toString());
				}
			}
		}

		// Tratador de Evento do Botao Limpar.
		else if (evento.getSource() == dialogoBanco.getBotaoLimpar()) {
			dialogoBanco.getBotaoLimpar().setEnabled(false);
			dialogoBanco.getBotaoPopular().setEnabled(false);
			op = JOptionPane.showConfirmDialog(dialogoBanco, "Confirma a Limpeza do Banco?");

			if (op != 1) {
				dialogoBanco.getAreaInformacoes().append("Apagando os Dados\n");
				limparBanco();
			}
		}
		dialogoBanco.getBotaoLimpar().setEnabled(true);
		dialogoBanco.getBotaoPopular().setEnabled(true);
	}	
}
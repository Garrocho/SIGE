package com.sige.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Esta classe e responsavel pela insercao e atualizacao da tabela "votacao_candidatos".
 *  
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class BancoDadosVotacaoCandidatos extends BancoDados {

	private String SQL;
	private ResultSet resultado;

	/**
	 * Este e o construtor. Ele instancia um novo <code>BancoDados</code>.
	 * 
	 * @see BancoDados
	 */
	public BancoDadosVotacaoCandidatos() {
		super(); 
	}

	/**
	 * Busca o numero de votos de um candidato a partir do nome do candidato e a data da pesquisa.
	 * 
	 * @param candidato um <code>String</code> com o nome do candidato.
	 * @param data um <code>String</code> com a data da votacao.
	 * @return um <code>int</code> com a quantidade de candidatos.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int obterVotosCandidato(String candidato, String data) throws ClassNotFoundException, SQLException {
		SQL = "SELECT votos FROM votacao_candidatos WHERE votacao_candidatos.candidato = '" + candidato + "' AND votacao_candidatos.data = '" + data + "'";
		resultado = executaComando(SQL);
		while (resultado.next() )
			return resultado.getInt("votos");
		return 0;
	}

	/**
	 * Busca a quantidade de candidatos de uma votacao a partir de um cargo.
	 * 
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @return um <code>int</code> com a quantidade de candidatos.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int quantidadeVotacaoCandidatos(String cargo) throws SQLException {
		SQL = "SELECT COUNT(votacao_candidatos.candidato) AS qtde FROM votacao_candidatos WHERE votacao_candidatos.cargo = '" + cargo + "'";
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}

	/**
	 * Busca a quantidade de candidatos a partir de um nome.
	 * 
	 * @param candidato um <code>String</code> com o nome do candidato.
	 * @return um <code>int</code> com a quantidade de candidatos.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int verificaVotacaoCandidato(String candidato) throws SQLException {
		SQL = "SELECT COUNT(candidato) AS qtde FROM votacao_candidatos WHERE votacao_candidatos.candidato = '" + candidato + "'";
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}

	/**
	 * Adiciona um novo candidato a uma votacao.
	 * 
	 * @param data um <code>String</code> com a data da votacao.
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @param candidato um <code>String</code> com o nome do candidato.
	 * @param votos um <code>int</code> com o numero de votos do candidato.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public void adicionarVotacaoCandidatos(String data, String cargo, String candidato, int votos) throws SQLException, ClassNotFoundException {
		SQL = "INSERT INTO votacao_candidatos (data, cargo, candidato, votos) VALUES ('" + data + "', '" + cargo + "', '" + candidato + "', " + votos + ")";
		executaSemRetorno(SQL);
	}

	/**
	 * Atualiza a quantidade de votos de um candidato a partir do nome do candidato.
	 * 
	 * @param candidato um <code>String</code> com o nome do candidato.
	 * @param votos um <code>int</code> com o numero de votos do candidato.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public void atualizarVotacaoCandidatos(String candidato, int votos) throws SQLException, ClassNotFoundException {
		SQL = "UPDATE votacao_candidatos SET votos = " + votos + " WHERE votacao_candidatos.candidato = '" + candidato + "'";
		executaSemRetorno(SQL);
	}

	/**
	 * Busca os candidatos de uma votacao a partir de uma data.
	 * 
	 * @param data um <code>String</code> com a data da votacao.
	 * @return um <code>ResultSet</code> com o resultado da pesquisa.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public ResultSet obterVotacaoCandidatos(String data) throws ClassNotFoundException, SQLException {
		SQL = "SELECT data, cargo, candidato, votos FROM votacao_candidatos WHERE votacao_candidatos.data = '" + data + "'";
		resultado = executaComando(SQL);
		return resultado;
	}

	/**
	 * Busca os candidatos de uma votacao a partir de uma data e um cargo.
	 * 
	 * @param data um <code>String</code> com a data da votacao.
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @return um <code>ResultSet</code> com o resultado da pesquisa.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public ResultSet obterVotacaoCandidatos(String data, String cargo) throws ClassNotFoundException, SQLException {
		SQL = "SELECT data, cargo, candidato, votos FROM votacao_candidatos WHERE votacao_candidatos.data = '" + data +
				"' AND votacao_candidatos.cargo = '" + cargo + "' ORDER BY votos DESC";
		resultado = executaComando(SQL);
		return resultado;
	}
} // classe BancoDadosVotacaoCandidatos.

package com.sige.persistencia;

import java.sql.*;

/**
 * Esta classe e responsavel pela insercao e atualizacao da tabela "votacao".
 *  
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class BancoDadosVotacao extends BancoDados {
	private String SQL;
	private ResultSet resultado;

	/**
	 * Este e o construtor. Ele instancia um novo <code>BancoDados</code>.
	 * 
	 * @see BancoDados
	 */
	public BancoDadosVotacao() {
		super();
	}
	
	/**
	 * Busca a quantidade total de votacoes existentes.
	 * 
	 * @return um <code>int</code> com a quantidade de votacoes.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int quantidadeVotacao() throws SQLException {
		SQL = "SELECT COUNT(*) AS qtde FROM votacao";
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}
	
	/**
	 * Verifica a quantidade de pesquisas existentes a partir de uma data.
	 * 
	 * @param data um <code>String</code> com a data da votacao.
	 * @return um <code>int</code> com a quantidade de votacoes.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int verificaVotacaoPorData(String data) throws SQLException, ClassNotFoundException {
	    SQL = "SELECT COUNT(data) AS qtde FROM votacao WHERE data = '" + data + "'";
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}
	
	/**
	 * Adiciona uma nova votacao a partir dos argumentos passados.
	 * 
	 * @param data um <code>String</code> com a data da votacao.
	 * @param horaInicio um <code>String</code> com a dataInicio da votacao.
	 * @param horaFim um <code>String</code> com a dataFim da votacao.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public void adicionarVotacao(String data, int horaInicio, int horaFim) throws SQLException, ClassNotFoundException {
		SQL = "INSERT INTO votacao (data, hora_inicio, hora_fim) VALUES ('" + data + "', '" + horaInicio + "','" + horaFim + "')";
		executaSemRetorno(SQL);
	}

	/**
	 * Busca uma votacao a partir de uma data.
	 * 
	 * @param data um <code>String</code> com a data da votacao.
	 * @return um <code>ResultSet</code> com o resultado da pesquisa.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public ResultSet obterVotacao(String data) throws ClassNotFoundException, SQLException {
		SQL = "SELECT data, hora_inicio, hora_fim FROM votacao WHERE votacao.data = '" + data + "'";
		resultado = executaComando(SQL);
		return resultado;
	}
} // class Votacao
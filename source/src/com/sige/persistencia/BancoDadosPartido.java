package com.sige.persistencia;

import java.sql.*;

/**
 * Esta classe e responsavel pela insercao e atualizacao da tabela "partido".
 *  
 * @author Charles Garrocho
 */
public class BancoDadosPartido extends BancoDados {
	private String SQL;
	private ResultSet resultado;

	/**
	 * Este e o construtor. Ele instancia um novo <code>BancoDados</code>.
	 * 
	 * @see BancoDados
	 */
	public BancoDadosPartido() {
		super();
	}

	/**
	 * Busca a quantidade total de partidos existentes.
	 * 
	 * @return um <code>int</code> com a quantidade de partidos.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int quantidadePartidos() throws SQLException {
		SQL = "SELECT COUNT(*) AS qtde FROM partido";
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}

	/**
	 * Verifica se existe algum partido a partir de uma sigla.
	 * 
	 * @param sigla um <code>String</code> com a sigla do partido.
	 * @return um <code>int</code> com a quantidade de partidos.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int verificaPartidoSigla(String sigla) throws SQLException, ClassNotFoundException {
		SQL = "SELECT numero FROM partido WHERE partido.sigla = \'" + sigla + "\'";
		resultado = executaComando(SQL);
		while (resultado.next() )
			return resultado.getInt("numero");
		return 0;
	}

	/**
	 * Verifica se existe algum partido a partir de um numero.
	 * 
	 * @param numero um <code>int</code> com o numero do partido.
	 * @return um <code>int</code> com a quantidade de partidos.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int verificaPartido(int numero) throws SQLException, ClassNotFoundException {
		SQL = "SELECT COUNT(numero) AS qtde FROM partido WHERE partido.numero = " + numero;
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}

	/**
	 * Verifica se existe algum partido a partir de um nome.
	 * 
	 * @param nome um <code>String</code> com o nome do partido.
	 * @return um <code>int</code> com a quantidade de partidos.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int verificaPartido(String nome) throws SQLException, ClassNotFoundException {
		SQL = "SELECT COUNT(nome) AS qtde FROM partido WHERE partido.nome LIKE \'" + nome + "\'";
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}

	/**
	 * Busca o numero de um partido a partir de uma sigla.
	 * 
	 * @param sigla um <code>String</code> com a sigla do partido.
	 * @return um <code>int</code> com o numero do partido.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int obterNumeroPartido(String sigla) throws SQLException, ClassNotFoundException {
		SQL = "SELECT numero FROM partido WHERE partido.sigla LIKE \'" + sigla + "\'";
		resultado = executaComando(SQL);
		while (resultado.next() )
			return resultado.getInt("numero");
		return 0;
	}

	/**
	 * Adiciona um partido a partir dos argumentos passados.
	 * 
	 * @param numero um <code>int</code> com o numero do partido.
	 * @param nome um <code>String</code> com o nome do partido.
	 * @param sigla um <code>String</code> com a sigla do partido.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public void adicionarPartido(int numero, String nome, String sigla) throws SQLException, ClassNotFoundException {
		nome = nome.toUpperCase();
		sigla = sigla.toUpperCase();
		SQL = "INSERT INTO partido (numero, nome, sigla) VALUES (" + numero + ",'" + nome + "','" + sigla + "')";
		executaSemRetorno(SQL);
	}

	/**
	 * Busca partidos a partir de um nome.
	 * 
	 * @param nome um <code>String</code> com o nome do partido.
	 * @return um <code>ResultSet</code> com o resultado da pesquisa.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public ResultSet obterPartido(String nome) throws ClassNotFoundException, SQLException {
		SQL = "SELECT numero, nome, sigla FROM partido WHERE partido.nome LIKE '" + nome + "'";
		resultado = executaComando(SQL);
		return resultado;
	}

	/**
	 * Busca partidos a partir de um numero.
	 * 
	 * @param numero um <code>int</code> com o numero do partido.
	 * @return um <code>ResultSet</code> com o resultado da pesquisa.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public ResultSet obterPartido(int numero) throws ClassNotFoundException, SQLException {
		SQL = "SELECT numero, nome, sigla FROM partido WHERE partido.numero = " + numero;
		resultado = executaComando(SQL);
		return resultado;
	}

	/**
	 * Altera um partido a partir dos argumentos, e altera o numero do partido.
	 * 
	 * @param numeroPartido um <code>int</code> com o numero anterior do partido.
	 * @param numero um <code>int</code> com o novo numero do partido.
	 * @param nome um <code>String</code> com o nome do partido.
	 * @param sigla um <code>String</code> com a sigla do partido.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public void alterarPartido(int numeroPartido, int numero, String nome, String sigla) throws SQLException, ClassNotFoundException {
		nome = nome.toUpperCase();
		sigla = sigla.toUpperCase();
		SQL = "UPDATE partido SET numero = " + numero + ", nome = '" + nome + "', sigla = '" + sigla + "' WHERE partido.numero = " + numeroPartido;
		executaSemRetorno(SQL);
	}

	/**
	 * Excluir um partido a partir de um numero.
	 * 
	 * @param numero um <code>int</code> com o novo numero do partido.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public void excluirPartido(int numero) throws SQLException, ClassNotFoundException {
		SQL = "DELETE FROM partido WHERE partido.numero = " + numero;
		executaSemRetorno(SQL);
	}
} // class partido

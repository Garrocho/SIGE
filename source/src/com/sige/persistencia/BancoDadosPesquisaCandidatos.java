package com.sige.persistencia;

import java.sql.*;

/**
 * Esta classe e responsavel pela insercao e atualizacao da tabela "pesquisa_candidatos".
 *  
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class BancoDadosPesquisaCandidatos extends BancoDados {
	private String SQL;
	private ResultSet resultado;
	
	/**
	 * Este e o construtor. Ele instancia um novo <code>BancoDados</code>.
	 * 
	 * @see BancoDados
	 */
	public BancoDadosPesquisaCandidatos() {
		super();
	}
	
	/**
	 * Busca a quantidade de candidatos de uma pesquisa a partir do numero de pesquisa.
	 * 
	 * @param numero_pesquisa um <code>int</code> com o numero da pesquisa.
	 * @return um <code>int</code> com a quantidade de candidatos.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int obterQuantidadeCandidatoNumero(int numero_pesquisa) throws SQLException {
		SQL = "SELECT COUNT(numero_pesquisa) AS qtde FROM pesquisa_candidatos WHERE numero_pesquisa = " + numero_pesquisa;
		resultado = executaComando(SQL);
		while (resultado.next())
			return resultado.getInt("qtde");
		return 0;
	}
	
	/**
	 * Busca a quantidade total de candidatos.
	 * 
	 * @return um <code>int</code> com a quantidade de candidatos.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int quantidadePesquisaCandidatos() throws SQLException {
		SQL = "SELECT COUNT(*) AS qtde FROM pesquisa_candidatos";
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}
	
	/**
	 * Verifica a quantidade de pesquisas existentes a partir de um numero de pesquisa.
	 * 
	 * @param numero_pesquisa um <code>int</code> com o numero da pesquisa.
	 * @return um <code>int</code> com a quantidade de partidos.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int verificaPesquisaCandidatos(int numero_pesquisa) throws SQLException, ClassNotFoundException {
		SQL = "SELECT COUNT(numero_pesquisa) AS qtde FROM pesquisa_candidatos WHERE pesquisa_candidatos.numero_pesquisa = " + numero_pesquisa;
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}
	
	/**
	 * Adiciona um novo candidato de uma pesquisa.
	 * 
	 * @param numero_pesquisa um <code>int</code> com o numero de pesquisa.
	 * @param nome_candidato um <code>String</code> com nome do candidato.
	 * @param numero_votos um <code>int</code> com o numero de votos.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public void adicionarPesquisaCandidatos(int numero_pesquisa, String nome_candidato, int numero_votos) throws SQLException, ClassNotFoundException {
		SQL = "INSERT INTO pesquisa_candidatos (numero_pesquisa, nome_candidato, numero_votos) VALUES (" + numero_pesquisa + ", '" + nome_candidato + "', " + numero_votos + ")";
		executaSemRetorno(SQL);
	}
	
	/**
	 * Busca os candidatos de uma pesquisa a partir do numero de pesquisa.
	 * 
	 * @param numero_pesquisa um <code>int</code> com o numero de pesquisa.
	 * @return um <code>ResultSet</code> com o resultado da pesquisa.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public ResultSet obterPesquisaCandidatos(int numero_pesquisa) throws ClassNotFoundException, SQLException {
		SQL = "SELECT nome_candidato, numero_votos FROM pesquisa_candidatos WHERE pesquisa_candidatos.numero_pesquisa = " + numero_pesquisa;
		resultado = executaComando(SQL);
		return resultado;
	}
	
} // classe BancoDadosPesquisaCandidatos.

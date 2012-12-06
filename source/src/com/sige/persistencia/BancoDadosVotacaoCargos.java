package com.sige.persistencia;

import java.sql.*;

/**
 * Esta classe e responsavel pela insercao e atualizacao da tabela "votacao_cargos".
 *  
 * @author Charles Garrocho
 */
public class BancoDadosVotacaoCargos extends BancoDados {
	private String SQL;
	private ResultSet resultado;

	/**
	 * Este e o construtor. Ele instancia um novo <code>BancoDados</code>.
	 * 
	 * @see BancoDados
	 */
	public BancoDadosVotacaoCargos() {
		super(); 
	}

	/**
	 * Busca a quantidade de votos nulos de um cargo a partir de um cargo e um data.
	 * 
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @param data um <code>String</code> com a data da votacao.
	 * @return um <code>int</code> com a quantidade de cargos.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int obterQuantidadeNulo(String cargo, String data) throws ClassNotFoundException, SQLException {
		SQL = "SELECT nulo FROM votacao_cargos WHERE votacao_cargos.cargo = '" + cargo + "' AND votacao_cargos.data = '" + data + "'";
		resultado = executaComando(SQL);
		while (resultado.next() )
			return resultado.getInt("nulo");
		return 0;
	}

	/**
	 * Atualiza o numero de votos nulos de um determinado cargo de uma votacao.
	 * 
	 * @param nulo um <code>int</code> com o numero de votos nulos.
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @param data um <code>String</code> com a data da votacao.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public void atualizarNulo(int nulo, String cargo, String data) throws SQLException {
		SQL = "UPDATE votacao_cargos SET nulo = " + nulo + " WHERE votacao_cargos.cargo = '" + cargo + "' AND votacao_cargos.data = '" + data + "'";
		executaSemRetorno(SQL);
	}

	/**
	 * Busca o numero de votos brancos de uma votacao a partir do cargo e data.
	 * 
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @param data um <code>String</code> com a data da votacao.
	 * @return um <code>int</code> com o numero de votos brancos.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL
	 */
	public int obterQuantidadeBranco(String cargo, String data) throws ClassNotFoundException, SQLException {
		SQL = "SELECT branco FROM votacao_cargos WHERE votacao_cargos.cargo = '" + cargo + "' AND votacao_cargos.data = '" + data + "'";
		resultado = executaComando(SQL);
		while (resultado.next() )
			return resultado.getInt("branco");
		return 0;
	}

	/**
	 * Atualiza o numero de votos Branco de um cargo de uma votacao.
	 * 
	 * @param branco <code>int</code> com o numero de votos brancos.
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @param data um <code>String</code> com a data da votacao.
	 * @throws SQLException Dispara uma excecao SQL
	 */
	public void atualizarBranco(int branco, String cargo, String data) throws SQLException {
		SQL = "UPDATE votacao_cargos SET branco = " + branco  + " WHERE votacao_cargos.cargo = '" + cargo + "' AND votacao_cargos.data = '" + data + "'";
		executaSemRetorno(SQL);
	}

	/**
	 * Busca a quantidade de eleitores de um cargo de uma votacao.
	 * 
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @param data um <code>String</code> com a data da votacao.
	 * @return um <code>int</code> com o numero de eleitores.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL
	 */
	public int obterQuantidadeEleitores(String cargo, String data) throws ClassNotFoundException, SQLException {
		SQL = "SELECT eleitores FROM votacao_cargos WHERE votacao_cargos.cargo = '" + cargo + "' AND votacao_cargos.data = '" + data + "'";
		resultado = executaComando(SQL);
		while (resultado.next() )
			return resultado.getInt("eleitores");
		return 0;
	}

	/**
	 * Atualiza o numero de eleitores de um cargo de uma votacao.
	 * 
	 * @param eleitores um <code>int</code> com o numero de eleitores.
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @param data um <code>String</code> com a data da votacao.
	 * @throws SQLException Dispara uma excecao SQL
	 */
	public void atualizarEleitores(int eleitores, String cargo, String data) throws SQLException {
		SQL = "UPDATE votacao_cargos SET eleitores = " + eleitores + " WHERE votacao_cargos.cargo = '" + cargo + "' AND votacao_cargos.data = '" + data + "'";
		executaSemRetorno(SQL);
	}

	/**
	 * Busca a quantidade de cargos de uma votacao a partir de uma data.
	 * 
	 * @param data um <code>String</code> com a data da votacao.
	 * @return um <code>int</code> com a quantidade de cargos.
	 * @throws SQLException Dispara uma excecao SQL
	 */
	public int quantidadeVotacaoCargos(String data) throws SQLException {
		SQL = "SELECT COUNT(data) AS qtde FROM votacao_cargos WHERE votacao_cargos.data = '" + data + "'";
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}

	/**
	 * Busca a quantidade de cargos a partir de um cargo.
	 * 
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @return um <code>int</code> com a quantidade de cargos.
	 * @throws SQLException Dispara uma excecao SQL
	 */
	public int verificaVotacaoCargos(String cargo) throws SQLException {
		SQL = "SELECT COUNT(cargo) AS qtde FROM votacao_cargos WHERE votacao_cargos.cargo LIKE '" + cargo + "'";
		resultado = executaComando(SQL);
		while (resultado.next())
			return resultado.getInt("qtde");
		return 0;
	}

	/**
	 * Adiciona um novo cargo a votacao.
	 * 
	 * @param data um <code>String</code> com a data da votacao.
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @param digitos um <code>int</code> com o numero de digitos do cargo.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL
	 */
	public void adicionarVotacaoCargos(String data, String cargo, int digitos) throws SQLException, ClassNotFoundException {
		SQL = "INSERT INTO votacao_cargos (data, cargo, digitos) VALUES ('" + data + "', '" + cargo + "'," + digitos + ")";
		executaSemRetorno(SQL);
	}

	/**
	 * Busca os cargos de uma votacao a partir de uma data.
	 * 
	 * @param data um <code>String</code> com a data da votacao.
	 * @return um <code>ResultSet</code> com o resultado da pesquisa.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL
	 */
	public ResultSet obterVotacaoCargos(String data) throws ClassNotFoundException, SQLException {
		SQL = "SELECT cargo, digitos FROM votacao_cargos WHERE votacao_cargos.data = '" + data + "'";
		resultado = executaComando(SQL);
		return resultado;
	}

	/**
	 * Busca os cargos de uma votacao a partir de um cargo.
	 * 
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @return um <code>ResultSet</code> com o resultado da pesquisa.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL
	 */
	public ResultSet obterVotacaoPorCargo(String cargo) throws ClassNotFoundException, SQLException {
		SQL = "SELECT data, cargo FROM votacao_cargos WHERE votacao_cargos.cargo LIKE '" + cargo + "'";
		resultado = executaComando(SQL);
		return resultado;
	}
} // classe BancoDadosVotacaoCargos.
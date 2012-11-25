package com.sige.persistencia;

import java.sql.*;

/**
 * Esta classe e responsavel pela insercao e atualizacao da tabela "cargo".
 *  
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class BancoDadosCargo extends BancoDados {
	private String SQL;
	private ResultSet resultado;
	
	/**
	 * Este e o construtor. Ele instancia um novo <code>BancoDados</code>.
	 * 
	 * @see BancoDados
	 */
	public BancoDadosCargo() {
		super();
	}
	
	/**
	 * Busca no banco de dados os digitos de um determinado cargo.
	 * 
	 * @param nome um <code>String</code> com o nome do cargo.
	 * @return um <code>int</code> com os digitos do cargo.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int obterDigitosCargo(String nome) throws ClassNotFoundException, SQLException {
		SQL = "SELECT digitos FROM cargo WHERE cargo.nome LIKE '" + nome + "'";
		resultado = executaComando(SQL);
		while (resultado.next() )
			return resultado.getInt("digitos");
		return 0;
	}
	
	/**
	 * Busca a quantidade de cargos existentes.
	 * 
	 * @return um <code>int</code> com a quantidade total de cargos.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int quantidadeCargos() throws SQLException {
		SQL = "SELECT COUNT(*) AS qtde FROM cargo";
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}
	
	/**
	 * Verifica no banco de dados cargos com um numero que e passado como argumento.
	 * 
	 * @param numero um <code>int</code> com o numero do cargo.
	 * @return um <code>int</code> com a quantidade de cargos.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int verificaCargo(int numero) throws SQLException, ClassNotFoundException {
		SQL = "SELECT COUNT(numero) AS qtde FROM cargo WHERE cargo.numero = " + numero;
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}
	
	/**
	 * Verifica no banco de dados cargos com um nome que e passado como argumento.
	 * 
	 * @param nome um <code>String</code> com o nome do cargo.
	 * @return um <code>int</code> com a quantidade de cargos.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int verificaCargo(String nome) throws SQLException, ClassNotFoundException {
		SQL = "SELECT COUNT(nome) AS qtde FROM cargo WHERE cargo.nome LIKE \'" + nome + "\'";
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}

	/**
	 * Adiciona um novo cargo a partir dos argumentos passados.
	 * 
	 * @param numero um <code>int</code> com o numero do cargo.
	 * @param nome um <code>String</code> com o nome do cargo.
	 * @param digitos um <code>int</code> com os digitos do cargo.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public void adicionarCargo(int numero, String nome, int digitos) throws SQLException, ClassNotFoundException {
		nome = nome.toUpperCase();
		SQL = "INSERT INTO cargo (numero, nome, digitos) VALUES (" + numero + ",'" + nome + "'," + digitos + ")";
		executaSemRetorno(SQL);
	}
	
	/**
	 * Pesquisa um cargo a partir de um nome.
	 * 
	 * @param nome um <code>String</code> com o nome do cargo.
	 * @return um <code>ResultSet</code> com o resultado da pesquisa.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public ResultSet obterCargo(String nome) throws ClassNotFoundException, SQLException {
		SQL = "SELECT numero, nome, digitos FROM cargo WHERE cargo.nome LIKE '" + nome + "'";
		resultado = executaComando(SQL);
		return resultado;
	}
	
	/**
	 * Pesquisa um cargo a partir de um numero.
	 * 
	 * @param numero um <code>int</code> com o numero do cargo.
	 * @return um <code>ResultSet</code> com o resultado da pesquisa.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public ResultSet obterCargo(int numero) throws ClassNotFoundException, SQLException {
		SQL = "SELECT numero, nome, digitos FROM cargo WHERE cargo.numero = " + numero;
		resultado = executaComando(SQL);
		return resultado;
	}
	
	/**
	 * Altera um cargo a partir dos argumentos passados.
	 * 
	 * @param numeroCargo um <code>int</code> com o numero do cargo.
	 * @param nome um <code>String</code> com o nome do cargo.
	 * @param digitos um <code>int</code> com os digitos do cargo.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public void alterarCargo(int numeroCargo, String nome, int digitos) throws SQLException, ClassNotFoundException {
		nome = nome.toUpperCase();
		SQL = "UPDATE cargo SET nome = '" + nome + "', digitos = " + digitos + " WHERE cargo.numero = " + numeroCargo;
		executaSemRetorno(SQL);
	}
	
	/**
	 * Exclui um cargo a partir de um numero.
	 * 
	 * @param numero um <code>int</code> com o numero do cargo.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public void excluirCargo(int numero) throws SQLException, ClassNotFoundException {
		SQL = "DELETE FROM cargo WHERE cargo.numero = " + numero;
		executaSemRetorno(SQL);
	}
} // class Cargo

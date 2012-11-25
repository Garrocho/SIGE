package com.sige.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Esta classe e responsavel pela insercao e atualizacao da tabela "candidato".
 *  
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class BancoDadosCandidato extends BancoDados {
	private String SQL;
	private ResultSet resultado;

	/**
	 * Este e o construtor. Ele instancia um novo <code>BancoDados</code>.
	 * 
	 * @see BancoDados
	 */
	public BancoDadosCandidato() {
		super();
	}
	
	/**
	 * @param cargo
	 * @return um <code>int</code> com a quantidade de candidatos.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int obterQuantidadeCandidatoCargo(String cargo) throws ClassNotFoundException, SQLException {
		SQL = "SELECT COUNT(candidato.cargo) AS qtde FROM candidato WHERE candidato.cargo LIKE '" + cargo + "'";
		resultado = executaComando(SQL);
		while (resultado.next() )
			return resultado.getInt("qtde");
		return 0;
	}

	/**
	 * Retorna a quantidade total de candidatos.
	 * 
	 * @return um <code>int</code> com a quantidade de candidatos.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int quantidadeCandidatos() throws SQLException {
		SQL = "SELECT COUNT(*) AS qtde FROM candidato";
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}

	/**
	 * Verifica no banco de dados um candidato com o numero que foi passado como argumetno.
	 * 
	 * @param numero um <code>int</code> com o numero do candidato.
	 * @return um <code>int</code> com a quantidade de candidatos.
	 * @throws SQLException Dispara uma excecao SQL.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe nao encontrada.
	 */
	public int verificaCandidato(int numero) throws SQLException, ClassNotFoundException {
		SQL = "SELECT COUNT(numero) AS qtde FROM candidato WHERE candidato.numero = " + numero;
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}

	/**
	 * Verifica no banco de dados um candidato com o nome que e como argumetno.
	 * 
	 * @param nome um <code>String</code> com o nome do candidato.
	 * @return um <code>int</code> com a quantidade de candidatos.
	 * @throws SQLException Dispara uma excecao SQL.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe nao encontrada.
	 */
	public int verificaCandidato(String nome) throws SQLException, ClassNotFoundException {
		SQL = "SELECT COUNT(nome) AS qtde FROM candidato WHERE nome LIKE \'" + nome + "\'";
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}

	/**
	 * Verifica no banco de dados um candidato com numero e cargo que e passado como argumento.
	 * 
	 * @param numero um <code>int</code> com o numero do candidato.
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @return um <code>int</code> com a quantidade de candidatos.
	 * @throws SQLException Dispara uma excecao SQL.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe nao encontrada.
	 */
	public int verificaCandidatoPorCargo(int numero, String cargo) throws SQLException, ClassNotFoundException {
		SQL = "SELECT COUNT(candidato.numero) AS qtde FROM candidato WHERE candidato.numero = " + numero + " AND candidato.cargo LIKE \'" + cargo + "\'";
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}

	/**
	 * Verifica no banco de dados um candidato com nome e cargo que e passado como argumento.
	 * 
	 * @param nome um <code>String</code> com o nome do candidato.
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @return um <code>int</code> com a quantidade de candidatos.
	 * @throws SQLException Dispara uma excecao SQL.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe nao encontrada.
	 */
	public int verificaCandidatoPorCargo(String nome, String cargo) throws SQLException, ClassNotFoundException {
		SQL = "SELECT COUNT(candidato.nome) AS qtde FROM candidato WHERE candidato.nome LIKE \'" + nome + "\' AND candidato.cargo LIKE \'" + cargo + "\'";
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}

	/**
	 * Verifica no banco de dados um candidato com numero e cargo que e passado como argumento.
	 * 
	 * @param numero um <code>int</code> com o numero do candidato.
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @return um <code>int</code> com a quantidade de candidatos.
	 * @throws SQLException Dispara uma excecao SQL.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe nao encontrada.
	 */
	public int verificaCandidatoPorNumeroCargo(int numero, String cargo) throws SQLException, ClassNotFoundException {
		SQL = "SELECT COUNT(candidato.numero) AS qtde FROM candidato WHERE candidato.numero = " + numero + " AND candidato.numero IN (SELECT candidato.numero FROM candidato WHERE candidato.cargo LIKE \'" + cargo + "\')";
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}

	/**
	 * Adiciona um novo candidato com os dados passados como argumento.
	 * 
	 * @param numero um <code>int</code> com o numero do candidato.
	 * @param nome um <code>String</code> com o nome do cargo.
	 * @param partido um <code>String</code> com o nome do partido.
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @param caminhoFoto um <code>String</code> com o endereco da foto do cargo.
	 * @throws SQLException Dispara uma excecao SQL.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe nao encontrada.
	 */
	public void adicionarCandidato(int numero, String nome, String partido, String cargo, String caminhoFoto) throws SQLException, ClassNotFoundException {
		nome = nome.toUpperCase();
		SQL = "INSERT INTO candidato (numero, nome, partido, cargo, caminhoFoto) VALUES (" + numero + ",'" + nome + "','" + partido + "','" + cargo + "','" + caminhoFoto + "')";
		executaSemRetorno(SQL);
	}

	/**
	 * Busca candidatos a partir do nome fornecido.
	 * 
	 * @param nome um <code>String</code> com o nome do candidato.
	 * @return um <code>ResultSet</code> com o resultado da pesquisa.
	 * @throws SQLException Dispara uma excecao SQL.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe nao encontrada.
	 */
	public ResultSet obterCandidato(String nome) throws ClassNotFoundException, SQLException {
		SQL = "SELECT numero, nome, partido, cargo, caminhoFoto FROM candidato WHERE candidato.nome LIKE '" + nome + "'";
		resultado = executaComando(SQL);
		return resultado;
	}

	/**
	 * Busca candidatos a partir do nome e cargo fornecidos.
	 * 
	 * @param nome um <code>String</code> com o nome do candidato.
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @return um <code>ResultSet</code> com o resultado da pesquisa.
	 * @throws SQLException Dispara uma excecao SQL.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe nao encontrada.
	 */
	public ResultSet obterCandidatoPorCargo(String nome, String cargo) throws ClassNotFoundException, SQLException {
		SQL = "SELECT numero, nome, partido, cargo FROM candidato WHERE candidato.nome LIKE \'" + nome + "\' AND candidato.nome IN (SELECT candidato.nome FROM candidato WHERE candidato.cargo LIKE \'" + cargo + "\')";
		resultado = executaComando(SQL);
		return resultado;
	}

	/**
	 * Busca candidatos a partir do cargo fornecido.
	 * 
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @return um <code>ResultSet</code> com o resultado da pesquisa.
	 * @throws SQLException Dispara uma excecao SQL.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe nao encontrada.
	 */
	public ResultSet obterCandidatoPorCargo(String cargo) throws ClassNotFoundException, SQLException {
		SQL = "SELECT numero, nome, partido, cargo FROM candidato WHERE candidato.cargo LIKE \'" + cargo + "\'";
		resultado = executaComando(SQL);
		return resultado;
	}

	/**
	 * Busca candidatos a partir do numero e cargo fornecidos.
	 * 
	 * @param numero um <code>int</code> com o numero do candidato.
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @return um <code>ResultSet</code> com o resultado da pesquisa.
	 * @throws SQLException Dispara uma excecao SQL.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe nao encontrada.
	 */
	public ResultSet obterCandidatoPorCargo(int numero, String cargo) throws ClassNotFoundException, SQLException {
		SQL = "SELECT numero, nome, partido, cargo, caminhoFoto FROM candidato WHERE candidato.numero = " + numero + " AND candidato.cargo = \'" + cargo + "\'";
		resultado = executaComando(SQL);
		return resultado;
	}

	/**
	 * Busca o endereco da foto do candidato a partir do numero e cargo fornecidos.
	 * 
	 * @param numero um <code>int</code> com o numero do candidato.
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @return um <code>String</code> com o endereco da foto do candidato.
	 * @throws SQLException Dispara uma excecao SQL.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe nao encontrada.
	 */
	public String obterFoto(int numero, String cargo) throws ClassNotFoundException, SQLException {
		SQL = "SELECT caminhoFoto FROM candidato WHERE candidato.numero = " + numero + " AND cargo = '" + cargo + "'";
		resultado = executaComando(SQL);
		return resultado.getString("caminhoFoto");
	}

	/**
	 * Busca um candidato a partir de um numero fornecido.
	 * 
	 * @param numero um <code>int</code> com o numero do candidato.
	 * @return um <code>ResultSet</code> com o resultado da pesquisa.
	 * @throws SQLException Dispara uma excecao SQL.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe nao encontrada.
	 */
	public ResultSet obterCandidato(int numero) throws ClassNotFoundException, SQLException {
		SQL = "SELECT numero, nome, partido, cargo, caminhoFoto FROM candidato WHERE candidato.numero = " + numero;
		resultado = executaComando(SQL);
		return resultado;
	}

	/**
	 * Altera um candidato a partir dos argumentos fornecidos, e altera o numero do candidato.
	 * 
	 * @param numeroCandidato um <code>int</code> com o numero anterior do candidato.
	 * @param numero um <code>int</code> com o numero do candidato.
	 * @param nome um <code>String</code> com o nome do cargo.
	 * @param partido um <code>String</code> com o nome do partido.
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @param caminhoFoto <code>String</code> com o endereco da foto do candidato.
	 * @throws SQLException Dispara uma excecao SQL.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe nao encontrada.
	 */
	public void alterarCandidatoNovoNumero(int numeroCandidato, int numero, String nome, String partido, String cargo, String caminhoFoto) throws SQLException, ClassNotFoundException {
		SQL = "UPDATE candidato SET numero = " + numero + ", nome = '" + nome + "', partido = '" + partido + "', caminhoFoto = '" + caminhoFoto + "' WHERE candidato.numero = " + numeroCandidato + " AND cargo = '" + cargo + "'";
		executaSemRetorno(SQL);
	}

	/**
	 * Altera um candidato a partir dos argumentos fornecidos, mas nao altera o numero do candidato.
	 * 
	 * @param numeroCandidato um <code>int</code> com o numero do candidato.
	 * @param nome um <code>String</code> com o nome do cargo.
	 * @param partido um <code>String</code> com o nome do partido.
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @param caminhoFoto <code>String</code> com o endereco da foto do candidato.
	 * @throws SQLException Dispara uma excecao SQL.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe nao encontrada.
	 */
	public void alterarCandidato(int numeroCandidato, String nome, String partido, String cargo, String caminhoFoto) throws SQLException, ClassNotFoundException {
		SQL = "UPDATE candidato SET nome = '" + nome + "', partido = '" + partido + "', caminhoFoto = '" + caminhoFoto + "' WHERE candidato.numero = " + numeroCandidato + " AND cargo = '" + cargo + "'";
		executaSemRetorno(SQL);
	}

	/**
	 * Altera o candidato a partir dos argumentos fornecidos, e altera o cargo e o numero do candidato.
	 * 
	 * @param cargoAnterior um <code>String</code> com o cargo anterior do candidato.
	 * @param numeroCandidato um <code>int</code> com o numero anterior do candidato.
	 * @param numero um <code>int</code> com o numero do candidato.
	 * @param nome um <code>String</code> com o nome do cargo.
	 * @param partido um <code>String</code> com o nome do partido.
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @param caminhoFoto <code>String</code> com o endereco da foto do candidato.
	 * @throws SQLException Dispara uma excecao SQL.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe nao encontrada.
	 */
	public void alterarCandidatoNovoNumeroCargo(String cargoAnterior, int numeroCandidato, int numero, String nome, String partido, String cargo, String caminhoFoto) throws SQLException, ClassNotFoundException {
		SQL = "UPDATE candidato SET numero = " + numero + ", nome = '" + nome + "', partido = '" + partido + "', cargo = '" + cargo + "', caminhoFoto = '" + caminhoFoto + "' WHERE candidato.numero = " + numeroCandidato + " AND cargo = '" + cargoAnterior + "'";
		executaSemRetorno(SQL);
	}

	/**
	 * Altera o candidato a partir dos argumentos fornecidos, altera o cargo mas nao altera o numero do candidato.
	 * 
	 * @param cargoAnterior um <code>String</code> com o cargo anterior do candidato.
	 * @param numeroCandidato um <code>int</code> com o numero do candidato.
	 * @param nome um <code>String</code> com o nome do cargo.
	 * @param partido um <code>String</code> com o nome do partido.
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @param caminhoFoto <code>String</code> com o endereco da foto do candidato.
	 * @throws SQLException Dispara uma excecao SQL.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe nao encontrada.
	 */
	public void alterarCandidatoCargo(String cargoAnterior, int numeroCandidato, String nome, String partido, String cargo, String caminhoFoto) throws SQLException, ClassNotFoundException {
		SQL = "UPDATE candidato SET nome = '" + nome + "', partido = '" + partido + "', cargo = '" + cargo + "', caminhoFoto = '" + caminhoFoto + "' WHERE candidato.numero = " + numeroCandidato + " AND cargo = '" + cargoAnterior + "'";
		executaSemRetorno(SQL);
	}

	/**
	 * Exclui um candidato a partir do numero e nome passados como argumento.
	 * 
	 * @param numero um <code>int</code> com o numero do candidato.
	 * @param nome um <code>String</code> com o nome do cargo.
	 * @throws SQLException Dispara uma excecao SQL.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe nao encontrada.
	 */
	public void excluirCandidato(int numero, String nome) throws SQLException, ClassNotFoundException {
		SQL = "DELETE FROM candidato WHERE candidato.numero = " + numero + " AND candidato.nome = '" + nome + "'";
		executaSemRetorno(SQL);
	}

} // class BancoDadosCandidato.
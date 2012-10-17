package com.sige.persistencia;

import java.sql.*;

/**
 * Esta classe e responsavel pela insercao e atualizacao da tabela "pesquisa".
 *  
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class BancoDadosPesquisa extends BancoDados {
	private String SQL;
	private ResultSet resultado;

	/**
	 * Este e o construtor. Ele instancia um novo <code>BancoDados</code>.
	 * 
	 * @see BancoDados
	 */
	public BancoDadosPesquisa() {
		super();
	}

	/**
	 * Busca a quantidade total de pesquisas existentes.
	 * 
	 * @return um <code>int</code> com a quantidade de pesquisas.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int quantidadePesquisa() throws SQLException {
		SQL = "SELECT COUNT(*) AS qtde FROM pesquisa";
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}

	/**
	 * Busca a quantidade de pesquisas existentes de acordo com o cargo, data inicio e data fim.
	 * 
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @param dataInicio um <code>String</code> com a data inicio da pesquisa.
	 * @param dataFim um <code>String</code> com a data fim da pesquisa.
	 * @return um <code>int</code> com a quantidade de pesquisas.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int obterQuantidadePesquisas(String cargo, String dataInicio, String dataFim) throws SQLException, ClassNotFoundException {
		SQL = "SELECT COUNT(numero_pesquisa) AS qtde FROM pesquisa WHERE cargo = '" + cargo + "' " +
				"AND data_inicio >= \'" + dataInicio + "\' AND data_fim <= \'" + dataFim + "\'";
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}

	/**
	 * Busca os numeros das pesquisas a partir do cargo, data inicio e data fim.
	 * 
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @param dataInicio um <code>String</code> com a data inicio da pesquisa.
	 * @param dataFim um <code>String</code> com a data fim da pesquisa.
	 * @param quantidade um <code>int</code> com a quantidade de pesquisas.
	 * @return um <code>int[]</code> com os numeros das pesquisas.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int[] obterNumeroPesquisas(String cargo, String dataInicio, String dataFim, int quantidade) throws SQLException, ClassNotFoundException {
		int y = 0;
		int[] pesquisas = new int[quantidade];
		SQL = "SELECT numero_pesquisa FROM pesquisa WHERE cargo = \'" + cargo + "\' AND data_inicio BETWEEN \'" + dataInicio + "\' AND \'" + dataFim + "\' AND data_fim BETWEEN \'" + dataInicio + "\' AND \'" + dataFim + "\'";
		resultado = executaComando(SQL);
		while(resultado.next())
			pesquisas[y++] = resultado.getInt("numero_pesquisa");
		return pesquisas;
	}

	/**
	 * Busca a quantidade de pesquisas existentes de acordo com o cargo, data inicio e data fim.
	 * 
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @param dataInicio um <code>String</code> com a data inicio da pesquisa.
	 * @param dataFim um <code>String</code> com a data fim da pesquisa.
	 * @return um <code>int</code> com a quantidade de pesquisas.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int verificaPesquisaPorData(String cargo, String dataInicio, String dataFim) throws SQLException, ClassNotFoundException {
		SQL = "SELECT COUNT(data_inicio) AS qtde FROM pesquisa WHERE cargo = '" + cargo + "' AND data_inicio BETWEEN \'" + dataInicio + "\' AND \'" + dataFim + "\'" + "' AND data_fim BETWEEN \'" + dataInicio + "\' AND \'" + dataFim + "\'";
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}

	/**
	 * Busca a quantidade de pesquisas existentes de acordo com a data inicio e data fim.
	 * 
	 * @param dataInicio um <code>String</code> com a data inicio da pesquisa.
	 * @param dataFim um <code>String</code> com a data fim da pesquisa.
	 * @return um <code>int</code> com a quantidade de pesquisas.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int verificaPesquisaPorData(String dataInicio, String dataFim) throws SQLException, ClassNotFoundException {
		SQL = "SELECT COUNT(data_inicio) AS qtde FROM pesquisa WHERE data_inicio BETWEEN \'" + dataInicio + "\' AND \'" + dataFim + "\'" + "' AND data_fim BETWEEN \'" + dataInicio + "\' AND \'" + dataFim + "\'";
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}

	/**
	 * Busca as pesquisas existentes de acordo com o cargo, data inicio e data fim.
	 * 
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @param dataInicio um <code>String</code> com a data inicio da pesquisa.
	 * @param dataFim um <code>String</code> com a data fim da pesquisa.
	 * @return um <code>ResultSet</code> com o resultado da pesquisa.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public ResultSet obterPesquisaPorData(String cargo, String dataInicio, String dataFim) throws SQLException, ClassNotFoundException {
		SQL = "SELECT numero_pesquisa, data_inicio, data_fim, cargo, numero_branco, numero_indeciso, numero_entrevistado, numero_municipios FROM pesquisa WHERE cargo = '" + cargo + "' AND data_inicio BETWEEN \'" + dataInicio + "\' AND \'" + dataFim + "\'" + "' AND data_fim BETWEEN \'" + dataInicio + "\' AND \'" + dataFim + "\'";
		resultado = executaComando(SQL);
		return resultado;
	}

	/**
	 * Busca as pesquisas existentes de acordo com a data inicio e data fim.
	 * 
	 * @param dataInicio um <code>String</code> com a data inicio da pesquisa.
	 * @param dataFim um <code>String</code> com a data fim da pesquisa.
	 * @return um <code>ResultSet</code> com o resultado da pesquisa.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public ResultSet obterPesquisaPorData(String dataInicio, String dataFim) throws SQLException, ClassNotFoundException {
		SQL = "SELECT numero_pesquisa, data_inicio, data_fim, cargo, numero_branco, numero_indeciso, numero_entrevistado, numero_municipios FROM pesquisa WHERE data_inicio BETWEEN \'" + dataInicio + "\' AND \'" + dataFim + "\'" + "' AND data_fim BETWEEN \'" + dataInicio + "\' AND \'" + dataFim + "\'";
		resultado = executaComando(SQL);
		return resultado;
	}

	/**
	 * Busca todas as pesquisas existentes.
	 * 
	 * @return um <code>ResultSet</code> com o resultado da pesquisa.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public ResultSet obterTodasPesquisas() throws SQLException, ClassNotFoundException {
		SQL = "SELECT * FROM pesquisa";
		resultado = executaComando(SQL);
		return resultado;
	}

	/**
	 * Busca a quantidade de pesquisas existentes de acordo com o cargo e data inicio.
	 * 
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @param dataInicio um <code>String</code> com a data inicio da pesquisa.
	 * @return um <code>int</code> com a quantidade de pesquisas.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int verificaPesquisaDataInicio(String cargo, String dataInicio) throws SQLException, ClassNotFoundException {
		SQL = "SELECT COUNT(data_inicio) AS qtde FROM pesquisa WHERE cargo = '" + cargo + "' AND data_Inicio >= \'" + dataInicio + "\'";
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}

	/**
	 * Busca a quantidade de pesquisas existentes de acordo com o cargo e data fim.
	 * 
	 * @param cargo um <code>String</code> com o nome do cargo.
	 * @param dataFim um <code>String</code> com a data fim da pesquisa.
	 * @return um <code>int</code> com a quantidade de pesquisas.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public int verificaPesquisaDataFim(String cargo, String dataFim) throws SQLException, ClassNotFoundException {
		SQL = "SELECT COUNT(data_inicio) AS qtde FROM pesquisa WHERE cargo = '" + cargo + "' AND data_fim <= \'" + dataFim + "\'";
		resultado = executaComando(SQL);
		return resultado.getInt("qtde");
	}

	/**
	 * Adiciona uma nova pesquisa a partir dos argumentos.
	 * 
	 * @param numero_pesquisa um <code>int</code> com o numero da pesquisa.
	 * @param dataInicio um <code>String</code> com a data inicio da pesquisa.
	 * @param dataFim um <code>String</code> com a data fim da pesquisa.
	 * @param cargo um <code>String</code> com o nome do cargo da pesquisa.
	 * @param branco um <code>int</code> com o numero de votos brancos.
	 * @param indeciso um <code>int</code> com o numero de votos indecisos.
	 * @param entrevistado um <code>int</code> com o numero de entrevistados.
	 * @param municipio um <code>int</code> com o numero de municipios.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public void adicionarPesquisa(int numero_pesquisa, String dataInicio, String dataFim, String cargo, int branco, int indeciso, int entrevistado, int municipio) throws SQLException, ClassNotFoundException {
		SQL = "INSERT INTO pesquisa (numero_pesquisa, data_inicio, data_fim, cargo, numero_branco, numero_indeciso, numero_entrevistado, numero_municipios) VALUES (" + numero_pesquisa + ",'" + dataInicio + "','" + dataFim + "', '" + cargo + "', " + branco + ", " + indeciso + ", " + entrevistado + ", " + municipio + ")";
		executaSemRetorno(SQL);
	}

	/**
	 * Busca uma pesquisa a partir de um numero.
	 * 
	 * @param numero_pesquisa um <code>int</code> com o numero da pesquisa.
	 * @return um <code>ResultSet</code> com o resultado da pesquisa.
	 * @throws ClassNotFoundException Dispara uma excecao de Classe Nao encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public ResultSet obterPesquisa(int numero_pesquisa) throws ClassNotFoundException, SQLException {
		SQL = "SELECT numero_pesquisa, data_inicio, data_fim, cargo, numero_branco, numero_indeciso, numero_entrevistado, numero_municipios FROM pesquisa WHERE pesquisa.numero_pesquisa = " + numero_pesquisa;
		resultado = executaComando(SQL);
		return resultado;
	}

	/**
	 * Busca uma pesquisa a partir de uma data inicio e fim.
	 * 
	 * @param dataInicio um <code>String</code> com a data inicio da pesquisa.
	 * @param dataFim um <code>String</code> com a data fim da pesquisa.
	 * @return um <code>ResultSet</code> com o resultado da pesquisa.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public ResultSet obterPesquisa(String dataInicio, String dataFim) throws SQLException {
		SQL = "SELECT numero_pesquisa, data_inicio, data_fim, cargo, numero_branco, numero_indeciso, numero_entrevistado, numero_municipios FROM pesquisa WHERE pesquisa.data_inicio >= '" + dataInicio + "' AND pesquisa.data_fim <= '" + dataFim + "'";
		resultado = executaComando(SQL);
		return resultado;
	}
} // classe BancoDadosPesquisa.
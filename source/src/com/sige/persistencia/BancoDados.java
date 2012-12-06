package com.sige.persistencia;
import java.sql.*;

/**
 * Esta classe e responsavel pela insercao e atualizacao do banco de dados.
 *  
 * @author Charles Garrocho
 */
public class BancoDados {
	private Connection conexao;
	private Statement afirmacao;
	private final java.io.File DATABASE;

	/**
	 * Este e o construtor. O construtor cria uma refencia do
	 */
	public BancoDados() {
		super();
		DATABASE = new java.io.File (
				"Recursos"
						+ System.getProperty("file.separator")
						+ "BancoDados"
						+ System.getProperty("file.separator")
						+ "sige.db");
	}

	/**
	 * Apaga todas as tabelas do banco de dados.
	 * 
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public void apagarBancoDados() throws SQLException {
		String SQL = "DROP TABLE cargo";
		afirmacao.executeUpdate(SQL);
		SQL = "DROP TABLE partido";
		afirmacao.executeUpdate(SQL);
		SQL = "DROP TABLE candidato";
		afirmacao.executeUpdate(SQL);
		SQL = "DROP TABLE pesquisa";
		afirmacao.executeUpdate(SQL);
		SQL = "DROP TABLE pesquisa_candidatos";
		afirmacao.executeUpdate(SQL);
		SQL = "DROP TABLE votacao";
		afirmacao.executeUpdate(SQL);
		SQL = "DROP TABLE votacao_cargos";
		afirmacao.executeUpdate(SQL);
		SQL = "DROP TABLE votacao_candidatos";
		afirmacao.executeUpdate(SQL);
	}

	/**
	 * Executa uma atualizacao. Nada e retornado para o usuario.
	 * 
	 * @param SQL um <code>String</code> com a query.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public void executaSemRetorno(String SQL) throws SQLException {
		afirmacao.executeUpdate(SQL);
	}

	/**
	 * Executa uma query. E retornado um <code>ResultSet</code>.
	 * 
	 * @param SQL um <code>String</code> com a query.
	 * @return um <code>ResultSet</code> 	/**
	 * 
	 * @throws SQLException 
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public ResultSet executaComando(String SQL) throws SQLException {
		return afirmacao.executeQuery(SQL);
	}

	/**
	 * Inicia uma conexao com o banco de dados.
	 * 
	 * @throws ClassNotFoundException Dispara uma excecao Classe Nao Encontrada.
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public void iniciaConexao() throws ClassNotFoundException, SQLException {

		Class.forName("org.sqlite.JDBC");
		conexao = DriverManager.getConnection("jdbc:sqlite:" + DATABASE.getPath());

		// Utilizamos o metodo createStatement de conexao para criar o Statement.
		afirmacao = conexao.createStatement();
	}

	/**
	 * Fecha uma conexao com o banco de dados.
	 * 
	 * @throws SQLException Dispara uma excecao SQL.
	 */
	public void fechaConexao() throws SQLException {
		conexao.close();
	}

	/**
	 * Verifica se o banco de dados existe. Caso nao exista, o programa roda o metodo <code>criaNovoBancoDados</code>
	 * para criar um arquivo de banco de dados.
	 * 
	 * @throws Exception Dispara uma excecao.
	 * 
	 * @see BancoDados#criaNovoBancoDados()
	 */
	public void verificaBancoDados() throws Exception {
		if (!DATABASE.exists()) {
			criaNovoBancoDados();
		}
	}

	/**
	 * Cria um novo banco de dados com todas as tabelas necessarias para a execucao do programa.
	 * 
	 * @throws Exception Dispara uma excecao.
	 */
	public void criaNovoBancoDados() throws Exception {

		try {
			DATABASE.getParentFile().mkdirs(); //Cria os diretorios pai do arquivo (caso nao existam)
			DATABASE.createNewFile();// Cria o arquivo do banco
			if (!DATABASE.exists()) { //Caso o arquivo ainda nao exista, apos os comandos acima, dispara excecao.
				throw new Exception("Erro ao gravar o arquivo de banco de dados.");
			}

			iniciaConexao();

			// Criacao da Tabela cargo
			afirmacao.execute(
					"CREATE TABLE IF NOT EXISTS cargo ("
							+ "numero numeric NOT NULL, "
							+ "nome character varying(30), "
							+ "digitos numeric NOT NULL, "
							+ "CONSTRAINT cargo_pkey PRIMARY KEY (numero)"
							+ ")");

			// Criacao da Tabela partido
			afirmacao.execute(
					"CREATE TABLE IF NOT EXISTS partido ("
							+ "numero numeric NOT NULL, "
							+ "nome character varying(30), "
							+ "sigla character varying(10),"
							+ "CONSTRAINT partido_pkey PRIMARY KEY (numero)"
							+ ")");

			// Criacao da Tabela candidato
			afirmacao.execute(
					"CREATE TABLE IF NOT EXISTS candidato ("
							+ "numero numeric NOT NULL, "
							+ "nome character varying(30), "
							+ "partido numeric NOT NULL, "
							+ "cargo numeric NOT NULL, "
							+ "caminhoFoto character varying(100), "
							+ "CONSTRAINT candidato_pkey PRIMARY KEY (numero,cargo),"
							+ "CONSTRAINT candidato_fkey FOREIGN KEY (partido) REFERENCES partido(numero) ON DELETE CASCADE,"
							+ "CONSTRAINT candidato_fkey FOREIGN KEY (cargo) REFERENCES cargo(numero) ON DELETE CASCADE"
							+ ")");

			// Criacao da Tabela pesquisa
			afirmacao.execute(
					"CREATE TABLE IF NOT EXISTS pesquisa ("
							+ "numero_pesquisa numeric NOT NULL, "
							+ "data_inicio DATE NOT NULL, "
							+ "data_fim DATE NOT NULL, "
							+ "cargo numeric NOT NULL, "
							+ "numero_branco numeric NOT NULL, "
							+ "numero_indeciso numeric NOT NULL, "
							+ "numero_entrevistado numeric NOT NULL, "
							+ "numero_municipios numeric NOT NULL, "
							+ "CONSTRAINT pesquisa_pkey PRIMARY KEY (numero_pesquisa)"
							+ ")");

			// Criacao da Tabela pesquisa_candidatos
			afirmacao.execute(
					"CREATE TABLE IF NOT EXISTS pesquisa_candidatos ("
							+ "numero_pesquisa numeric NOT NULL, "
							+ "nome_candidato character varying(100) NOT NULL, "
							+ "numero_votos numeric NOT NULL, "
							+ "CONSTRAINT candidato_fkey FOREIGN KEY (numero_pesquisa) REFERENCES pesquisa(numero_pesquisa) ON DELETE CASCADE"
							+ ")");

			// Criacao da Tabela votacao
			afirmacao.execute(
					"CREATE TABLE IF NOT EXISTS votacao ("
							+ "data DATE NOT NULL, "
							+ "hora_inicio numeric NOT NULL, "
							+ "hora_fim numeric NOT NULL, "
							+ "CONSTRAINT votacao_pkey PRIMARY KEY (data)"
							+ ")");

			// Criacao da Tabela votacao_cargos
			afirmacao.execute(
					"CREATE TABLE IF NOT EXISTS votacao_cargos ("
							+ "data DATE NOT NULL, "
							+ "cargo numeric NOT NULL, "
							+ "digitos numeric NOT NULL, "
							+ "branco numeric DEFAULT 0, "
							+ "nulo numericic DEFAULT 0, "
							+ "eleitores numericic DEFAULT 0, "
							+ "CONSTRAINT votacao_cargos_fkey FOREIGN KEY (cargo) REFERENCES cargo(numero) ON DELETE CASCADE, "
							+ "CONSTRAINT votacao_cargos_fkey FOREIGN KEY (data) REFERENCES votacao(data) ON DELETE CASCADE"
							+ ")");

			// Criacao da Tabela votacao_candidatos
			afirmacao.execute(
					"CREATE TABLE IF NOT EXISTS votacao_candidatos ("
							+ "data DATE NOT NULL, "
							+ "cargo numeric NOT NULL, "
							+ "candidato numeric NOT NULL, "
							+ "votos numeric numeric DEFAULT 0, "
							+ "CONSTRAINT votacao_candidatos_fkey FOREIGN KEY (data) REFERENCES votacao(data) ON DELETE CASCADE, "
							+ "CONSTRAINT votacao_candidatos_fkey FOREIGN KEY (cargo) REFERENCES votacao_cargos(cargo) ON DELETE CASCADE, "
							+ "CONSTRAINT votacao_candidatos_fkey FOREIGN KEY (candidato) REFERENCES candidato(numero) ON DELETE CASCADE"
							+ ")");

		} catch (Exception ex) {
			throw new Exception("Erro na criacao do banco de dados\n" + ex.getMessage());
		}
		conexao.close();
	}	 
}
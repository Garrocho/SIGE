package com.sige.graficos;

import static com.sige.recursos.Recurso.descobreMes;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.sige.persistencia.BancoDadosPesquisa;
import com.sige.persistencia.BancoDadosPesquisaCandidatos;

/**
 * Esta classe cria uma interface grafica com um grafico de linha.
 * 
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class GraficoLinha extends JDialog {

	private static final long serialVersionUID = 1L;
	private BancoDadosPesquisa dataBasePesquisa;
	private BancoDadosPesquisaCandidatos dataBasePesquisaCandidato;
	private ResultSet resultado;
	private int[] pesquisas;
	private String[] dataPesquisas;
	private int[] quantidadeMunicipios;
	private int[] quantidadeEntrevistados;
	private DefaultCategoryDataset dataSet;

	/**
	 * Este e o construtor. Ele constroi a interface grafica do grafico de linha.
	 * 
	 * @param cargo <code>String</code> Cargo da pesquisa eleitoral.
	 * @param dataInicio <code>String</code> Data Inicio da pesquisa eleitoral.
	 * @param dataFim <code>String</code> Data Fim da pesquisa eleitoral.
	 */
	public GraficoLinha(String cargo, String dataInicio, String dataFim) {

		super();
		setTitle("Pesquisa Eleitoral");

		dataBasePesquisaCandidato = new BancoDadosPesquisaCandidatos();
		dataBasePesquisa = new BancoDadosPesquisa();

		obterDadosPesquisas(cargo, dataInicio, dataFim);
		criaConjuntoDados();

		JPanel chartPanel = new ChartPanel(criaGraficoLinha("Pesquisa Eleitoral Para " + cargo, dataSet));
		add(chartPanel, BorderLayout.CENTER);
		
		JPanel painelDados = new JPanel(new GridLayout(3,quantidadeMunicipios.length));
		painelDados.setBackground(Color.WHITE);
		
		JLabel labelDados;
		
		for (int x = 0; x < quantidadeMunicipios.length; x++) {
			labelDados = new JLabel("                  " + descobreMes(dataPesquisas[x]));
			labelDados.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
			painelDados.add(labelDados);
		}
		
		for (int x = 0; x < quantidadeMunicipios.length; x++) {
			labelDados = new JLabel("                  " + "Municipios: " + quantidadeMunicipios[x]);
			labelDados.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
			 painelDados.add(labelDados);
		}
		
		for (int x = 0; x < quantidadeMunicipios.length; x++) {
			labelDados = new JLabel("                  " + "Entrevistados: " + quantidadeEntrevistados[x]);
			labelDados.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
			painelDados.add(labelDados);
		}
		
		add(painelDados, BorderLayout.SOUTH);

		// Define as propriedades do dialogo.
		pack();
		setIconImage(Toolkit.getDefaultToolkit().getImage("Recursos//Imagens//Default//icone.png"));
		setLocationRelativeTo(null);
		setModal(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	/**
	 * Este metodo cria o grafico de linha e defini as propriedades necessarias para a visualizacao do grafico.
	 * 
	 * @param titulo <code>String</code> Titulo para o grafico de Barra.
	 * @param dataset <code>CategoryDataset</code> Conjunto de dados que sera adicionado ao grafico.
	 * @return Um grafico de Linha customizado.
	 */
	private JFreeChart criaGraficoLinha(String titulo, CategoryDataset dataset) {

		JFreeChart grafico = ChartFactory.createLineChart(
				titulo,						// Titulo do grafico.
				null,						// Titulo do Label do Eixo de Tickets.
				"PORCENTAGEM", 				// Titulo do Label Porcentagem.
				dataset, 					// Dados da Barra.
				PlotOrientation.VERTICAL,	// Orientacao da Barra.
				true,						// Defini a Legenda como visivel.
				true, 
				false);

		// OBTENDO A REFERENCIA DO PLOT(enredo) PARA AS CUSTOMIZACOES DO GRAFICO.
		CategoryPlot plot = (CategoryPlot)grafico.getPlot();

		// POSICIONA AS CATEGORIAS NO TOPO DO GRAFICO ( jan / fev / etc ).
		plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);

		// MODIFICA A FONTE DAS CATEGORIAS.
		CategoryAxis categoryAxis = plot.getDomainAxis();
		categoryAxis.setTickLabelFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));

		// OBTENDO A REFERENCIA DOS NUMEROS DO EIXO.
		NumberAxis numberAxis = (NumberAxis)plot.getRangeAxis();
		numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// DEFININDO UM ESPACO NA MARGEM.
		numberAxis.setUpperMargin(0.15);
		numberAxis.setLowerMargin(0.10);
		
		// CUSTOMIZANDO A FONTE DO TITULO DO LABEL DO EIXO.
		numberAxis.setLabelFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

		// CUSTOMIZACAO DAS LINHAS DO GRAFICO.
		LineAndShapeRenderer renderer = new LineAndShapeRenderer();
		
		// DEFININDO O LABEL DO ITEM COMO A PORCENTAGEM, E COMO DECIMAL 0 SEM VIRGULA.
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", new DecimalFormat("0")));
		renderer.setBaseItemLabelsVisible(true);
		renderer.setBaseItemLabelFont(new Font(Font.SERIF, Font.BOLD, 17));

		// CUSTOMIZANDO A LINHA.
		renderer.setBaseStroke(new BasicStroke(6));
		
		// CUSTOMIZANDO A BOLINHA.
		renderer.setBaseShape(new Ellipse2D.Double(-5.0, -5.0, 10.0, 10.0));

		// DEFININDO A CUSTOMIZACAO DE TODOS ITENS.
		renderer.setAutoPopulateSeriesShape(false);
		renderer.setAutoPopulateSeriesStroke(false);

		plot.setRenderer(renderer);
		return grafico;
	}
	
	/**
	 * Este metodo busca no banco de dados as pesquisas eleitorais entre a data inicio e fim fornecida, e guarda os dados dessas
	 * pesquisas para criar o conjunto de dados posteriormente para adicionar ao grafico de linha.
	 * 
	 * @param cargo <code>String</code> Cargo da pesquisa eleitoral.
	 * @param dataInicio <code>String</code> Data Inicio da pesquisa eleitoral.
	 * @param dataFim <code>String</code> Data Fim da pesquisa eleitoral.
	 */
	private void obterDadosPesquisas(String cargo, String dataInicio, String dataFim) {
		int quantidade;

		/* OBTEM A QUANTIDADE DE PESQUISAS E CRIA OS VETORES DE "DATA DE PESQUISA", "QUANTIDADE DE ELEITORES" E "QUANTIDADE,
		 * DE MUNICIPIOS" DE ACORDO COM A QUANTIDADE DE PESQUISAS.  */
		try {
			dataBasePesquisa.iniciaConexao();
			quantidade = dataBasePesquisa.obterQuantidadePesquisas(cargo, dataInicio, dataFim);
			pesquisas = dataBasePesquisa.obterNumeroPesquisas(cargo, dataInicio, dataFim, quantidade);
			dataBasePesquisa.fechaConexao();

			dataPesquisas = new String[quantidade];
			quantidadeEntrevistados = new int[quantidade];
			quantidadeMunicipios = new int[quantidade];
			for (int x = 0; x < quantidade; x++) {
				dataBasePesquisa.iniciaConexao();
				resultado = dataBasePesquisa.obterPesquisa(pesquisas[x]);
				
				// ADICIONA AOS VETORES OS VALORES DAS PESQUISAS.
				while(resultado.next()) {
					dataPesquisas[x] = resultado.getString("data_inicio");
					quantidadeEntrevistados[x] = resultado.getInt("numero_entrevistado");
					quantidadeMunicipios[x] = resultado.getInt("numero_municipios");
				}
				dataBasePesquisa.fechaConexao();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este metodo busca no banco de dados os candidatos e seus respectivos dados. Calcula a porcentagem de votos e adiciona
	 * em uma variavel <code>DefaultCategoryDataset</code>, para ser utilizada na criacao do grafico de linha.
	 */
	private void criaConjuntoDados() {
		double porcentagem;
		dataSet = new DefaultCategoryDataset();

		// CRIA O CONJUNTO DE DADOS UTILIZANDO OS DADOS DOS CANDIDATOS.
		for (int x = 0; x < pesquisas.length; x++) {
			try {
				porcentagem = 100.0 / quantidadeEntrevistados[x];
				dataBasePesquisaCandidato.iniciaConexao();
				resultado = dataBasePesquisaCandidato.obterPesquisaCandidatos(pesquisas[x]);
				while (resultado.next()) {
					dataSet.addValue( ( resultado.getInt("numero_votos") * porcentagem ), resultado.getString("nome_candidato"), descobreMes(dataPesquisas[x]));
				}
				dataBasePesquisaCandidato.fechaConexao();

				dataBasePesquisa.iniciaConexao();
				resultado = dataBasePesquisa.obterPesquisa(pesquisas[x]);
				while(resultado.next()) {
					dataSet.addValue( ( resultado.getInt("numero_branco") * porcentagem ), "Branco/Nulo", descobreMes(dataPesquisas[x]));
					dataSet.addValue( ( resultado.getInt("numero_indeciso") * porcentagem ), "Indecisos", descobreMes(dataPesquisas[x]));
				}
				dataBasePesquisa.fechaConexao();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
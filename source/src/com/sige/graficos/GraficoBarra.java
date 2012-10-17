package com.sige.graficos;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

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
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import com.sige.persistencia.BancoDadosVotacaoCandidatos;
import com.sige.persistencia.BancoDadosVotacaoCargos;

/**
 * Esta classe cria uma interface grafica com um grafico de barra horizontal.
 * 
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class GraficoBarra extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private DefaultCategoryDataset dataSet;
	private String data, cargo;
	private int qtdeEleitores;
	private int qtdeCandidatos;
	private BancoDadosVotacaoCandidatos dataBaseVotacaoCandidato;
	private BancoDadosVotacaoCargos dataBancoDadosVotacaoCargos;
	private ResultSet resultado;

	/**
	 * Este e o construtor. Ele constroi a interface grafica do grafico de barra.
	 * 
	 * @param data <code>String</code> Data da apuracao eleitoral.
	 * @param cargo <code>String</code> Cargo da apuracao eleitoral.
	 * @param qtdeEleitores <code>int</code> Quantidade de eleitores do cargo.
	 */
	public GraficoBarra(String data, String cargo, int qtdeEleitores) {
		
		super();
		setTitle("Apuracao Eleitoral");
		
		this.data = data;
		this.cargo = cargo;
		this.qtdeEleitores = qtdeEleitores;
		this.dataSet = new DefaultCategoryDataset();
		this.dataBaseVotacaoCandidato = new BancoDadosVotacaoCandidatos();
		this.dataBancoDadosVotacaoCargos = new BancoDadosVotacaoCargos();
		this.qtdeCandidatos = quantidadeCandidatos();
		
		criaConjuntoDados();
		JFreeChart grafico = criaGraficoBarra(dataSet, "Apuracao Eleitoral de " + cargo);

		ChartPanel painelGrafico = new ChartPanel(grafico);
		painelGrafico.setPreferredSize(new Dimension( 550, qtdeCandidatos * 45));
		add(painelGrafico);
		
		JPanel painelDados = new JPanel();
		painelDados.setBackground(Color.WHITE);
		
		JLabel labelDados = new JLabel("Numero de eleitores: " + qtdeEleitores);
		labelDados.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		
	    painelDados.add(labelDados); 
		
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
	 * Este metodo cria o grafico de barra e defini as propriedades necessarias para a visualizacao do grafico.
	 * 
	 * @param dados Conjunto de dados que sera adicionado ao grafico.
	 * @param titulo Titulo para o grafico de Barra. 
	 * @return Um grafico de Barra customizado.
	 */
	private JFreeChart criaGraficoBarra(DefaultCategoryDataset dados, String titulo) {

		JFreeChart grafico = ChartFactory.createBarChart(
				titulo,                       // Titulo do grafico.
				"CANDIDATOS",                 // Titulo do Label do Eixo de Tickets.
				"PORCENTAGEM",                // Titulo do Label Porcentagem.
				dados,                        // Dados da Barra.
				PlotOrientation.HORIZONTAL,   // Orientacao da Barra.
				true,                         // Defini a Legenda como visivel.
				false,
				false
				);
		
		// OBTENDO A REFERENCIA DO PLOT(enredo) PARA AS CUSTOMIZACOES DO GRAFICO.
		CategoryPlot plot = grafico.getCategoryPlot();
		
		// DEFININDO A LOCALIZACAO DA PORCENTAGEM EM BAIXO DO CHART.
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		
		// OBTENDO A REFERENCIA DO DOMINIO DO EIXO.
		CategoryAxis domainAxis = plot.getDomainAxis();
		
		// CUSTOMIZANDO A FONTE DO TITULO DO LABEL DE EIXO DE TICKETS.
		domainAxis.setLabelFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		
		// DEFININDO A VIZUALIZACAO FALSA PARA OS TICKETS.
		domainAxis.setTickLabelsVisible(false);
		
		// CUSTOMIZACAO DA BARRA.
		BarRenderer barRenderer = (BarRenderer)plot.getRenderer();
		barRenderer.setBaseItemLabelsVisible(true);
		barRenderer.setDrawBarOutline(false);
		
		
		// ADICIONANDO A QUANTIDADE DE VOTOS NO FINAL DA BARRA DE CADA CANDIDATO.
		int x = 0;
		try {
			dataBaseVotacaoCandidato.iniciaConexao();
			resultado = dataBaseVotacaoCandidato.obterVotacaoCandidatos(data, this.cargo);
			while(resultado.next()) {
				barRenderer.setSeriesItemLabelFont(x, new Font(Font.SANS_SERIF, Font.BOLD, 14));
				barRenderer.setSeriesItemLabelGenerator(x++, new StandardCategoryItemLabelGenerator("{2}" + "%  " + resultado.getInt("votos") + " votos", new DecimalFormat("0.00"), NumberFormat.getPercentInstance(new Locale("pt", "BR"))));
			}
			dataBaseVotacaoCandidato.fechaConexao();
			
			dataBancoDadosVotacaoCargos.iniciaConexao();
			barRenderer.setSeriesItemLabelFont(x, new Font(Font.SANS_SERIF, Font.BOLD, 14));
			barRenderer.setSeriesItemLabelGenerator(x++, new StandardCategoryItemLabelGenerator("{2}" + "%  " + dataBancoDadosVotacaoCargos.obterQuantidadeBranco(this.cargo, data) + " votos", new DecimalFormat("0.00"), NumberFormat.getPercentInstance(new Locale("pt", "BR"))));
			barRenderer.setSeriesItemLabelFont(x, new Font(Font.SANS_SERIF, Font.BOLD, 14));
			barRenderer.setSeriesItemLabelGenerator(x, new StandardCategoryItemLabelGenerator("{2}" + "%  " + dataBancoDadosVotacaoCargos.obterQuantidadeNulo(this.cargo, data) + " votos", new DecimalFormat("0.00"), NumberFormat.getPercentInstance(new Locale("pt", "BR"))));
			dataBancoDadosVotacaoCargos.fechaConexao();
			
		} catch (Exception e) {
			showMessageDialog(null, "Informe o Seguinte Erro ao Analista: " + e.toString(), "Atencao", INFORMATION_MESSAGE);
		}
		
		// OBTENDO A REFERENCIA DOS NUMEROS DO EIXO.
		NumberAxis numberAxis = (NumberAxis)plot.getRangeAxis();
		
		// DEFININDO UM ESPACO NA MARGEM.
		numberAxis.setUpperMargin(0.25);
		numberAxis.setLowerMargin(0.25);
		
		// DEFININDO OS NUMEROS DE 0 ATE 100.
		numberAxis.setRange(0, 100);
		
		// CUSTOMIZANDO A FONTE DO TITULO DO LABEL DO EIXO.
		numberAxis.setLabelFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		
        plot.setRenderer(barRenderer);
		return grafico;
	}

	/**
	 * Este metodo calcula a quantidade de eleitores do cargo.
	 * @return Quantidade de candidatos da apuracao eleitoral.
	 */
	private int quantidadeCandidatos() {
		int qtde = 2;
		try {
			dataBaseVotacaoCandidato.iniciaConexao();
			qtde += dataBaseVotacaoCandidato.quantidadeVotacaoCandidatos(cargo);
			dataBaseVotacaoCandidato.fechaConexao();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qtde;
	}

	/**
	 * Este metodo busca no banco de dados os eleitores e seus respectivos dados. Calcula a porcentagem de votos e adiciona
	 * em uma variavel <code>DefaultCategoryDataset</code>, para ser utilizada na criacao do grafico de barra.
	 */
	private void criaConjuntoDados() { 
		
		double porcentagem;
		try {
			dataBaseVotacaoCandidato.iniciaConexao();
			
			porcentagem = 100.0 / qtdeEleitores;
			
			resultado = dataBaseVotacaoCandidato.obterVotacaoCandidatos(data,cargo);
			while(resultado.next())
				dataSet.addValue(resultado.getInt("votos") * porcentagem, resultado.getString("candidato"), cargo);
			dataBaseVotacaoCandidato.fechaConexao();
			
			dataBancoDadosVotacaoCargos.iniciaConexao();
			dataSet.addValue(dataBancoDadosVotacaoCargos.obterQuantidadeBranco(cargo, data) * porcentagem, "Branco", cargo);
			dataSet.addValue(dataBancoDadosVotacaoCargos.obterQuantidadeNulo(cargo, data) * porcentagem, "Nulo", cargo);
			dataBancoDadosVotacaoCargos.fechaConexao();
			
		} catch (Exception e) {
			showMessageDialog(null, "Informe o Seguinte Erro ao Analista: " + e.toString(), "Atencao", INFORMATION_MESSAGE);
		}
	}
}

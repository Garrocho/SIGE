package com.sige.gui.pesquisa;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import com.alee.extended.date.WebDateField;
import com.sige.gui.ShadowBorder;
import com.sige.gui.lookup.LookUpCandidato;
import com.sige.gui.lookup.LookUpCargo;
import com.sige.gui.pesquisa.eventos.TratadorEventosCadastroPesquisa;
import com.sige.gui.recursos.SomenteNumeros;

/**
 * Esta classe extende um <code>JDialog</code> e cria uma interface grafica que permite o usuario consultar, alterar ou
 * excluir uma Pesquisa.
 * 
 * @author Charles Garrocho
 */
public class DialogoCadastrarPesquisa extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField cargoField, candidatoField, numeroBrancoNuloField, numeroIndecisosField,
	numeroEntrevistadosField, numeroMunicipiosField;
	private WebDateField dataInicioCalendario, dataFimCalendario;
	private JTable tabelaCandidato;
	private JButton botaoGravar, BotaoLimpar, botaoCancelar;

	/**
	 * Este e o construtor. Ele constroi a interface grafica do dialogo Cadastrar Pesquisa.
	 */
	public DialogoCadastrarPesquisa() {

		super();
		getRootPane().setBorder(new ShadowBorder());
		setTitle("Cadastro de Pesquisa Eleitoral");
		TratadorEventosCadastroPesquisa tratadorEventos = new TratadorEventosCadastroPesquisa(this);

		// Define o painelNorte como GridLayout, com 4 linhas e 1 coluna.
		JPanel painelNorte = new JPanel(new GridLayout(3,1));

		JPanel painelBrancoNuloIndeciso = new JPanel();

		// Define o painelBrancoNulo como GridLayout com 2 linhas 1 uma coluna.
		JPanel painelBrancoNulo = new JPanel(new GridLayout(2,1));

		// Define o painelDataInicio com 2 linhas e 1 coluna.
		JPanel painelDataInicio = new JPanel(new GridLayout(2,1));
		dataInicioCalendario = new WebDateField();
		((JTextField)dataInicioCalendario.getComponent(0)).setEditable(false);

		// Adiciona o labelDataInicio na primeira linha e o dataInicioCalendario na segunda linha do painelDataInicio.
		painelDataInicio.add(new JLabel("Data Inicio"));
		painelDataInicio.add(dataInicioCalendario);

		// Adiciona o painelDataInicio na primeira linha do painelInicioFim.
		painelBrancoNuloIndeciso.add(painelDataInicio);

		numeroBrancoNuloField = new JTextField(8);
		numeroBrancoNuloField.setToolTipText("Quantidade de Votos Brancos ou Nulos");
		numeroBrancoNuloField.setDocument(new SomenteNumeros());

		// Adiciona o labelBrancoNulo na primeira linha e o numeroBrancoNuloField na segunda linha do painelBrancoNulo.
		painelBrancoNulo.add(new JLabel(" Branco / Nulo"));
		painelBrancoNulo.add(numeroBrancoNuloField);

		// Adiciona o painelBrancoNulo ao painelBrancoNuloIndeciso.
		painelBrancoNuloIndeciso.add(painelBrancoNulo);

		// Define o painelIndecisos como GridLayout com 2 linhas 1 uma coluna.
		JPanel painelIndecisos = new JPanel(new GridLayout(2,0));

		numeroIndecisosField = new JTextField(8);
		numeroIndecisosField.setToolTipText("Quantidade de Eleitores Indecisos, Que N�o Souberam ou N�o Quiseram Responder.");
		numeroIndecisosField.setDocument(new SomenteNumeros());

		// Adiciona o labelIndecisos na primeira linha e o numeroIndecisosField na segunda linha do painelIndecisos.
		painelIndecisos.add(new JLabel(" Indecisos"));
		painelIndecisos.add(numeroIndecisosField);

		// Adiciona o painelIndecisos ao painelBrancoNuloIndeciso.
		painelBrancoNuloIndeciso.add(painelIndecisos);

		// Adiciona o painelBrancoNuloIndeciso na segunda linha do painelNorte.
		painelNorte.add(painelBrancoNuloIndeciso);

		JPanel painelMunicipioeleitores = new JPanel();

		// Define o painelEntrevistados como GridLayout com 2 linhas 1 uma coluna.
		JPanel painelEntrevistados = new JPanel(new GridLayout(2,1));

		// Define o painelDataFim com 2 linhas e 1 coluna.
		JPanel painelDataFim = new JPanel(new GridLayout(2,1));
		dataFimCalendario = new WebDateField();
		((JTextField)dataFimCalendario.getComponent(0)).setEditable(false);

		// Adiciona o labelDataFim na primeira linha e o dataFimCalendario na segunda linha do painelDataFim.
		painelDataFim.add(new JLabel("Data Fim"));
		painelDataFim.add(dataFimCalendario);

		// Adiciona o painelDataFim na segunda coluna do painelInicioFim.
		painelMunicipioeleitores.add(painelDataFim);

		numeroEntrevistadosField = new JTextField(8);
		numeroEntrevistadosField.setToolTipText("Quantidade de Eleitores Entrevistados.");
		numeroEntrevistadosField.setDocument(new SomenteNumeros());

		// Adiciona o labelEntrevistados na primeira linha e o numeroEntrevistadosField na segunda linha do painelEntrevistados.
		painelEntrevistados.add(new JLabel(" Eleitores"));
		painelEntrevistados.add(numeroEntrevistadosField);

		// Adiciona o painelEntrevistados ao painelMunicipioeleitores.
		painelMunicipioeleitores.add(painelEntrevistados);

		// Define o painelMunicipio como GridLayout com 2 linhas 1 uma coluna.
		JPanel painelMunicipio = new JPanel(new GridLayout(2,1));

		numeroMunicipiosField = new JTextField(8);
		numeroMunicipiosField.setToolTipText("Quantidade de Municipios que a Pesquisa Foi Realizada.");
		numeroMunicipiosField.setDocument(new SomenteNumeros());

		// Adiciona o labelMunicipio na primeira linha e o numeroMunicipiosField na segunda linha do painelMunicipio.
		painelMunicipio.add(new JLabel(" Municipios"));
		painelMunicipio.add(numeroMunicipiosField);

		// Adiciona o painelMunicipio ao painelMunicipioeleitores.
		painelMunicipioeleitores.add(painelMunicipio);

		// Adiciona o painelMunicipioeleitores na terceira linha do painelNorte.
		painelNorte.add(painelMunicipioeleitores);

		// Define o painelMunicipio como GridLayout com 2 linhas 1 uma coluna.
		JPanel painelCargoBotao = new JPanel(new GridLayout(2,1));
		JPanel painelCargo = new JPanel();
		cargoField = new JTextField(13);
		cargoField.setEditable(false);
		JButton botaoPesquisaCargo = new JButton();
		botaoPesquisaCargo.setIcon(new ImageIcon(getClass().getResource("/icones/pesquisar.png")));
		botaoPesquisaCargo.setPreferredSize(new Dimension(30,30));
		botaoPesquisaCargo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botaoPesquisaCargo.setToolTipText("Clique aqui para selecionar um cargo.");
		
		// Adiciona um tratamento de eventos ao botao pesquisarCargo.
		botaoPesquisaCargo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new LookUpCargo(getThis(),getThis().getCargoField());
			}
		});

		JButton botaoPesquisaCandidato = new JButton("Candidato", new ImageIcon(getClass().getResource("/icones/mais.png")));
		botaoPesquisaCandidato.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botaoPesquisaCandidato.setToolTipText("Clique aqui para adicionar um candidato.");

		// Adiciona um tratamento de eventos ao botao botaoPesquisaCandidato.
		botaoPesquisaCandidato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new LookUpCandidato(getThis(), getThis().getTabelaCandidato(), getThis().getCargoField().getText());
			}
		});
		
		// Adiciona o labelCargo, cargoField e o botaoPesquisaCargo no painelCargo.
		painelCargo.add(new JLabel("Cargo"));
		painelCargo.add(cargoField);
		painelCargo.add(botaoPesquisaCargo);
		
		painelCargoBotao.add(painelCargo);
		painelCargo.add(botaoPesquisaCandidato);

		// Adiciona o painelCargoCandidato na quarta linha do painelNorte.
		painelNorte.add(painelCargo);

		// Adiciona o painelNorte na regiao central do Dialogo Cadastrar Pesquisa.
		add(painelNorte, BorderLayout.NORTH);

		JPanel painelCentro = new JPanel();

		String colunasTabela[] = {"Numero","Candidato","Votos"},
				dadosTabela[][] = new String[0][0];
		tabelaCandidato = new JTable(new DefaultTableModel(dadosTabela, colunasTabela));
		tabelaCandidato.setPreferredScrollableViewportSize(new Dimension(345, 100));
		tabelaCandidato.addMouseListener(tratadorEventos);
		tabelaCandidato.getColumn("Numero").setPreferredWidth(30);
		tabelaCandidato.getColumn("Candidato").setPreferredWidth(200);
		tabelaCandidato.getColumn("Votos").setPreferredWidth(30);
		tabelaCandidato.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		// Adiciona na BarraRolagem a tabela de candidatos.
		JScrollPane barraRolagem = new JScrollPane(tabelaCandidato);

		// Adiciona a barraRolagem no painelCentro.
		painelCentro.add(barraRolagem);

		// Adiciona o painelCentro na regiao central do Dialogo Cadastrar Pesquisa.
		add(painelCentro, BorderLayout.CENTER);

		JPanel painelSul = new JPanel();
		botaoGravar   = new JButton("GRAVAR", new ImageIcon(getClass().getResource("/icones/gravar.png")));
		BotaoLimpar   = new JButton("LIMPAR", new ImageIcon(getClass().getResource("/icones/limpar.png")));
		botaoCancelar = new JButton("CANCELAR", new ImageIcon(getClass().getResource("/icones/cancelar.png")));
		
		botaoGravar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
		BotaoLimpar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
		botaoCancelar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
		
		botaoGravar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		BotaoLimpar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botaoCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		
		botaoGravar.addActionListener(tratadorEventos);
		BotaoLimpar.addActionListener(tratadorEventos);
		botaoCancelar.addActionListener(tratadorEventos);

		// Adiciona os botoes(limpar, sair) ao painelSul.
		painelSul.add(botaoGravar);
		painelSul.add(BotaoLimpar);
		painelSul.add(botaoCancelar);

		// Adiciona o painelSul na regiao sul do Dialogo Cadastrar Pesquisa.
		add(painelSul, BorderLayout.SOUTH);

		// Define as propriedades do Dialogo.
		pack();
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icones/icone.png")));
		setLocationRelativeTo(null);
		setModal(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	/** 
	 * Este metodo retorna a referencia da propria classe.
	 * 
	 * @return <code>DialogoCadastrarPesquisa</code> com a referencia da classe.
	 */
	private DialogoCadastrarPesquisa getThis() {
		return this;
	}

	/**
	 * Este metodo retorna uma referencia do Field Cargo do Dialogo.
	 * 
	 * @return <code>JTextField</code> com o nome do cargo.
	 */
	public JTextField getCargoField() {
		return cargoField;
	}

	/**
	 * Este metodo retorna uma referencia do Field Candidato do Dialogo.
	 * 
	 * @return <code>JTextField</code> com o nome do candidato.
	 */
	public JTextField getCandidatoField() {
		return candidatoField;
	}

	/**
	 * Este metodo retorna uma referencia do Field NumeroBrancoNulo do Dialogo.
	 * 
	 * @return <code>JTextField</code> com o numero de Branco/Nulos da Pesquisa.
	 */
	public JTextField getNumeroBrancoNuloField() {
		return numeroBrancoNuloField;
	}

	/**
	 * Este metodo retorna uma referencia do Field NumeroIndecisos do Dialogo.
	 * 
	 * @return <code>JTextField</code> com o numero de Indecisos da Pesquisa.
	 */
	public JTextField getNumeroIndecisosField() {
		return numeroIndecisosField;
	}

	/**
	 * Este metodo retorna uma referencia do Field NumeroEntrevistados do Dialogo.
	 * 
	 * @return <code>JTextField</code> com o numero de Entrevistados da Pesquisa.
	 */
	public JTextField getNumeroEntrevistadosField() {
		return numeroEntrevistadosField;
	}

	/**
	 * Este metodo retorna uma referencia do Field NumeroMunicipios do Dialogo.
	 * 
	 * @return <code>JTextField</code> com o numero de Municipios da Pesquisa.
	 */
	public JTextField getNumeroMunicipiosField() {
		return numeroMunicipiosField;
	}

	/**
	 * Este metodo retorna uma referencia da DataInicioCalendario do Dialogo.
	 * 
	 * @return <code>WebDateField</code> com a Data Inicio da Pesquisa.
	 */
	public WebDateField getDataInicioCalendario() {
		return dataInicioCalendario;
	}

	/**
	 * Este metodo retorna uma referencia da DataFimCalendario do Dialogo.
	 * 
	 * @return <code>WebDateField</code> com a Data Fim da Pesquisa.
	 */
	public WebDateField getDataFimCalendario() {
		return dataFimCalendario;
	}

	/**
	 * Este metodo retorna uma referencia da tabela de candidatos da Pesquisa.
	 * 
	 * @return <code>JTable</code> com o conteudo da tabela.
	 */
	public JTable getTabelaCandidato() {
		return tabelaCandidato;
	}

	/**
	 * Este metodo retorna uma referencia do Botao Gravar do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao gravar.
	 */
	public JButton getBotaoGravar() {
		return botaoGravar;
	}

	/**
	 * Este metodo retorna uma referencia do Botao Limpar do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao limpar.
	 */
	public JButton getBotaoLimpar() {
		return BotaoLimpar;
	}
	
	/**
	 * Este metodo retorna uma referencia do Botao Cancelar do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao cancelar.
	 */
	public JButton getBotaoCancelar() {
		return botaoCancelar;
	}
}
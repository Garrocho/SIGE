package com.sige.gui.lookup;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.sige.gui.lookup.eventos.TratadorEventosLookUpPartido;

/**
 * Esta classe extende um <code>JDialog</code> e cria uma interface grafica para o dialogo LookUpPartido, que permite o usuario
 * pesquisa um partido.
 * 
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class LookUpPartido extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField partido;
	private JTextField fieldNome;
	private JTable tabelaPartidos;
	private JButton botaoLimpar;
	private JButton botaoSair;

	/**
	 * Este e o contrutor. Ele recebe como argumento um <code>JTextField</code> que sera utilizado para adicionar os dados 
	 * pesquisados no LookUpCargo.
	 * 
	 * @param dialogo um <code>JDialog</code> com um dialogo.
	 * @param partido um <code>JTextField</code> com um partido.
	 */
	public LookUpPartido(JDialog dialogo, JTextField partido) {

		super(dialogo, "Consulta de Partido");
		this.partido = partido;
		TratadorEventosLookUpPartido tratadorEventos = new TratadorEventosLookUpPartido(this);

		JPanel painelNorte = new JPanel();
		fieldNome = new JTextField(23);
		fieldNome.setToolTipText("Descreva Aqui o Nome de Partido a Ser Pesquisado. Utilize o \'%\' Para Uma Melhor Pesquisa.");
		fieldNome.addKeyListener(tratadorEventos);

		// Adiciona o labelNome e o fieldNome no painelNorte.
		painelNorte.add(new JLabel("Nome"));
		painelNorte.add(fieldNome);

		// Adiciona o painelNorte na regiao norte do LookUpPartido.
		add(painelNorte, BorderLayout.NORTH);			

		JPanel painelCentro = new JPanel();

		String colunasTabela[] = {"Numero", "Nome", "Sigla"},
				dadosTabela[][] = new String[0][0];

		tabelaPartidos = new JTable(new DefaultTableModel(dadosTabela, colunasTabela));

		// Define as propriedades da tabela de partidos.
		tabelaPartidos.setPreferredScrollableViewportSize(new Dimension(300, 200));
		tabelaPartidos.addMouseListener(tratadorEventos);
		tabelaPartidos.getColumn("Numero").setPreferredWidth(35);
		tabelaPartidos.getColumn("Nome").setPreferredWidth(170);
		tabelaPartidos.getColumn("Sigla").setPreferredWidth(20);
		tabelaPartidos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		// Instancia uma nova barra de rolagem passando como argumento a tabela de candidatos.
		JScrollPane barraRolagem = new JScrollPane(tabelaPartidos);

		// Adiciona a barra de rolagem no painelCentro.
		painelCentro.add(barraRolagem);

		// Adiciona o painelCentro na regiao central do LookUpPartido.
		add(painelCentro, BorderLayout.CENTER);

		JPanel painelSul = new JPanel();
		botaoLimpar = new JButton("LIMPAR", new ImageIcon(getClass().getResource("/icones/limpar.png")));
		botaoSair   = new JButton("SAIR", new ImageIcon(getClass().getResource("/icones/sair2.png")));

		botaoLimpar.addActionListener(tratadorEventos);
		botaoSair.addActionListener(tratadorEventos);

		botaoLimpar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botaoSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		botaoLimpar.setFont(new Font("Arial", Font.BOLD, 14));
		botaoSair.setFont(new Font("Arial", Font.BOLD, 14));

		// Adiciona os botoes(limpar, Sair) no painelSul.
		painelSul.add(botaoLimpar);
		painelSul.add(botaoSair);

		// Adiciona o painelSul na regiao sul do LookUpPartido.
		add(painelSul, BorderLayout.SOUTH);

		// Define as propriedades do dialogo.
		pack();
		setLocationRelativeTo(null);
		setModal(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	/**
	 * Este metodo retorna uma referencia do Field Partido.
	 * 
	 * @return <code>JTextField</code> com o partido.
	 */	
	public JTextField getPartido() {
		return partido;
	}

	/**
	 * Este metodo retorna uma referencia do Field Nome do Dialogo.
	 * 
	 * @return <code>JTextField</code> com nome.
	 */	
	public JTextField getFieldNome() {
		return fieldNome;
	}

	/**
	 * Este metodo retorna uma referencia da Tabela Partidos do Dialogo.
	 * 
	 * @return <code>JTable</code> com a tabela de partidos.
	 */	
	public JTable getTabelaPartidos() {
		return tabelaPartidos;
	}

	/** Este metodo retorna uma referencia do Botao Limpar do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao limpar.
	 */
	public JButton getBotaoLimpar() {
		return botaoLimpar;
	}

	/** Este metodo retorna uma referencia do Botao Sair do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao Sair.
	 */
	public JButton getBotaoSair() {
		return botaoSair;
	}
}
package com.sige.gui.lookup;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.sige.gui.ShadowBorder;
import com.sige.gui.lookup.eventos.TratadorEventosLookUpCargo;

/**
 * Esta classe extende um <code>JDialog</code> e cria uma interface grafica para o dialogo LookUpCargo, que permite o usuario
 * pesquisa um cargo.
 * 
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class LookUpCargo extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField cargo;
	private JTable tabelaCargo;
	private JTextField fieldNome;
	private JTable tabelaCargos;
	private JButton botaoLimpar;
	private JButton botaoSair;
	private Component cargoAux;

	/**
	 * Este e o contrutor. Ele recebe como argumento um <code>Component</code> que sera utilizado para adicionar os dados pesquisados
	 * no LookUpCargo.
	 * 
	 * @param dialogo um <code>JDialog</code> com um dialogo.
	 * @param cargoAux um <code>Component</code> com um componente.
	 */
	public LookUpCargo(JDialog dialogo, Component cargoAux) {

		super(dialogo, "Consulta de Cargo");
		getRootPane().setBorder(new ShadowBorder());
		TratadorEventosLookUpCargo tratadorEventos = new TratadorEventosLookUpCargo(this);

		this.cargoAux = cargoAux;

		// Verifica se a o Componente e um JTextField ou um JTable.
		if (cargoAux instanceof JTextField)
			this.cargo = (JTextField)cargoAux;
		if (cargoAux instanceof JTable)
			this.tabelaCargo = (JTable)cargoAux;

		JPanel painelNorte = new JPanel();
		fieldNome = new JTextField(23);
		fieldNome.setToolTipText("Descreva Aqui o Nome do Cargo a Ser Pesquisado. Utilize o \'%\' Para Uma Melhor Pesquisa.");
		fieldNome.addKeyListener(tratadorEventos);

		// Adiciona o labelNome e o fieldNome no painelNorte.
		painelNorte.add(new JLabel("Nome"));
		painelNorte.add(fieldNome);

		// Adiciona o painelNorte na regiao norte do LookUpCargo.
		add(painelNorte, BorderLayout.NORTH);			

		JPanel painelCentro = new JPanel();

		String colunasTabela[] = {"ID", "Nome", "Digitos"},
				dadosTabela[][] = new String[0][0];

		tabelaCargos = new JTable(new DefaultTableModel(dadosTabela, colunasTabela));

		// Define as propriedades da tabela de cargos.
		tabelaCargos.addMouseListener(tratadorEventos);
		tabelaCargos.setPreferredScrollableViewportSize(new Dimension(300, 200));
		tabelaCargos.getColumn("ID").setPreferredWidth(10);
		tabelaCargos.getColumn("Nome").setPreferredWidth(170);
		tabelaCargos.getColumn("Digitos").setPreferredWidth(20);
		tabelaCargos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		// Instancia uma nova barra de rolagem passando como argumento a tabela de cargos.
		JScrollPane barraRolagem = new JScrollPane(tabelaCargos);
		
		// Adiciona a barra de rolagem no painelCentro,
		painelCentro.add(barraRolagem);
		
		// Adiciona o painelCentro na regiao central do LookUpCargo.
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
		
		// Adiciona os botoes(limpar, sair) ao painelSul.
		painelSul.add(botaoLimpar);
		painelSul.add(botaoSair);

		// Adiciona o painelSul na regiao sul do LookUpCargo.
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
	 * Este metodo retorna uma referencia da Tabela Cargo.
	 * 
	 * @return <code>JTable</code> com a tabela de cargo.
	 */	
	public JTable getTabelaCargo() {
		return tabelaCargo;
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
	 * Este metodo retorna uma referencia da Tabela Cargos do Dialogo.
	 * 
	 * @return <code>JTable</code> com a tabela de cargo.
	 */	
	public JTable getTabelaCargos() {
		return tabelaCargos;
	}

	/**
	 * Este metodo retorna uma referencia do Botao Limpar do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao limpar.
	 */	
	public JButton getBotaoLimpar() {
		return botaoLimpar;
	}

	/**
	 * Este metodo retorna uma referencia do Botao Sair do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao sair.
	 */	
	public JButton getBotaoCancelar() {
		return botaoSair;
	}

	/**
	 * Este metodo retorna uma referencia do Componente cargoAux do Dialogo.
	 * 
	 * @return <code>Component</code> com componente cargoAux.
	 */	
	public Component getCargoAux() {
		return cargoAux;
	}

	/**
	 * Este metodo retorna uma referencia do Field Cargo do Dialogo.
	 * 
	 * @return <code>JTextField</code> com o cargo.
	 */	
	public JTextField getCargo() {
		return cargo;
	}
}
package com.sige.gui.lookup;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.sige.gui.lookup.eventos.TratadorEventosLookUpCandidato;
import com.sige.gui.recursos.SomenteNumeros;
import com.sige.gui.recursos.TamanhoMaximo;

/**
 * Esta classe extende um <code>JDialog</code> e cria uma interface grafica para o dialogo LookUpCandidato, que permite o usuario
 * pesquisar um candidato filtrando pelo cargo.
 * 
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class LookUpCandidato extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField fieldNome, fieldNumero;
	private JButton botaoSair, botaoLimpar;
	private JTable tabelaCandidato;
	private String cargo;
	private JTable tabelaCandidatos;

	/**
	 * Este e o contrutor. Ele recebe como argumento um cargo que sera utilizado para filtrar os candidatos e caso um candidato seja selecionado,
	 * ele e adicionado na tabela que e passado como argumento no construtor.
	 * 
	 * @param dialogo um <code>JDialog</code> com um dialogo.
	 * @param tabelaCandidato um <code>JTable</code> a tabela de candidatos.
	 * @param cargo um <code>String</code> com o nome do cargo.
	 */
	public LookUpCandidato(JDialog dialogo, JTable tabelaCandidato, String cargo) {

		super(dialogo, "Consulta de Candidato");
		this.cargo = cargo;
		this.tabelaCandidato = tabelaCandidato;
		TratadorEventosLookUpCandidato tratadorEventos = new TratadorEventosLookUpCandidato(this);

		JPanel painelNorte = new JPanel();

		// Define o painelNome como GridLayout com duas linhas e uma coluna.
		JPanel painelNome = new JPanel(new GridLayout(2,1));

		fieldNome = new JTextField(21);
		fieldNome.setToolTipText("Descreva Aqui o Nome do Candidato a Ser Pesquisado. Utilize \'%\' Para Uma Melhor Pesquisa.");
		fieldNome.addKeyListener(tratadorEventos);
		fieldNome.setDocument( new TamanhoMaximo(30));

		// Adiciona o labelNome na primeira linha e o fieldNome na segunda linha do painelNome.
		painelNome.add(new JLabel("Nome"));
		painelNome.add(fieldNome);

		// Adiciona o painelNome no painelNorte.
		painelNorte.add(painelNome);

		// Define o painelOUNumero como GridLayout com duas linhas e uma coluna.
		JPanel painelOUNumero = new JPanel(new GridLayout(2,1));

		JLabel aux = new JLabel();
		JLabel ou = new JLabel("-OU-");
		ou.setFont(new Font("Arial",Font.BOLD,19));

		// Adiciona o labelAux na primeira linha e o labelOU na segunda linha do painelOUNumero. 
		painelOUNumero.add(aux);
		painelOUNumero.add(ou);

		// Adiciona o painelOUNumero no painelNorte.
		painelNorte.add(painelOUNumero);

		// Define o painelNumero como GridLayout com duas linhas e uma coluna.
		JPanel painelNumero = new JPanel(new GridLayout(2,1));

		fieldNumero = new JTextField(5);
		fieldNumero.setToolTipText("Descreva Aqui o Numero do Candidato a Ser Pesquisado.");
		fieldNumero.setDocument(new SomenteNumeros());
		fieldNumero.addKeyListener(tratadorEventos);

		// Adiciona o labelNumero na primeira linha e o fieldNumero na segunda linha do painelNumero. 
		painelNumero.add(new JLabel(" Numero"));
		painelNumero.add(fieldNumero);

		// Adiciona o painelNumero no painelNorte.
		painelNorte.add(painelNumero);

		// Adiciona o painelNorte na regiao norte do LookUpCandidato.
		add(painelNorte, BorderLayout.NORTH);

		JPanel painelCentro = new JPanel();

		String colunasTabela[] = {"Numero", "Nome", "Partido", "Cargo"},
				dadosTabela[][] = new String[0][0];

		tabelaCandidatos = new JTable(new DefaultTableModel(dadosTabela, colunasTabela)){
			private static final long serialVersionUID = 5727320816550514929L;
			public boolean isCellEditable(int rowIndex, int colIndex) {
				if (colIndex == getColumn("Nome").getModelIndex() || colIndex == getColumn("Numero").getModelIndex() 
						|| colIndex == getColumn("Partido").getModelIndex() || colIndex == getColumn("Cargo").getModelIndex()){
					return false; //evita a edicao das celulas
				}
				else
					return true;
			}
		};

		// Define as propriedades da tabela de candidatos.
		tabelaCandidatos.setPreferredScrollableViewportSize(new Dimension(360, 200));
		tabelaCandidatos.getColumn("Numero").setPreferredWidth(30);
		tabelaCandidatos.getColumn("Nome").setPreferredWidth(140);
		tabelaCandidatos.getColumn("Partido").setPreferredWidth(27);
		tabelaCandidatos.getColumn("Cargo").setPreferredWidth(60);
		tabelaCandidatos.addMouseListener(tratadorEventos);
		tabelaCandidatos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		// Instancia uma barra de rolagem passando como argumento a tabela de candidatos.
		JScrollPane barraRolagem = new JScrollPane(tabelaCandidatos);

		// adiciona a barra de rolagem no painelCentro.
		painelCentro.add(barraRolagem);

		// Adiciona o painelCentro na regiao central do LookUpCandidato.
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

		// Adiciona os botoes(limpar, sair) no painelSul.
		painelSul.add(botaoLimpar);
		painelSul.add(botaoSair);

		// Adiciona o painelSul na regiao sul do LookUpCandidato.
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
	 * Este metodo retorna uma referencia do Field Nome do Dialogo.
	 * 
	 * @return <code>JTextField</code> com nome.
	 */	
	public JTextField getFieldNome() {
		return fieldNome;
	}

	/**
	 * Este metodo retorna uma referencia do Field Numero do Dialogo.
	 * 
	 * @return <code>JTextField</code> com numero.
	 */	
	public JTextField getFieldNumero() {
		return fieldNumero;
	}

	/**
	 * Este metodo retorna uma referencia do Botao Sair do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao sair.
	 */	
	public JButton getBotaoSair() {
		return botaoSair;
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
	 * Este metodo retorna uma referencia da Tabela Candidato.
	 * 
	 * @return <code>JTable</code> com a tabela de candidato.
	 */	
	public JTable getTabelaCandidato() {
		return tabelaCandidato;
	}

	/**
	 * Este metodo retorna uma referencia do Cargo.
	 * 
	 * @return <code>String</code> com o cargo.
	 */	
	public String getCargo() {
		return cargo;
	}

	/**
	 * Este metodo retorna uma referencia da Tabela Candidatos do Dialogo.
	 * 
	 * @return <code>JTable</code> com a tabela de candidato.
	 */	
	public JTable getTabelaCandidatos() {
		return tabelaCandidatos;
	}

}	
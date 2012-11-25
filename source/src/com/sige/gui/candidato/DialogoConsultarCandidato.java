package com.sige.gui.candidato;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

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

import com.sige.gui.JanelaPrincipal;
import com.sige.gui.candidato.eventos.TratadorEventosConsultaCandidato;
import com.sige.gui.recursos.SomenteNumeros;
import com.sige.gui.recursos.TamanhoMaximo;

/**
 * Esta classe extende um <code>JDialog</code> e cria uma interface grafica que permite o usuario consultar, alterar ou
 * excluir um candidato.
 * 
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class DialogoConsultarCandidato extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField fieldNome, fieldNumero;
	private JButton botaoSair, botaoLimpar;
	private JTable tabelaCandidatos;

	/**
	 * Este e o construtor. Ele constroi a interface grafica do dialogo consultar candidato. 
	 * 
	 * @param janelaPrincipal um <code>JanelaPrincipal</code> com a referência da janela pai.
	 */
	public DialogoConsultarCandidato(JanelaPrincipal janelaPrincipal) {

		super(janelaPrincipal, "Listagem de Candidatos");
		TratadorEventosConsultaCandidato tratadorEventos = new TratadorEventosConsultaCandidato(this);

		JPanel painelNorte = new JPanel();

		// Define o painelNome como GridLayout com duas linhas e uma coluna.
		JPanel painelNome = new JPanel(new GridLayout(2,1));
		painelNome.add( new JLabel(" Nome"));
		fieldNome = new JTextField(34);
		fieldNome.setToolTipText("Descreva Aqui o Nome do Candidato a Ser Pesquisado. Utilize \'%\' Para Uma Melhor Pesquisa.");
		fieldNome.addKeyListener(tratadorEventos);
		fieldNome.setDocument( new TamanhoMaximo(30));
		painelNome.add(fieldNome);
		
		// Adiciona o painelNome e todo seu conteudo ao painelNorte.
		painelNorte.add(painelNome);

		// Define o painelOUNumero como GridLayout com duas linhas e uma coluna.
		JPanel painelOUNumero = new JPanel(new GridLayout(2,1));
		JLabel ou = new JLabel("  -OU-  ");
		ou.setFont(new Font("Arial",Font.BOLD,19));
		painelOUNumero.add(new JLabel());
		painelOUNumero.add(ou);
		
		// Adiciona o painelOUNumero e todo seu conteudo ao painelNorte.
		painelNorte.add(painelOUNumero);

		// Define o painelNumero como GridLayout com duas linhas e uma coluna.
		JPanel painelNumero = new JPanel(new GridLayout(2,1));
		painelNumero.add(new JLabel(" Numero"));
		fieldNumero = new JTextField(8);
		fieldNumero.setToolTipText("Descreva Aqui o Numero do Candidato a Ser Pesquisado.");
		fieldNumero.setDocument(new SomenteNumeros());
		fieldNumero.addKeyListener(tratadorEventos);
		painelNumero.add(fieldNumero);
		
		// Adiciona o painelNumero e todo seu conteudo ao painelNorte.
		painelNorte.add(painelNumero);

		// Adiciona o painelNorte na regiao norte do Dialogo Consultar Candidato.
		add(painelNorte, BorderLayout.NORTH);

		JPanel painelCentro = new JPanel();
		String colunasTabela[] = {"Numero", "Nome", "Partido", "Cargo"},
				dadosTabela[][] = new String[0][0];

		tabelaCandidatos = new JTable(new DefaultTableModel(dadosTabela, colunasTabela)){
			private static final long serialVersionUID = 5727320816550514929L;
			public boolean isCellEditable(int rowIndex, int colIndex) {
				if (colIndex == getColumn("Numero").getModelIndex() || colIndex == getColumn("Nome").getModelIndex() 
						|| colIndex == getColumn("Partido").getModelIndex() || colIndex == getColumn("Cargo").getModelIndex())
					return false; // Evita a edicao das celulas.
				else
					return true;
			}
		};
		tabelaCandidatos.setPreferredScrollableViewportSize(new Dimension(420, 230));
		tabelaCandidatos.getColumn("Numero").setPreferredWidth(35);
		tabelaCandidatos.getColumn("Nome").setPreferredWidth(160);
		tabelaCandidatos.getColumn("Partido").setPreferredWidth(30);
		tabelaCandidatos.getColumn("Cargo").setPreferredWidth(100);
		tabelaCandidatos.addMouseListener(tratadorEventos);
		tabelaCandidatos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		// Cria uma barra de rolagem para a tabela de Candidatos.
		JScrollPane barraRolagem = new JScrollPane(tabelaCandidatos);

		// Adiciona a barra de rolagem ao painelCentro.
		painelCentro.add(barraRolagem);

		// Adiciona o painelCentro na regiao central do Dialogo Consultar Candidato.
		add(painelCentro, BorderLayout.CENTER);

		JPanel painelSul = new JPanel();
		
		// Instancia os botoes(limpar, sair) e adiciona tratamento de eventos para eles.
		botaoLimpar = new JButton("LIMPAR", new ImageIcon(getClass().getResource("/icones/limpar.png")));
		botaoSair   = new JButton("SAIR", new ImageIcon(getClass().getResource("/icones/sair2.png")));

		botaoLimpar.addActionListener(tratadorEventos);
		botaoSair.addActionListener(tratadorEventos);

		botaoLimpar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botaoSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		botaoLimpar.setFont(new Font("Arial", Font.BOLD, 14));
		botaoSair.setFont(new Font("Arial", Font.BOLD, 14));
		
		// Adiciona os botoes no painelSul.
		painelSul.add(botaoLimpar);
		painelSul.add(botaoSair);

		// Adiciona o painelSul na regiao sul do Dialogo Consultar Candidato.
		add(painelSul, BorderLayout.SOUTH);

		pack();
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icones/icone.png")));
		setLocationRelativeTo(null);
		setModal(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	/**
	 * Este metodo retorna uma referencia do Field Nome do Dialogo.
	 * 
	 * @return <code>JTextField</code> com o nome do candidato.
	 */
	public JTextField getFieldNome() {
		return fieldNome;
	}

	/**
	 * Este metodo retorna uma referencia do Field Numero do Dialogo.
	 * 
	 * @return <code>JTextField</code> com o numero do candidato.
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
	 * Este metodo retorna uma referencia da tabela de candidatos do Dialogo.
	 * 
	 * @return <code>JTable</code> com o conteudo da tabela.
	 */
	public JTable getTabelaCandidatos() {
		return tabelaCandidatos;
	}
}
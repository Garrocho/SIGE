package com.sige.gui.partido;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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

import com.sige.gui.partido.eventos.TratadorEventosConsultaPartido;

/**
 * Esta classe extende um <code>JDialog</code> e cria uma interface grafica que permite o usuario consultar, alterar ou
 * excluir um partido.
 * 
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class DialogoConsultarPartido extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField fieldNome;
	private JTable tabelaPartidos;
	private JButton botaoLimpar;
	private JButton botaoSair;
	private int opcao;

	/**
	 * Este e o construtor. Ele constroi a interface grafica do dialogo consultar cargo. Caso seja uma consulta, o usuario
	 * deve informar "1" no campo "opcao", "2" alteracao, "3" exclusao.
	 * 
	 * @param opcao <code>int</code> com a opcao do usuario. "1" para consultar, "2" para editar ou "3" para excluir dados do
	 * partido.
	 * @param titulo <code>String</code> com o titulo da dialogo.
	 */
	public DialogoConsultarPartido(int opcao, String titulo) {

		super();
		setTitle(titulo);
		this.opcao = opcao;
		TratadorEventosConsultaPartido tratadorEventos = new TratadorEventosConsultaPartido(this);

		JPanel painelNorte = new JPanel();
		fieldNome = new JTextField(43);
		fieldNome.setToolTipText("Descreva Aqui o Nome de Partido a Ser Pesquisado. Utilize o \'%\' Para Uma Melhor Pesquisa.");
		fieldNome.addKeyListener(tratadorEventos);

		// Adiciona o LabelNome e o FieldNome no painelNorte.
		painelNorte.add(new JLabel("Nome"));
		painelNorte.add(fieldNome);

		// Adiciona o painelNorte e seus dados na regiao norte do Dialogo Consultar Partido.
		add(painelNorte, BorderLayout.NORTH);			

		JPanel painelCentro = new JPanel();

		String colunasTabela[] = {"Numero", "Nome", "Sigla"},
				dadosTabela[][] = new String[0][0];

		tabelaPartidos = new JTable(new DefaultTableModel(dadosTabela, colunasTabela)){
			private static final long serialVersionUID = 5727320816550514929L;
			public boolean isCellEditable(int rowIndex, int colIndex) {
				if (colIndex == getColumn("Numero").getModelIndex() || colIndex == getColumn("Nome").getModelIndex() || colIndex == getColumn("Sigla").getModelIndex())
					return false; // Evita a edicao das celulas.
				else
					return true;
			}
		};
		tabelaPartidos.setPreferredScrollableViewportSize(new Dimension(390, 250));
		tabelaPartidos.addMouseListener(tratadorEventos);
		tabelaPartidos.getColumn("Numero").setPreferredWidth(35);
		tabelaPartidos.getColumn("Nome").setPreferredWidth(230);
		tabelaPartidos.getColumn("Sigla").setPreferredWidth(50);
		tabelaPartidos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		// Adiciona na BarraRolagem a tabela de partidos.
		JScrollPane barraRolagem = new JScrollPane(tabelaPartidos);

		// Adiciona a barraRolagem no painelCentro.
		painelCentro.add(barraRolagem);

		// Adiciona o painelCentro na regiao central do Dialogo Consultar Partido.
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

		// Adiciona o painelSul na regiao sul do Dialogo Consultar Cargo.
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
	 * Este metodo retorna uma referencia do Field Nome do Dialogo.
	 * 
	 * @return <code>JTextField</code> com o nome do partido.
	 */
	public JTextField getFieldNome() {
		return fieldNome;
	}

	/**
	 * Este metodo retorna uma referencia da tabela de partidos do Dialogo.
	 * 
	 * @return <code>JTable</code> com o conteudo da tabela.
	 */
	public JTable getTabelaPartidos() {
		return tabelaPartidos;
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
	public JButton getBotaoSair() {
		return botaoSair;
	}

	/**
	 * Este metodo retorna uma referencia da opcao, neste inteiro fica armazenado a opcao do usuario para saber se o mesmo e
	 * consulta, alteracao ou exclusao de partido.
	 * 
	 * @return <code>int</code> com a opcao do usuario.
	 */
	public int getOpcao() {
		return opcao;
	}
}
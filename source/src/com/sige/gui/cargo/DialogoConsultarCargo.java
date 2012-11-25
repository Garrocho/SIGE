package com.sige.gui.cargo;

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

import com.sige.gui.cargo.eventos.TratadorEventosConsultaCargo;

/**
 * Esta classe extende um <code>JDialog</code> e cria uma interface grafica que permite o usuario consultar, alterar ou
 * excluir um cargo.
 * 
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class DialogoConsultarCargo extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField fieldNome;
	private JTable tabelaCargos;
	private JButton botaoLimpar;
	private JButton botaoSair;
	private int opcao;

	/**
	 * Este e o construtor. Ele constroi a interface grafica do dialogo consultar cargo. Caso seja uma consulta, o usuario
	 * deve informar "1" no campo "opcao", "2" alteracao, "3" exclusao.
	 * 
	 * @param opcao <code>int</code> com a opcao do usuario. "1" para consultar, "2" para editar ou "3" para excluir dados do
	 * cargo.
	 * @param titulo <code>String</code> com o titulo da dialogo.
	 */
	public DialogoConsultarCargo(int opcao, String titulo) {

		super();
		setTitle(titulo);
		this.opcao = opcao;
		TratadorEventosConsultaCargo tratadorEventos = new TratadorEventosConsultaCargo(this);

		JPanel painelNorte = new JPanel();
		fieldNome = new JTextField(33);
		fieldNome.setToolTipText("Descreva Aqui o Nome do Cargo a Ser Pesquisado. Utilize o \'%\' Para Uma Melhor Pesquisa.");
		fieldNome.addKeyListener(tratadorEventos);

		// Adiciona o LabelNome e o FieldNome no painelNorte.
		painelNorte.add(new JLabel("Nome"));
		painelNorte.add(fieldNome);

		// Adiciona o painelNorte e seus dados na regiao norte do Dialogo Consultar Cargo.
		add(painelNorte, BorderLayout.NORTH);			

		JPanel painelCentro = new JPanel();
		String colunasTabela[] = {"ID", "NOME", "DIGITOS"},
				dadosTabela[][] = new String[0][0];

		tabelaCargos = new JTable(new DefaultTableModel(dadosTabela, colunasTabela)){
			private static final long serialVersionUID = 5727320816550514929L;
			public boolean isCellEditable(int rowIndex, int colIndex) {
				if (colIndex == getColumn("ID").getModelIndex() || colIndex == getColumn("NOME").getModelIndex() || colIndex == getColumn("DIGITOS").getModelIndex())
					return false; // Evita a edicao das celulas.
				else
					return true;
			}
		};
		tabelaCargos.addMouseListener(tratadorEventos);
		tabelaCargos.setPreferredScrollableViewportSize(new Dimension(300, 200));
		tabelaCargos.getColumn("ID").setPreferredWidth(10);
		tabelaCargos.getColumn("NOME").setPreferredWidth(160);
		tabelaCargos.getColumn("DIGITOS").setPreferredWidth(30);
		tabelaCargos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		// Adiciona na BarraRolagem a tabela de cargos.
		JScrollPane barraRolagem = new JScrollPane(tabelaCargos);

		// Adiciona a barraRolagem no painelCentro.
		painelCentro.add(barraRolagem);

		// Adiciona o painelCentro na regiao central do Dialogo Consultar Cargo.
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
	 * @return <code>JTextField</code> com o nome do cargo.
	 */
	public JTextField getFieldNome() {
		return fieldNome;
	}

	/**
	 * Este metodo retorna uma referencia da tabela de cargos do Dialogo.
	 * 
	 * @return <code>JTable</code> com o conteudo da tabela.
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
	public JButton getBotaoSair() {
		return botaoSair;
	}

	/**
	 * Este metodo retorna uma referencia da opcao, neste inteiro fica armazenado a opcao do usuario para saber se o mesmo e
	 * consulta, alteracao ou exclusao de cargo.
	 * 
	 * @return <code>int</code> com a opcao do usuario.
	 */
	public int getOpcao() {
		return opcao;
	}
}
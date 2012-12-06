package com.sige.gui.votacao;

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

import com.sige.gui.ShadowBorder;
import com.sige.gui.votacao.eventos.TratadorEventosConsultaVotacao;

/**
 * Esta classe extende um <code>JDialog</code> e e cria uma interface grafica que permite a consulta de votacao.
 * 
 * @author Charles Garrocho
 */
public class DialogoConsultarVotacao extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField fieldCargo;
	private JTable tabelaVotacao;
	private JButton botaoLimpar;
	private JButton botaoSair;

	/**
	 * Este e o construtor. Ele constroi a interface grafica do dialogo consultar votacao.
	 */
	public DialogoConsultarVotacao() {

		super();
		getRootPane().setBorder(new ShadowBorder());
		setTitle("Consultar Votacao");
		TratadorEventosConsultaVotacao tratadorEventos = new TratadorEventosConsultaVotacao(this);

		JPanel painelNorte = new JPanel();
		
		fieldCargo = new JTextField(36);
		fieldCargo.setToolTipText("Descreva Aqui o Cargo do Cargo a Ser Pesquisado. Utilize o \'%\' Para Uma Melhor Pesquisa.");
		fieldCargo.addKeyListener(tratadorEventos);
		
		// Adiciona o labelCargo e o fieldCargo no painelNorte.
		painelNorte.add(new JLabel("Cargo"));
		painelNorte.add(fieldCargo);
		
		// Adiciona o painelNorte na regiao norte do Dialogo Consultar Votacao.
		add(painelNorte, BorderLayout.NORTH);

		String colunasTabela[] = {"Data", "Cargo"},
				dadosTabela[][] = new String[0][0];

		JPanel painelCentro = new JPanel();
		
		tabelaVotacao = new JTable(new DefaultTableModel(dadosTabela, colunasTabela));
		
		// Define as preferencias da tabela de votacoes.
		tabelaVotacao.setPreferredScrollableViewportSize(new Dimension(330, 200));
		tabelaVotacao.addMouseListener(tratadorEventos);
		tabelaVotacao.getColumn("Data").setPreferredWidth(20);
		tabelaVotacao.getColumn("Cargo").setPreferredWidth(200);
		tabelaVotacao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		
		// Cria uma barra de rolagem passando como argumento a tabela de votacoes.
		JScrollPane barraRolagem = new JScrollPane(tabelaVotacao);
		
		// Adiciona a barra de rolagem no painelCentro.
		painelCentro.add(barraRolagem);
		
		// Adiciona o painelCentro na regiao central do Dialogo Consultar Votacao.
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

		// Adiciona o painelSul na regiao sul do Dialogo Consultar Votacao.
		add(painelSul, BorderLayout.SOUTH);

		// Define as propriedades do dialogo.
		pack();
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icones/icone.png")));
		setLocationRelativeTo(null);
		setModal(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	/**
	 * Este metodo retorna uma referencia do Field Cargo do Dialogo.
	 * 
	 * @return <code>JTextField</code> com o nome do cargo.
	 */
	public JTextField getFieldCargo() {
		return fieldCargo;
	}

	/**
	 * Este metodo retorna uma referencia da tabela de votacoes do Dialogo.
	 * 
	 * @return <code>JTable</code> com o conteudo da tabela.
	 */
	public JTable getTabelaVotacao() {
		return tabelaVotacao;
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

}
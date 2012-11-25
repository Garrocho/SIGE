package com.sige.gui.partido;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.sige.gui.partido.eventos.TratadorEventosCadastroPartido;
import com.sige.gui.recursos.SomenteNumeros;
import com.sige.gui.recursos.TamanhoMaximo;

/**
 * Esta classe extende um <code>JDialog</code> e cria uma interface grafica que permite o usuario cadastrar um partido.
 * 
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class DialogoCadastrarPartido extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField fieldNumero;
	private JTextField fieldNome;
	private JTextField fieldSigla;
	private JButton botaoGravar, botaoCancelar, botaoAlterar, botaoExcluir;
	private int opcao;

	/**
	 * Este e o construtor. Ele constroi a interface grafica do dialogo cadastrar partido. Caso seja um cadastro, o usuario
	 * deve informar "-1" no campo "Numero". Caso contrario, seja alteracao, o usuario deve apenas passar as informacoes do
	 * partido a ser alterado.
	 * @param numero
	 * @param nome
	 * @param sigla
	 */
	public DialogoCadastrarPartido(int numero, String nome, String sigla) {
		TratadorEventosCadastroPartido tratadorEvento = new TratadorEventosCadastroPartido(this);

		// Verifica se o numero e igual a "-1", se sim define o titulo como Cadastro, se nao define como alteracao.
		opcao = numero;
		JPanel painelNorte = new JPanel();

		fieldNome = new JTextField(26);
		fieldNome.setDocument(new TamanhoMaximo(30));
		fieldNome.setToolTipText("Descreva Aqui o Nome Para o Novo Partido.");
		fieldNome.addActionListener(tratadorEvento);
		fieldNome.setText(nome);

		// Adiciona o LabelNome e o FieldNome ao painelNorte.
		painelNorte.add(new JLabel("Nome   "));
		painelNorte.add(fieldNome);

		// Adiciona o painelNorte na regiao norte do Dialogo Cadastrar Partido.
		add(painelNorte, BorderLayout.NORTH);

		JPanel painelCentro = new JPanel(new GridLayout(2,1));

		JPanel painelSigla = new JPanel();
		fieldSigla = new JTextField(16);
		fieldSigla.setDocument(new TamanhoMaximo(10));
		fieldSigla.setToolTipText("Descreva aqui a SIGLA Para o Novo Partido.");
		fieldSigla.addActionListener(tratadorEvento);
		fieldSigla.setText(sigla);

		// Adiciona o LabelSigla e o FieldSigla ao painelSigla.
		painelSigla.add(new JLabel("Sigla     "));
		painelSigla.add(fieldSigla);
		painelSigla.add(new JLabel("                         "));

		// Adiciona o painelSigla ao painelCentro.
		painelCentro.add(painelSigla);

		JPanel painelNumero = new JPanel();
		fieldNumero = new JTextField(10);
		fieldNumero.setDocument(new SomenteNumeros());
		fieldNumero.setToolTipText("Descreva Aqui o Numero Para o Novo Partido.");
		fieldNumero.addActionListener(tratadorEvento);
		if (numero != -1) 
			fieldNumero.setText(String.format("%d",numero));

		// Adiciona o LabelNumero e o fieldNumero ao painelNumero.
		painelNumero.add(new JLabel("Numero"));
		painelNumero.add(fieldNumero);
		painelNumero.add(new JLabel("                                        "));

		// Adiciona o painelNumero ao painelCentro.
		painelCentro.add(painelNumero);

		// Adiciona o painelCentro na regiao central do Dialogo Cadastrar Partido.
		add(painelCentro,BorderLayout.CENTER);

		JPanel painelSul = new JPanel();
		
		if (numero == -1) {
			setTitle("Cadastro de Partido");
			botaoGravar   = new JButton("GRAVAR", new ImageIcon(getClass().getResource("/icones/gravar.png")));
			botaoCancelar = new JButton("CANCELAR", new ImageIcon(getClass().getResource("/icones/cancelar.png")));
			
			botaoGravar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
			botaoCancelar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
			
			botaoGravar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
			botaoCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
			
			botaoGravar.addActionListener(tratadorEvento);
			botaoCancelar.addActionListener(tratadorEvento);

			// Adiciona os botoes(gravar, cancelar) no painelSul.
			painelSul.add(botaoGravar);
			painelSul.add(botaoCancelar);
		}
		else {
			setTitle("Alteracao de Partido");
			botaoAlterar   = new JButton("GRAVAR", new ImageIcon(getClass().getResource("/icones/gravar.png")));
			botaoExcluir = new JButton("Excluir", new ImageIcon(getClass().getResource("/icones/excluir.png")));
			
			botaoAlterar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
			botaoExcluir.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
			
			botaoAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
			botaoExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
			
			botaoAlterar.addActionListener(tratadorEvento);
			botaoExcluir.addActionListener(tratadorEvento);

			// Adiciona os botoes(Alterar, Excluir) no painelSul.
			painelSul.add(botaoAlterar);
			painelSul.add(botaoExcluir);
		}

		// Adiciona o painelSul na regiao sul do Dialogo Cadastrar Partido.
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
	 * Este metodo retorna uma referencia do Field Numero do Dialogo.
	 * 
	 * @return <code>JTextField</code> com o numero do partido.
	 */
	public JTextField getFieldNumero() {
		return fieldNumero;
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
	 * Este metodo retorna uma referencia do Field Sigla do Dialogo.
	 * 
	 * @return <code>JTextField</code> com a sigla do partido.
	 */
	public JTextField getFieldSigla() {
		return fieldSigla;
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
	 * Este metodo retorna uma referencia do Botao Cancelar do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao cancelar.
	 */	
	public JButton getBotaoCancelar() {
		return botaoCancelar;
	}
	
	/**
	 * Este metodo retorna uma referencia do Botao Alterar do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao alterar.
	 */	
	public JButton getBotaoAlterar() {
		return botaoAlterar;
	}
	
	/**
	 * Este metodo retorna uma referencia do Botao Excluir do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao excluir.
	 */	
	public JButton getBotaoExcluir() {
		return botaoExcluir;
	}

	/**
	 * Este metodo retorna uma referencia do opcao, neste inteiro fica armazenado a opcao que o usuario passou, para que o
	 * tratador de eventos saiba se e uma consulta, alteracao ou exclusao de partido.
	 * 
	 * @return <code>int</code> com a opcao de consulta de partido.
	 */
	public int getOpcao() {
		return opcao;
	}

}
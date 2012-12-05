package com.sige.gui.cargo;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.sige.gui.ShadowBorder;
import com.sige.gui.cargo.eventos.TratadorEventosCadastroCargo;
import com.sige.gui.recursos.SomenteNumeros;
import com.sige.gui.recursos.TamanhoMaximo;

/**
 * Esta classe extende um <code>JDialog</code> e cria uma interface grafica que permite o usuario cadastrar um cargo.
 * 
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class DialogoCadastrarCargo extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JTextField fieldNome;
	private JTextField fieldDigitos;
	private JButton botaoGravar, botaoCancelar, botaoExcluir, botaoAlterar;
	
	/**
	 * Este e o construtor. Ele constroi a interface grafica do dialogo cadastrar cargo.
	 * 
	 * @param id <code>int</code> com o numero do cargo a ser alterado.
	 * @param nome <code>String</code> com o nome do cargo a ser alterado.
	 * @param digitos <code>String</code> com os digitos do cargo a ser alterado.
	 */
	public DialogoCadastrarCargo(int id, String nome, String digitos) {
		
		super();
		getRootPane().setBorder(new ShadowBorder());
		TratadorEventosCadastroCargo tratadorEvento = new TratadorEventosCadastroCargo(this, id);

		JPanel painelNorte = new JPanel();
		fieldNome = new JTextField(27);
		fieldNome.setToolTipText("Descreva Aqui o Nome do Novo Cargo.");
		fieldNome.setDocument(new TamanhoMaximo(30));
		fieldNome.addActionListener(tratadorEvento);
		fieldNome.setText(nome);
		
		// Adiciona o LabelNome e o FieldNome ao painelNorte.
		painelNorte.add(new JLabel("Nome "));
		painelNorte.add(fieldNome);
		
		// Adiciona o painelNorte na regiao norte do Dialogo Cadastrar Cargo.
		add(painelNorte, BorderLayout.NORTH);
		
		JPanel painelCentro = new JPanel();
		fieldDigitos = new JTextField(6);
		fieldDigitos.setToolTipText("Descreva Aqui a Quantidade de Digitos Para Este Cargo.");
		fieldDigitos.setDocument(new SomenteNumeros());
		fieldDigitos.addActionListener(tratadorEvento);
		fieldDigitos.setText(digitos);
		
		// Adiciona o labelDigitos e o FieldDigitos ao painelCentro.
		painelCentro.add(new JLabel(" Digitos"));
		painelCentro.add(fieldDigitos);
		
		// Adiciona o painelCentro na regiao central do Dialogo Cadastrar Cargo.
		add(painelCentro, BorderLayout.WEST);
		
		JPanel painelSul = new JPanel();
		
		// Verifica se o numero e igual a "-1", se sim define o titulo como Cadastro, se nao define como alteracao.
		if (nome.length() <  3) {
			setTitle("Cadastro de Cargo");
			
			botaoGravar   = new JButton("GRAVAR", new ImageIcon(getClass().getResource("/icones/gravar.png")));
			botaoCancelar = new JButton("CANCELAR", new ImageIcon(getClass().getResource("/icones/cancelar.png")));
			
			botaoGravar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
			botaoCancelar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
			
			botaoGravar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
			botaoCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
			
			botaoGravar.addActionListener(tratadorEvento);
			botaoCancelar.addActionListener(tratadorEvento);
			
			// Adiciona os botoes(gravar, cancelar) ao painelSul.
			painelSul.add(botaoGravar);
			painelSul.add(botaoCancelar);
		}
		else {
			setTitle("Alteracao de Cargo");
			
			botaoAlterar   = new JButton("GRAVAR", new ImageIcon(getClass().getResource("/icones/gravar.png")));
			botaoCancelar = new JButton("CANCELAR", new ImageIcon(getClass().getResource("/icones/cancelar.png")));
			
			botaoAlterar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
			botaoCancelar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
			
			botaoAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
			botaoCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
			
			botaoAlterar.addActionListener(tratadorEvento);
			botaoCancelar.addActionListener(tratadorEvento);
			
			// Adiciona os botoes(gravar, cancelar) ao painelSul.
			painelSul.add(botaoAlterar);
			painelSul.add(botaoCancelar);
		}
		
		// Adiciona o painelSul na regiao sul do Dialogo Cadastrar Cargo.
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
	 * Este metodo retorna uma referencia do Field Nome do Dialogo.
	 * 
	 * @return <code>JTextField</code> com o nome do cargo.
	 */
	public JTextField getFieldNome() {
		return fieldNome;
	}
	
	/**
	 * Este metodo retorna uma referencia do Field Digitos do Dialogo.
	 * 
	 * @return <code>JTextField</code> com o digitos do candidato.
	 */
	public JTextField getFieldDigitos() {
		return fieldDigitos;
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
	 * Este metodo retorna uma referencia do Botao Excluir do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao excluir.
	 */	
	public JButton getBotaoExcluir() {
		return botaoExcluir;
	}
	
	/**
	 * Este metodo retorna uma referencia do Botao Alterar do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao alterar.
	 */	
	public JButton getBotaoAlterar() {
		return botaoAlterar;
	}
}
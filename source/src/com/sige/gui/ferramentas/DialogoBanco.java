package com.sige.gui.ferramentas;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import com.sige.gui.ferramentas.eventos.TratadorEventosBancoDados;

/**
 * Esta classe extende um <code>JDialog</code> e cria uma interface grafica para o dialogo banco de dados.
 * 
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class DialogoBanco extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextArea areaInformacoes;
	private JButton botaoPopular;
	private JButton botaoLimpar;

	/**
	 * Este e o construtor. Ele constroi a interface grafica do dialogo banco de dados.
	 */
	public DialogoBanco() {

		super();
		setTitle("Banco de Dados");
		TratadorEventosBancoDados tratadorEventos = new TratadorEventosBancoDados(this);

		JPanel painelCentro = new JPanel();
		areaInformacoes = new JTextArea(8,30);
		areaInformacoes.setEnabled(false);

		// Adicionando a area de texto com informacoes na barra de rolagem.
		JScrollPane barraRolagem = new JScrollPane(areaInformacoes);

		// Adiciona a barra de rolagem no painelCentro.
		painelCentro.add(barraRolagem);

		// Adiciona o painelCentro na regiao central do Dialogo Banco de Dados.
		add(painelCentro, BorderLayout.CENTER);

		JPanel painelSul = new JPanel();
		botaoLimpar  = new JButton("LIMPAR", new ImageIcon(getClass().getResource("/icones/limpar.png")));
		botaoPopular = new JButton("POPULAR", new ImageIcon(getClass().getResource("/icones/popular.png")));

		botaoLimpar.addActionListener(tratadorEventos);
		botaoPopular.addActionListener(tratadorEventos);

		botaoLimpar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botaoPopular.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		botaoLimpar.setFont(new Font("Arial", Font.BOLD, 14));
		botaoPopular.setFont(new Font("Arial", Font.BOLD, 14));

		// Adiciona os botoes(popular, limpar) no painelSul.
		painelSul.add(botaoPopular);
		painelSul.add(botaoLimpar);

		// Adiciona o painelSul na regiao sul do Dialogo Banco de Dados.
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
	 * Este metodo retorna uma referencia da area de informacoes do Dialogo.
	 * 
	 * @return <code>JTextArea</code> com a area de informacoes.
	 */	
	public JTextArea getAreaInformacoes() {
		return areaInformacoes;
	}


	/**
	 * Este metodo retorna uma referencia do Botao Popular do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao popular.
	 */	
	public JButton getBotaoPopular() {
		return botaoPopular;
	}

	/**
	 * Este metodo retorna uma referencia do Botao Limpar do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao limpar.
	 */	
	public JButton getBotaoLimpar() {
		return botaoLimpar;
	}
}

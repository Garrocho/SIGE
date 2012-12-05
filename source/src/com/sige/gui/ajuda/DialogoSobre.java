package com.sige.gui.ajuda;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.sige.gui.ShadowBorder;

/**
 * Esta classe extende um <code>JDialog</code> e cria uma interface grafica com as informacoes do software e equipe.
 * 
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class DialogoSobre extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Este e o construtor. Ele constroi a interface grafica do dialogo sobre.
	 */
	public DialogoSobre() {
		
		super();
		setTitle("Sobre o SIGE");
		getRootPane().setBorder(new ShadowBorder());
		
		// Cria um painel de abas. 
		JTabbedPane tabbedPane = new JTabbedPane();
		
		// Cria o conteúdo da primeira aba e coloca no painel 1.  
		JPanel painel1 = new JPanel(new GridLayout(1,2));
		JPanel painel11 = new JPanel();
		Image img = Toolkit.getDefaultToolkit().getImage((getClass().getResource("/icones/logo.png")));  
		painel11.add(new JLabel(new ImageIcon(img), SwingConstants.LEFT));
		
		JPanel painel12 = new JPanel(new GridLayout(3,1));
		painel12.add(new JLabel("Sistema Integrado de Gestao Eleitoral", SwingConstants.CENTER));
		painel12.add(new JLabel("Versao: 1.0", SwingConstants.CENTER));
		painel12.add(new JLabel("Copyright(c), BUILD SYSTEM", SwingConstants.CENTER));
		painel1.add(painel11);
		painel1.add(painel12);
		
		// Adiciona o conteúdo do painel 1 na primeira aba do TabbedPane.
		tabbedPane.addTab("Sobre", null, painel1, "Informacoes do software.");
		
		// Cria o conteúdo da segunda aba e coloca no painel 2.  
		JPanel painel2 = new JPanel();
		JPanel painel3 = new JPanel();
		painel3.add(new JLabel("Desenvolvedores: Barbara Silverio, Charles Tim Batista Garrocho"));
		painel2.add(new JLabel("Curso Superior de Tecnologia Em Sistemas Para Internet."), BorderLayout.NORTH);
		painel2.add(painel3, BorderLayout.SOUTH);
		
		// Adiciona o conteúdo do painel 2 na segunda aba do TabbedPane.
		tabbedPane.addTab("Creditos", null, painel2, "Equipe de desenvolvimento.");

		// Adiciona o tabbedPane ao container da janela.
		add(tabbedPane);

		// Define as propriedades do dialogo.
		setSize(610, 120);
		setIconImage(Toolkit.getDefaultToolkit().getImage((getClass().getResource("/icones/icone.png"))));
		setLocationRelativeTo(null);
		setModal(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	} // construtor
} // class GUISobre

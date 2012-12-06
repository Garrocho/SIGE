package com.sige.gui;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.sige.gui.ajuda.MenuAjuda;
import com.sige.gui.ferramentas.MenuFerramentas;
import com.sige.gui.pesquisa.MenuPesquisa;
import com.sige.gui.votacao.MenuVotacao;

/**
 * Esta e a classe que contem a janela principal do programa. Ela e constituida de um conjunto de menus que sao os modulos do
 * sistema.
 * 
 * @author Charles Garrocho
 */
public class JanelaPrincipal extends Janela {

	private static final long serialVersionUID = 1L;
	private JMenuBar barraMenu;
	private JMenuItem menuSair;

	/**
	 * Este e o construtor. Ele cria os menus e adiciona a janela a barra de menus contendo os modulos do sistema.
	 */
	public JanelaPrincipal() {
		super();
		barraMenu = new JMenuBar();

		new MenuCadastro(this);
		new MenuListagem(this);
		new MenuPesquisa(this);
		new MenuVotacao(this);
		new MenuFerramentas(this);
		new MenuAjuda(this);

		menuSair  = new JMenuItem("Sair");
		menuSair.setMnemonic(KeyEvent.VK_F4);
		menuSair.setIcon(new ImageIcon(getClass().getResource("/icones/sair.png")));
		menuSair.setFont(new Font("Arial", Font.BOLD, 12));
		menuSair.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		menuSair.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		barraMenu.add(menuSair);
		setJMenuBar(barraMenu);

		// ADICIONA UM ICONE A JANELA.
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icones/icone.png")));

		// ADICIONA UMA IMAGEM "LOGO" A JANELA PRINCIPAL DO PROGRAMA.
		JPanel painelLogo = new JPanel(new GridBagLayout());
		GridBagConstraints inserts = new GridBagConstraints();
		inserts.gridx = 0;
		inserts.gridy = 0;
		JLabel labelLogo  = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icones/TSE.png"))));
		JLabel labelHelp  = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icones/descricao.png"))));
		painelLogo.add(labelLogo, inserts);
		inserts.gridx = 0;
		inserts.gridy = 1;
		painelLogo.add(labelHelp, inserts);
		add(painelLogo);

		addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
		});

		// Define as propriedades do dialogo.
		setTitle("SIGE - Sistema Integrado de Gestao Eleitoral");
		setSize(720,650);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Retorna uma referencia da Barra de Menu.
	 * 
	 * @return um <code>JMenuBar</code> com a Barra de Menu.
	 */
	public JMenuBar getBarraMenu() {
		return barraMenu;
	}
}
package com.sige.gui.ajuda;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.sige.gui.JanelaPrincipal;

/**
 * Esta classe extende um <code>JMenu</code> e cria o menu ajuda.
 * 
 * @author Charles Garrocho
 */
public class MenuAjuda extends JMenu {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Este e o construtor. Ele constroi o menu "Ajuda" e o adiciona na barra de menus na <code>JanelaPrincipal</code>.
	 * 
	 * @param janelaPrincipal <code>JFrame</code> Janela principal da aplicacao, ela e utilizada para adicionar o menu Ajuda.
	 */
	public MenuAjuda(JanelaPrincipal janelaPrincipal) {
		
		super("Ajuda");
		setFont(new Font("Arial", Font.BOLD, 12));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setIcon(new ImageIcon(getClass().getResource("/icones/AJUDA.png")));
		setMnemonic(KeyEvent.VK_A);
		
		JMenuItem menuItemSobre = new JMenuItem("Sobre");
		menuItemSobre.setFont(new Font("Arial", Font.BOLD, 12));
		menuItemSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		menuItemSobre.setMnemonic(KeyEvent.VK_S);
		
		// Adicionando um tratamento de evento para o menu item sobre.
		menuItemSobre.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				new DialogoSobre();
			}
		});
		
		// Adiciona o menu iten sobre ao menu ajuda.
		add(menuItemSobre);
		
		// Adiciona o menu ajuda a barra de menus da janela principal.
		janelaPrincipal.getBarraMenu().add(this);
	}

} // classe Menu Ajuda.

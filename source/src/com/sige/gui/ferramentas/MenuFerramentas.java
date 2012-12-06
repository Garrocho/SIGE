package com.sige.gui.ferramentas;

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
 * Esta classe extende um <code>JMenu</code> e cria o menu ferramentas.
 * 
 * @author Charles Garrocho
 */
public class MenuFerramentas extends JMenu {

	private static final long serialVersionUID = 1L;
	private JMenuItem menuItemBancoDados;

	/**
	 * Este e o construtor. Ele constroi o menu "Ferramentas" e o adiciona na barra de menus na <code>JanelaPrincipal</code>.
	 * 
	 * @param janelaPrincipal <code>JFrame</code> Janela principal da aplicacao, ela e utilizada para adicionar o menu Ferramentas.
	 */
	public MenuFerramentas(JanelaPrincipal janelaPrincipal) {
		
		super("Ferramentas");
		setFont(new Font("Arial", Font.BOLD, 12));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setIcon(new ImageIcon(getClass().getResource("/icones/FERRAMENTA.png")));
		setMnemonic(KeyEvent.VK_F);
		
		// Instancia novos menu itens para adicionar ao menu Ferramentas.
		JMenuItem menuItemBancoDados = new JMenuItem("Banco Dados");
		menuItemBancoDados.setFont(new Font("Arial", Font.BOLD, 12));
		menuItemBancoDados.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		menuItemBancoDados.setMnemonic(KeyEvent.VK_B);
		
		// Cria e registra o tratador de evento do menu item banco de dados.
		menuItemBancoDados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new DialogoBanco();
			}
		});
		
		// Adiciona o menu item banco de dados ao menu Ferramentas. E adiciona o menu Aparencia ao menu Ferramentas.
	    add(menuItemBancoDados);
		
 		// Adiciona o Menu Ferramentas a barra de menus da janela principal.
		janelaPrincipal.getBarraMenu().add(this);		
	}

	/**
	 * Este metodo retorna a referencia do menu item "banco de dados" do menu Ferramentas.
	 * 
	 * @return <code>JMenuItem</code> Um Menu Item Banco de Dados.
	 */
	public JMenuItem getMenuItemBancoDados() {
		return menuItemBancoDados;
	}

} // classe Menu Ferramentas.

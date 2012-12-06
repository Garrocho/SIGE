package com.sige.gui.pesquisa;

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
 * Esta classe extende um <code>JMenu</code> e cria o menu Pesquisa.
 * 
 * @author Charles Garrocho
 */
public class MenuPesquisa extends JMenu {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Este e o construtor. Ele constroi o menu "Pesquisa" e o adiciona na barra de menus na <code>JanelaPrincipal</code>.
	 * 
	 * @param janelaPrincipal <code>JFrame</code> Janela principal da aplicacao, ela e utilizada para adicionar o menu Pesquisa.
	 */
	public MenuPesquisa(JanelaPrincipal janelaPrincipal) {
		
		super("Pesquisa Eleitoral");
		setFont(new Font("Arial", Font.BOLD, 12));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setIcon(new ImageIcon(getClass().getResource("/icones/grafico.png")));
		setMnemonic(KeyEvent.VK_P);
		
		// Instancia novos menu itens para adicionar ao menu Pesquisa.
		JMenuItem menuItemCadastro = new JMenuItem("Cadastro");
		menuItemCadastro.setMnemonic(KeyEvent.VK_C);
		JMenuItem menuItemConsulta = new JMenuItem("Listagem");
		menuItemConsulta.setMnemonic(KeyEvent.VK_L);
		JMenuItem menuItemRelatorio = new JMenuItem("Relatorio");
		menuItemRelatorio.setMnemonic(KeyEvent.VK_R);
		
		menuItemCadastro.setFont(new Font("Arial", Font.BOLD, 12));
		menuItemCadastro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		menuItemConsulta.setFont(new Font("Arial", Font.BOLD, 12));
		menuItemConsulta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		menuItemRelatorio.setFont(new Font("Arial", Font.BOLD, 12));
		menuItemRelatorio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		// Cria e registra o tratador de eventos dos menu itens.
		menuItemCadastro.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				new DialogoCadastrarPesquisa();
			}
		});
		menuItemConsulta.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				new DialogoConsultarPesquisa();
			}
		});
		menuItemRelatorio.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				new DialogoRelatorioPesquisa();
			}
		});
		
		// Adiciona os menu itens ao menu Pesquisa.
		add(menuItemCadastro);
		add(menuItemConsulta);
		add(menuItemRelatorio);
		
		// Adiciona o menu Pesquisa a barra de menus da janela principal.
		janelaPrincipal.getBarraMenu().add(this);
	}
	
} // classe Menu Pesquisa.

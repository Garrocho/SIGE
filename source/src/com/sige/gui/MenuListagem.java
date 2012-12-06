package com.sige.gui;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.sige.gui.candidato.DialogoConsultarCandidato;
import com.sige.gui.cargo.DialogoConsultarCargo;
import com.sige.gui.partido.DialogoConsultarPartido;

/**
 * Esta classe extende um <code>JMenu</code> e cria o menu Candidato.
 * 
 * @author Charles Garrocho
 */
public class MenuListagem extends JMenu {
	
	private JanelaPrincipal janelaPrincipal;

	private static final long serialVersionUID = 1L;
	
	/**
	 * Este e o construtor. Ele constroi o menu "Candidato" e o adiciona na barra de menus na <code>JanelaPrincipal</code>.
	 * 
	 * @param janelaPrincipal <code>JFrame</code> Janela principal da aplicacao, ela e utilizada para adicionar o menu Candidato.
	 */
	public MenuListagem(JanelaPrincipal janelaPrincipal) {
		
		super("Listagem");
		this.janelaPrincipal = janelaPrincipal;
		setFont(new Font("Arial", Font.BOLD, 12));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setIcon(new ImageIcon(getClass().getResource("/icones/MOVIMENTO.png")));
		setMnemonic(KeyEvent.VK_L);

		// Instancia novos menu itens para adicionar ao menu Candidato.
		JMenuItem menuItemListagemCandidato = new JMenuItem("Candidato");
		JMenuItem menuItemListagemCargo = new JMenuItem("Cargo");
		JMenuItem menuItemListagemPartido   = new JMenuItem("Partido"); 
		
		menuItemListagemCandidato.setMnemonic(KeyEvent.VK_C);
		menuItemListagemCargo.setMnemonic(KeyEvent.VK_A);
		menuItemListagemPartido.setMnemonic(KeyEvent.VK_P);
		
		menuItemListagemCandidato.setFont(new Font("Arial", Font.BOLD, 12));
		menuItemListagemCandidato.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		menuItemListagemCargo.setFont(new Font("Arial", Font.BOLD, 12));
		menuItemListagemCargo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		menuItemListagemPartido.setFont(new Font("Arial", Font.BOLD, 12));
		menuItemListagemPartido.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		// Adiciona os menu itens ao menu candidato.
	    add(menuItemListagemCandidato);
		add(menuItemListagemCargo);
		add(menuItemListagemPartido);
		setVisible(true);
		
		// Adiciona o menu candidato a barra de menus da janela principal.
		janelaPrincipal.getBarraMenu().add(this);		
		
		// Cria e registra o tratador de eventos dos botoes do dialogo.
		menuItemListagemCandidato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				new DialogoConsultarCandidato(getJanelaPrincipal());
			}
		});
		menuItemListagemCargo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				new DialogoConsultarCargo(1, "Consulta de Cargo");
			}
		});
		menuItemListagemPartido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				new DialogoConsultarPartido(1, "Consulta de Partido");
			}
		});
	}

	/**
	 * @return uma <JanelaPrincipal> com a referência da janela Pai.
	 */
	public JanelaPrincipal getJanelaPrincipal() {
		return janelaPrincipal;
	}
	
} // classe Menu Listagem.
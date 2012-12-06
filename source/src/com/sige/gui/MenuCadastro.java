package com.sige.gui;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.sige.gui.candidato.DialogoCadastrarCandidato;
import com.sige.gui.cargo.DialogoCadastrarCargo;
import com.sige.gui.partido.DialogoCadastrarPartido;

/**
 * Esta classe extende um <code>JMenu</code> e cria o menu Candidato.
 * 
 * @author Charles Garrocho
 */
public class MenuCadastro extends JMenu {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Este e o construtor. Ele constroi o menu "Candidato" e o adiciona na barra de menus na <code>JanelaPrincipal</code>.
	 * 
	 * @param janelaPrincipal <code>JFrame</code> Janela principal da aplicacao, ela e utilizada para adicionar o menu Candidato.
	 */
	public MenuCadastro(JanelaPrincipal janelaPrincipal) {
		
		super("Cadastro");
		setFont(new Font("Arial", Font.BOLD, 12));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setIcon(new ImageIcon(getClass().getResource("/icones/cliente.png")));
		setMnemonic(KeyEvent.VK_C);

		// Instancia novos menu itens para adicionar ao menu Candidato.
		JMenuItem menuItemCadastroCandidato = new JMenuItem("Candidato");
		JMenuItem menuItemCadastroCargo = new JMenuItem("Cargo");
		JMenuItem menuItemCadastroPartido   = new JMenuItem("Partido"); 
		
		menuItemCadastroCandidato.setMnemonic(KeyEvent.VK_C);
		menuItemCadastroCargo.setMnemonic(KeyEvent.VK_A);
		menuItemCadastroPartido.setMnemonic(KeyEvent.VK_P);
		
		menuItemCadastroCandidato.setFont(new Font("Arial", Font.BOLD, 12));
		menuItemCadastroCandidato.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		menuItemCadastroCargo.setFont(new Font("Arial", Font.BOLD, 12));
		menuItemCadastroCargo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		menuItemCadastroPartido.setFont(new Font("Arial", Font.BOLD, 12));
		menuItemCadastroPartido.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		// Adiciona os menu itens ao menu candidato.
	    add(menuItemCadastroCandidato);
		add(menuItemCadastroCargo);
		add(menuItemCadastroPartido);
		setVisible(true);
		
		// Adiciona o menu candidato a barra de menus da janela principal.
		janelaPrincipal.getBarraMenu().add(this);		
		
		// Cria e registra o tratador de eventos dos botoes do dialogo.
		menuItemCadastroCandidato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				new DialogoCadastrarCandidato("-1","","","","");
			}
		});
		menuItemCadastroCargo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				new DialogoCadastrarCargo(-1,"","");
			}
		});
		menuItemCadastroPartido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				new DialogoCadastrarPartido(-1,"","");
			}
		});
	}
} // classe Menu Cadastro.
package com.sige.gui.votacao;

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
 * Esta classe extende um <code>JMenu</code> e e cria o menu Votacao.
 * 
 * @author Charles Garrocho
 */
public class MenuVotacao extends JMenu {

	private static final long serialVersionUID = 1L;

	/**
	 * Este e o construtor. Ele constroi o menu "Votacao" e o adiciona na barra de menus na <code>JanelaPrincipal</code>.
	 * 
	 * @param janelaPrincipal <code>JFrame</code> Janela principal da aplicacao, ela e utilizada para adicionar o menu Votacao.
	 */
	public MenuVotacao(JanelaPrincipal janelaPrincipal) {

		super("Votacao");
		setFont(new Font("Arial", Font.BOLD, 12));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setIcon(new ImageIcon(getClass().getResource("/icones/VOTACAO.png")));
		setMnemonic(KeyEvent.VK_V);

		// Instancia novos menu itens para adicionar ao menu Votacao.
		JMenuItem menuItemCadastro = new JMenuItem("Cadastro");
		JMenuItem menuItemIniciar  = new JMenuItem("Urna Eletronica");
		JMenuItem menuApuracao     = new JMenuItem("Apuracao");
		
		menuItemCadastro.setMnemonic(KeyEvent.VK_C);
		menuItemIniciar.setMnemonic(KeyEvent.VK_U);
		menuApuracao.setMnemonic(KeyEvent.VK_A);
		
		menuItemCadastro.setFont(new Font("Arial", Font.BOLD, 12));
		menuItemCadastro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		menuItemIniciar.setFont(new Font("Arial", Font.BOLD, 12));
		menuItemIniciar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		menuApuracao.setFont(new Font("Arial", Font.BOLD, 12));
		menuApuracao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// Cria e registra o tratador de eventos dos menu itens.
		menuItemCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				new DialogoCadastrarVotocao();
			}
		});
		menuItemIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				new DialogoUrnaEletronica();
			}
		});
		menuApuracao.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				new DialogoConsultarVotacao();
			}
		});

		// Adiciona os menu itens ao menu Votacao.
		add(menuItemCadastro);
		add(menuItemIniciar);
		add(menuApuracao);

		// Adiciona o menu Votacao a barra de menus da janela principal.
		janelaPrincipal.getBarraMenu().add(this);
	}

} // classe Menu Votacao.
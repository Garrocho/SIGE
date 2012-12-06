package com.sige.gui.pesquisa;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.alee.extended.date.WebDateField;
import com.sige.gui.ShadowBorder;
import com.sige.gui.lookup.LookUpCargo;
import com.sige.gui.pesquisa.eventos.TratadorEventosRelatorioPesquisa;

/**
 * Esta classe extende um <code>JDialog</code> e cria uma interface grafica que permite o usuario fonecer um cargo e um
 * periodo para gerar um grafico de linha com os dados das pesquisas.
 * 
 * @author Charles Garrocho
 */
public class DialogoRelatorioPesquisa extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField cargoField;
	private JButton botaoExibir, botaoSair;
	private WebDateField dataInicioCalendario, dataFimCalendario;

	/**
	 * Este e o construtor. Ele constroi a interface grafica do dialogo Relatorio Pesquisa.
	 */
	public DialogoRelatorioPesquisa() {
		
		super();
		getRootPane().setBorder(new ShadowBorder());
		setTitle("Relatorio de Pesquisa Eleitoral");
		
		TratadorEventosRelatorioPesquisa tratadorEventos = new TratadorEventosRelatorioPesquisa(this);

		// Define o painelNorte como GridLayout com 3 linhas e 1 coluna.
		JPanel painelNorte = new JPanel(new GridLayout(3,1));

		JPanel painelCargo = new JPanel();
		cargoField = new JTextField(17);
		cargoField.setEditable(false);
		JPanel painelBotaoCargo = new JPanel();
		JButton botaoPesquisaCargo = new JButton();
		botaoPesquisaCargo.setIcon(new ImageIcon(getClass().getResource("/icones/pesquisar.png")));
		botaoPesquisaCargo.setPreferredSize(new Dimension(30,30));
		botaoPesquisaCargo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botaoPesquisaCargo.setToolTipText("Clique aqui para selecionar um cargo.");
		
		// Adiciona o cargoField e o botaoPesquisaCargo ao painelBotaoCargo.
		painelBotaoCargo.add(cargoField);
		painelBotaoCargo.add(botaoPesquisaCargo);
		
		// Adiciona o labelCargo e o painelBotaoCargo ao painelCargo.
		painelCargo.add(new JLabel("Cargo"));
		painelCargo.add(painelBotaoCargo);
		
		// Adiciona um tratador de Evento ao botaoPesquisaCargo. 
		botaoPesquisaCargo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new LookUpCargo(getThis(),getThis().getCargoField());
			}
		});
		
		// Adiciona o painelCargo na primeira linha do painelNorte.
		painelNorte.add(painelCargo);

		JPanel painelDataInicio = new JPanel();
		dataInicioCalendario = new WebDateField();
		
		// Adiciona o labelDataInicio e o dataInicioCalendario no painelDataInicio.
		painelDataInicio.add(new JLabel(" Data Inicio"));
		painelDataInicio.add(dataInicioCalendario);
		painelDataInicio.add(new JLabel("                       "));
		
		// Adiciona o painelDataInicio na segunda linha do painelNorte.
		painelNorte.add(painelDataInicio);

		JPanel painelDataFim = new JPanel();
		dataFimCalendario = new WebDateField();
		
		// Adiciona o labelDataFim e o dataFimCalendario no painelDataFim.
		painelDataFim.add(new JLabel("Data Fim   "));
		painelDataFim.add(dataFimCalendario);
		painelDataFim.add(new JLabel("                      "));
		
		// Adiciona o painelDataFim na terceira linha do painelNorte.
		painelNorte.add(painelDataFim);

		// Adiciona o painelNorte na regiao norte do Dialogo Relario Pesquisa.
		add(painelNorte, BorderLayout.NORTH);

		JPanel painelSul = new JPanel();

		botaoExibir  = new JButton("EXIBIR", new ImageIcon(getClass().getResource("/icones/exibir.png")));
		botaoExibir.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		botaoExibir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botaoExibir.addActionListener(tratadorEventos);
		
		botaoSair  = new JButton("SAIR", new ImageIcon(getClass().getResource("/icones/sair2.png")));
		botaoSair.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		botaoSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botaoSair.addActionListener(tratadorEventos);
		
		// Adiciona o botaoExibir ao painelSul.
		painelSul.add(botaoExibir);
		painelSul.add(botaoSair);

		// Adiciona o painelSul na regiao sul do Dialogo Relario Pesquisa.
		add(painelSul,BorderLayout.SOUTH);

		// Define as propriedades do Dialogo.
		pack();
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icones/icone.png")));
		setLocationRelativeTo(null);
		setModal(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	/**
	 * Retorna a referencia da propria classe.
	 * 
	 * @return um <code>DialogoRelatorioPesquisa</code> com a referencia da classe.
	 */
	public DialogoRelatorioPesquisa getThis() {
		return this;
	}

	/**
	 * Este metodo retorna uma referencia do Field Cargo do Dialogo.
	 * 
	 * @return <code>JTextField</code> com o nome do cargo.
	 */
	public JTextField getCargoField() {
		return cargoField;
	}

	/**
	 * Este metodo retorna uma referencia do Botao Exibir do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao exibir.
	 */
	public JButton getBotaoExibir() {
		return botaoExibir;
	}
	
	/**
	 * Este metodo retorna uma referencia do Botao Sair do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao sair.
	 */
	public JButton getBotaoSair() {
		return botaoSair;
	}

	/**
	 * Este metodo retorna uma referencia da Data Inicio da Pesquisa.
	 * 
	 * @return <code>JXDatePicker</code> com a dataInicioCalendario.
	 */
	public WebDateField getDataInicioCalendario() {
		return dataInicioCalendario;
	}

	/**
	 * Este metodo retorna uma referencia da Data Fim da Pesquisa.
	 * 
	 * @return <code>JXDatePicker</code> com a dataFimCalendario.
	 */
	public WebDateField getDataFimCalendario() {
		return dataFimCalendario;
	}
}
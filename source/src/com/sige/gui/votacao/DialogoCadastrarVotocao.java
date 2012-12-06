package com.sige.gui.votacao;

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
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import com.alee.extended.date.WebDateField;
import com.sige.gui.ShadowBorder;
import com.sige.gui.lookup.LookUpCargo;
import com.sige.gui.votacao.eventos.TratadorEventosCadastrarVotacao;

/**
 * Esta classe extende um <code>JDialog</code> e cria uma interface grafica que permite o usuario cadastrar uma votacao.
 * 
 * @author Charles Garrocho
 */
public class DialogoCadastrarVotocao extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JFormattedTextField horaInicio;
	private JFormattedTextField horaFim;
	private WebDateField data;
	private JButton botaoGravar, botaoCancelar, botaoLimpar;
	private JTable tabelaCargo;
	
	/**
	 * 	Este e o construtor. Ele constroi a interface grafica do dialogo cadastrar votacao. 
	 */
	public DialogoCadastrarVotocao() {
		
		super();
		getRootPane().setBorder(new ShadowBorder());
		setTitle("Cadastro de Votacao");
		TratadorEventosCadastrarVotacao tratadorEventos = new TratadorEventosCadastrarVotacao(this);
		
		MaskFormatter mascaraHora = null;
		
		// Cria uma mascara que ira aceitar apenas dois numeros inteiros.
		try {
			mascaraHora = new MaskFormatter("##");
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		
		JPanel painelNorte = new JPanel();
		
		// Define o painelData como GridLayout com 2 linhas e 1 coluna.
		JPanel painelData = new JPanel(new GridLayout(2,1));
		data = new WebDateField();
		((JTextField)data.getComponent(0)).setEditable(false);
		
		// Adiciona o labelData na primeira linha e a data na segunda linha do painelData.
		painelData.add(new JLabel(" Data"));
		painelData.add(data);
		
		// Adicionado o painelData ao painelNorte.
		painelNorte.add(painelData);
		
		// Define o painelHoraInicio como GridLayout com 2 linhas e 1 coluna.
		JPanel painelHoraInicio = new JPanel(new GridLayout(2,1));
		horaInicio = new JFormattedTextField(mascaraHora);
		horaInicio.setSize(40, 40);
		
		// Adiciona o labelHoraInicio na primeira linha e o Field HoraInicio na segunda linha do painelHoraInicio.
		painelHoraInicio.add(new JLabel(" Hora Inicio  "));
		painelHoraInicio.add(horaInicio);
		
		// Adiciona o painelHoraInicio ao painelNorte.
		painelNorte.add(painelHoraInicio);
		
		// Define o painelHoraFim como GridLayout com 2 linhas e 1 coluna.
		JPanel painelHoraFim = new JPanel(new GridLayout(2,0));
		horaFim = new JFormattedTextField(mascaraHora);
		
		// Adiciona o labelHoraFim na primeira linha e o Field HoraFim na segunda linha do painelHoraFim.
		painelHoraFim.add(new JLabel(" Hora Fim       "));
		painelHoraFim.add(horaFim);
		
		// Adiciona o painelAuxHora ao painelNorte.
		painelNorte.add(painelHoraFim);
		
		JPanel painelCargo = new JPanel();
		JButton botaoPesquisaCargo = new JButton("Cargo");
		botaoPesquisaCargo.setIcon(new ImageIcon(getClass().getResource("/icones/mais.png")));
		botaoPesquisaCargo.setToolTipText("Clique aqui para selecionar um cargo.");
		botaoPesquisaCargo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		
		// Adiciona o labelCargo na primeira linha e o Field cargo na segunda linha do painelCargo.
		painelCargo.add(botaoPesquisaCargo);
		
		// Adiciona um tratamento de evento no botaoPesquisaCargo.
		botaoPesquisaCargo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new LookUpCargo(getThis(), getTabelaCargo());
			}
		});
		
		// adiciona o painelCargo na primeira linha do painelSul.
		painelNorte.add(painelCargo);
		
		// Adiciona o painelNorte na regiao norte do Dialogo Cadastrar Votacao.
		add(painelNorte, BorderLayout.NORTH);
		
		JPanel painelCentro = new JPanel();
		
		String colunasTabela[] = {"ID","DESCRICAO DO CARGO","DIGITOS"},
	           dadosTabela[][] = new String[0][0];
		
		tabelaCargo = new JTable(new DefaultTableModel(dadosTabela, colunasTabela));
		
		// Define as preferencias da tabela de cargos.
		tabelaCargo.setPreferredScrollableViewportSize(new Dimension(360, 100));
		tabelaCargo.getColumn("ID").setPreferredWidth(4);
		tabelaCargo.getColumn("DESCRICAO DO CARGO").setPreferredWidth(186);
		tabelaCargo.getColumn("DIGITOS").setPreferredWidth(35);
		tabelaCargo.addMouseListener(tratadorEventos);
		tabelaCargo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		
		// cria uma barra de rolagem passando como argumento a tabela de cargos.
		JScrollPane barraRolagem = new JScrollPane(tabelaCargo);
		
		// Adiciona a barra de rolagem no painelCentro.
    	painelCentro.add(barraRolagem);
    	
    	// Adiciona o painelCentro na regiao central do Dialogo Cadastrar Votacao.
		add(painelCentro, BorderLayout.CENTER);
		
		// Define o painelSul como GridLayout com 2 linhas e 1 coluna.
		JPanel painelSul = new JPanel();
		
		JPanel painelBotoes = new JPanel();
		botaoGravar   = new JButton("GRAVAR", new ImageIcon(getClass().getResource("/icones/gravar.png")));
		botaoLimpar   = new JButton("LIMPAR", new ImageIcon(getClass().getResource("/icones/limpar.png")));
		botaoCancelar = new JButton("CANCELAR", new ImageIcon(getClass().getResource("/icones/cancelar.png")));
		
		botaoGravar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		botaoLimpar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		botaoCancelar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		
		botaoGravar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botaoLimpar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botaoCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		
		botaoGravar.addActionListener(tratadorEventos);
		botaoLimpar.addActionListener(tratadorEventos);
		botaoCancelar.addActionListener(tratadorEventos);
		
		// Adiciona os botoes(gravar, limpar, cancelar) no painelBotoes.
		painelBotoes.add(botaoGravar);
		painelBotoes.add(botaoLimpar);
		painelBotoes.add(botaoCancelar);
		
		// Adiciona o painelBotoes na segunda linha do painelSul.
		painelSul.add(painelBotoes);
		
		// Adiciona o painelSul na regiao sul do Dialogo Cadastrar Votacao.
		add(painelSul, BorderLayout.SOUTH);
		
		// Define as propriedades do Dialgo.
		pack();
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icones/icone.png")));
		setLocationRelativeTo(null);
		setModal(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
	/**
	 * Este metodo retorna a referencia da propria classe.
	 * 
	 * @return <code>DialogoCadastrarVotacao</code> com a referencia da classe.
	 */
	public DialogoCadastrarVotocao getThis() {
		return this;
	}

	/**
	 * Este metodo retorna uma referencia do Field HoraInicio do Dialogo.
	 * 
	 * @return <code>JFormattedTextField</code> com a hora inicio.
	 */
	public JFormattedTextField getHoraInicio() {
		return horaInicio;
	}

	/**
	 * Este metodo retorna uma referencia do Field HoraFim do Dialogo.
	 * 
	 * @return <code>JFormattedTextField</code> com a hora fim.
	 */
	public JFormattedTextField getHoraFim() {
		return horaFim;
	}

	/**
	 * Este metodo retorna uma referencia do calendario da data de votacao.
	 * 
	 * @return <code>JXDatePicker</code> com a data da votacao.
	 */
	public WebDateField getData() {
		return data;
	}

	/**
	 * Este metodo retorna uma referencia do Botao Gravar do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao gravar.
	 */
	public JButton getBotaoGravar() {
		return botaoGravar;
	}

	/**
	 * Este metodo retorna uma referencia do Botao Cancelar do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao cancelar.
	 */
	public JButton getBotaoCancelar() {
		return botaoCancelar;
	}

	/**
	 * Este metodo retorna uma referencia do Botao Limpar do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao limpar.
	 */
	public JButton getBotaoLimpar() {
		return botaoLimpar;
	}

	/**
	 * Este metodo retorna uma referencia da tabela de cargos do Dialogo.
	 * 
	 * @return <code>JTable</code> com o conteudo da tabela.
	 */
	public JTable getTabelaCargo() {
		return tabelaCargo;
	}
	
}
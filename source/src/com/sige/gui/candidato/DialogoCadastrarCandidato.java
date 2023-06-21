package com.sige.gui.candidato;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.sige.gui.ShadowBorder;
import com.sige.gui.candidato.eventos.TratadorEventosCadastroCandidato;
import com.sige.gui.lookup.LookUpCargo;
import com.sige.gui.lookup.LookUpPartido;
import com.sige.gui.recursos.SomenteNumeros;
import com.sige.gui.recursos.TamanhoMaximo;

/**
 * Esta classe extende um <code>JDialog</code> e cria uma interface grafica que permite o usuario cadastrar um candidato.
 * 
 * @author Charles Garrocho
 */
public class DialogoCadastrarCandidato extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField fieldNome, fieldNumero, cargoField, partidoField;
	private JButton botaoExcluir, botaoGravar, botaoCancelar, botaoPesquisaCargo, botaoPesquisaPartido, botaoAbrirImagem;
	private ImageIcon FotoCandidato;
	private String caminhoImagem;
	private int NumeroAlterar;
	private String cargoAlterar;

	/**
	 * Este e o construtor. Ele constroi a interface grafica do dialogo cadastrar candidato. Caso seja um cadastro, o usuario
	 * deve informar "-1" no campo "Numero". Caso contrario, seja alteracao, o usuario deve apenas passar as informacoes do
	 * candidato a ser alterado.
	 * 
	 * @param numero <code>String</code> com o numero do candidato a ser alterado.
	 * @param nome <code>String</code> com o nome do candidato a ser alterado.
	 * @param partido <code>String</code> com o partido do candidato a ser alterado.
	 * @param cargo <code>String</code> com o cargo do candidato a ser alterado.
	 * @param caminhoFoto <code>String</code> com o caminho da Foto do candidato a ser alterado.
	 */
	public DialogoCadastrarCandidato(String numero, String nome, String partido, String cargo, String caminhoFoto) {

		getRootPane().setBorder(new ShadowBorder());
		TratadorEventosCadastroCandidato tratadorEventos = new TratadorEventosCadastroCandidato(this);
		NumeroAlterar = Integer.parseInt(numero);
		cargoAlterar = cargo;

		private static final int NUMERO_DE_LINHAS = 4;
		JPanel painelNorte = new JPanel(new GridLayout(NUMERO_DE_LINHAS,0));

		JPanel painelNome = new JPanel();
		fieldNome = new JTextField(25);
		fieldNome.setToolTipText("Descreva Aqui o Nome Completo do Candidato.");
		fieldNome.setDocument(new TamanhoMaximo(30));
		painelNome.add(new JLabel("Nome   "));
		painelNome.add(fieldNome);

		// Adiciona o painelNome e todo seu conteudo ao painelNorte.
		painelNorte.add(painelNome);

		JPanel painelCargo = new JPanel();
		cargoField = new JTextField(21);
		cargoField.setEditable(false);
		botaoPesquisaCargo = new JButton();
		botaoPesquisaCargo.setIcon(new ImageIcon(getClass().getResource("/icones/pesquisar.png")));
		botaoPesquisaCargo.setPreferredSize(new Dimension(30,30));
		botaoPesquisaCargo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botaoPesquisaCargo.setToolTipText("Clique aqui para selecionar um cargo.");
		painelCargo.add(new JLabel(" Cargo   "));
		painelCargo.add(cargoField);
		painelCargo.add(botaoPesquisaCargo);
		botaoPesquisaCargo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new LookUpCargo(getThis(),getThis().getCargoField());
			}
		});

		// Adiciona o painelCargo e todo seu conteudo ao painelNorte.
		painelNorte.add(painelCargo);		

		JPanel painelPartido = new JPanel();
		partidoField = new JTextField(21);
		partidoField.setEditable(false);
		botaoPesquisaPartido = new JButton();
		botaoPesquisaPartido.setIcon(new ImageIcon(getClass().getResource("/icones/pesquisar.png")));
		botaoPesquisaPartido.setPreferredSize(new Dimension(30,30));
		botaoPesquisaPartido.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botaoPesquisaPartido.setToolTipText("Clique aqui para selecionar um cargo.");
		painelPartido.add(new JLabel(" Partido "));
		painelPartido.add(partidoField);
		painelPartido.add(botaoPesquisaPartido);
		botaoPesquisaPartido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new LookUpPartido(getThis(),getThis().getPartidoField());
			}
		});

		// Adiciona o painelPartido e todo seu conteudo ao painelNorte.
		painelNorte.add(painelPartido);

		JPanel painelNumero = new JPanel();
		fieldNumero = new JTextField(13);
		fieldNumero.setToolTipText("Descreva Aqui o Numero de Elei��o do Candidato.");
		fieldNumero.setDocument(new SomenteNumeros());
		
		botaoAbrirImagem  = new JButton("FOTO", new ImageIcon(getClass().getResource("/icones/abrir.png")));
		botaoAbrirImagem.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		botaoAbrirImagem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botaoAbrirImagem.setToolTipText("Selecione Aqui a Foto do Candidato.");
		botaoAbrirImagem.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				String caminhoFoto = dialogoAbrirArquivo(getThis(),"Selecionar Foto");
				if (caminhoFoto != null) {
					addFotoCandidato(caminhoFoto);
				}
			}
		});
		
		painelNumero.add(new JLabel("Numero"));
		painelNumero.add(fieldNumero);
		painelNumero.add(botaoAbrirImagem);
		painelNorte.add(painelNumero);

		// Adiciona o painelNorte no centro do DialogoCadastrarCandidato.
		add(painelNorte, BorderLayout.NORTH);

		JPanel painelCentro = new JPanel();
		
		JPanel painelBotoes = new JPanel();
		botaoGravar   = new JButton("GRAVAR", new ImageIcon(getClass().getResource("/icones/gravar.png")));
		botaoGravar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		botaoGravar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botaoGravar.addActionListener(tratadorEventos);
		
		painelBotoes.add(botaoGravar);

		// Verifica se sera um cadastro ou alteracao de candidato.
		if (NumeroAlterar == -1) {

			// Caso seja um cadastro adiciona a imagem, uma imagem default de cadastro e seta o titulo para Cadastro.
			caminhoImagem = "/icones/cadastro.png";
			setTitle("Cadastro de Candidato");
			
			botaoCancelar = new JButton("CANCELAR", new ImageIcon(getClass().getResource("/icones/cancelar.png")));
			botaoCancelar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
			botaoCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
			botaoCancelar.addActionListener(tratadorEventos);
			
			painelBotoes.add(botaoCancelar);
		}
		else {

			// Caso seja uma alteracao, adiciona todas as informacoes do candidato aos objetos e seta o titulo para alteracao.
			setTitle("Alteracao de Candidato");
			caminhoImagem = caminhoFoto;
			fieldNumero.setText(numero);
			fieldNome.setText(nome);
			cargoField.setText(cargo);
			partidoField.setText(partido);
			
			botaoExcluir = new JButton("EXCLUIR", new ImageIcon(getClass().getResource("/icones/excluir.png")));
			botaoExcluir.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
			botaoExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
			botaoExcluir.addActionListener(tratadorEventos);
			
			painelBotoes.add(botaoExcluir);
		}
		
		// Adiciona o painelBotoes ao sul do DialogoCadastrarCandidato.
		add(painelBotoes, BorderLayout.SOUTH);

		Image img;
		if (caminhoImagem.equalsIgnoreCase("/icones/cadastro.png"))
			img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(caminhoImagem));
		else
			img = Toolkit.getDefaultToolkit().getImage(caminhoImagem);
		Image menor   = img.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
		FotoCandidato = new ImageIcon(menor);
		JLabel labelFoto = new JLabel(FotoCandidato);

		// Adiciona o labelFoto ao painelCentro.
		painelCentro.add(labelFoto);

		// Adiciona o painelCentro no centro do DialogoCadastrarCandidato.
		add(painelCentro, BorderLayout.CENTER);

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
	 * @return <code>DialogoCadastrarCandidato</code>.
	 */
	private DialogoCadastrarCandidato getThis() {
		return this;
	}

	/**
	 * Este metodo recebe o caminho de uma imagem e a carrega e redimensiona em 200 x 200 e adiciona a <code>ImageIcon</code>
	 * FotoCandidato.
	 * 
	 * @param caminhoFoto <code>String</code> com o endereco da Imagem do candidato.
	 */
	public void addFotoCandidato(String caminhoFoto) {

		Image img = Toolkit.getDefaultToolkit().getImage(caminhoFoto);  
		Image menor = img.getScaledInstance(200, 200, Image.SCALE_DEFAULT); 
		FotoCandidato.setImage(menor);
		repaint();
		caminhoImagem = caminhoFoto;
	}

	/** 
	 * Exibe uma caixa de dialogo <code>javax.swing.JFileChooser</code> para 
	 * o usuario indicar o nome do diretorio e arquivo que sera aberto. 
	 * 
	 * @param janelaPai objeto <code>java.awt.Component</code> que identifica a
	 *        janela pai sobre a qual a janela <code>JFileChooser</code> sera
	 *        exibida.  
	 * @param titulo <code>String</code> com o nome da barra de titulo da caixa 
	 *        de dialogo.
	 *        
	 * @return <code>String</code> com o nome do arquivo a ser aberto. 
	 *         Se o usu�rio cancelar a operacao (clicar no botao "Cancelar") sera
	 *         retornado <code>null</code>.
	 *         
	 * @see java.awt.Component
	 */
	public String dialogoAbrirArquivo(Component janelaPai, String titulo) {

		JFileChooser dialogoAbrir = new JFileChooser();

		dialogoAbrir.setFileSelectionMode(JFileChooser.FILES_ONLY);
		dialogoAbrir.setDialogTitle(titulo);
		dialogoAbrir.setApproveButtonText("Abrir");
		dialogoAbrir.setApproveButtonToolTipText("Abre um arquivo em disco.");

		int opcao = dialogoAbrir.showOpenDialog(janelaPai);

		if (opcao == JFileChooser.CANCEL_OPTION)
			return null;

		File arquivo = dialogoAbrir.getSelectedFile();
		String nomeArquivo = arquivo.getPath();

		return nomeArquivo;
	}

	/**
	 * Este metodo retorna uma referencia do Field Nome do Dialogo.
	 * 
	 * @return <code>JTextField</code> com o nome do candidato.
	 */
	public JTextField getFieldNome() {
		return fieldNome;
	}

	/**
	 * Este metodo retorna uma referencia do Field Numero do Dialogo.
	 * 
	 * @return <code>JTextField</code> com o numero do candidato.
	 */
	public JTextField getFieldNumero() {
		return fieldNumero;
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
	 * Este metodo retorna uma referencia do Botao Excluir do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao excluir.
	 */
	public JButton getBotaoExcluir() {
		return botaoExcluir;
	}

	/**
	 * Este metodo retorna uma referencia do Numero Alterar, neste inteiro fica armazenado o antigo numero do candidato antes
	 * do mesmo ser alterado.
	 * 
	 * @return <code>int</code> com o numero anterior do candidato.
	 */
	public int getNumeroAlterar() {
		return NumeroAlterar;
	}

	/**
	 * Este metodo retorna uma referencia do Field Cargo do Dialogo.
	 * 
	 * @return <code>JTextField</code> com o cargo do candidato.
	 */
	public JTextField getCargoField() {
		return cargoField;
	}

	/**
	 * Este metodo retorna uma referencia do Field Partido do Dialogo.
	 * 
	 * @return <code>JTextField</code> com o partido do candidato.
	 */
	public JTextField getPartidoField() {
		return partidoField;
	}

	/**
	 * Este metodo retorna uma referencia do Cargo Alterar, nesta String fica armazenado o antigo cargo do candidato antes
	 * do mesmo ser alterado.
	 * 
	 * @return <code>int</code> com o cargo anterior do candidato.
	 */
	public String getCargoAlterar() {
		return cargoAlterar;
	}

	/**
	 * Este metodo retorna uma referencia do endereco da imagem do candidato.
	 * 
	 * @return <code>String</code> com o endereco do candidato.
	 */
	public String getCaminhoImagem() {
		return caminhoImagem;
	}
}
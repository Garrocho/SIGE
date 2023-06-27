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

		final int NUMERO_DE_LINHAS = 4;
		final int TAMANHO_MAXIMO_NOME = 25;
		final int TAMANHO_MAXIMO_DOCUMENTO = 30;
		final int TAMANHO_CAMPO_CARGO = 21;

		JPanel painelNorte = criarPainelNorte(numero, nome, partido, cargo, tratadorEventos, NUMERO_DE_LINHAS, TAMANHO_MAXIMO_NOME,
				TAMANHO_MAXIMO_DOCUMENTO, TAMANHO_CAMPO_CARGO);

		JPanel painelCentro = criarPainelCentro(caminhoFoto);

		JPanel painelBotoes = criarPainelBotoes(tratadorEventos);

		configurarDialogo(painelNorte, painelCentro, painelBotoes);

		pack();
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icones/icone.png")));
		setLocationRelativeTo(null);
		setModal(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	private JPanel criarPainelNorte(String numero, String nome, String partido, String cargo,
								TratadorEventosCadastroCandidato tratadorEventos,
								int numLinhas, int tamMaxNome, int tamMaxDocumento, int tamCampoCargo) {
		JPanel painelNorte = new JPanel(new GridLayout(numLinhas, 0));

		JPanel painelNome = criarPainelCampo("Nome", nome, tamMaxNome);
		painelNorte.add(painelNome);

		JPanel painelCargo = criarPainelCampoCargo(cargo, tamCampoCargo, tratadorEventos);
		painelNorte.add(painelCargo);

		JPanel painelPartido = criarPainelCampoPartido(partido, tratadorEventos);
		painelNorte.add(painelPartido);

		JPanel painelNumero = criarPainelCampoNumero(numero);
		painelNorte.add(painelNumero);

		return painelNorte;
	}

	private JPanel criarPainelCampo(String labelCampo, String valorInicial, int tamanhoMaximo) {
		JPanel painelCampo = new JPanel();
		JTextField textField = new JTextField(tamanhoMaximo);
		textField.setToolTipText("Descreva aqui o " + labelCampo + " Completo do Candidato.");
		textField.setDocument(new TamanhoMaximo(tamanhoMaximo));
		textField.setText(valorInicial);
		painelCampo.add(new JLabel(labelCampo + "   "));
		painelCampo.add(textField);
		return painelCampo;
	}

	private JPanel criarPainelCampoCargo(String cargo, int tamanhoCampoCargo,
										TratadorEventosCadastroCandidato tratadorEventos) {
		JPanel painelCargo = new JPanel();
		cargoField = new JTextField(tamanhoCampoCargo);
		cargoField.setEditable(false);
		botaoPesquisaCargo = criarBotaoPesquisa("/icones/pesquisar.png", "Clique aqui para selecionar um cargo", tratadorEventos);
		painelCargo.add(new JLabel(" Cargo   "));
		painelCargo.add(cargoField);
		painelCargo.add(botaoPesquisaCargo);
		return painelCargo;
	}

	private JPanel criarPainelCampoPartido(String partido, TratadorEventosCadastroCandidato tratadorEventos) {
		JPanel painelPartido = new JPanel();
		partidoField = new JTextField(21);
		partidoField.setEditable(false);
		botaoPesquisaPartido = criarBotaoPesquisa("/icones/pesquisar.png", "Clique aqui para selecionar um partido", tratadorEventos);
		painelPartido.add(new JLabel(" Partido "));
		painelPartido.add(partidoField);
		painelPartido.add(botaoPesquisaPartido);
		return painelPartido;
	}

	private JPanel criarPainelCampoNumero(String numero) {
		JPanel painelNumero = new JPanel();
		fieldNumero = new JTextField(13);
		fieldNumero.setToolTipText("Descreva aqui o Número de Eleição do Candidato.");
		fieldNumero.setDocument(new SomenteNumeros());
		fieldNumero.setText(numero);
		botaoAbrirImagem = criarBotaoAbrirImagem();
		painelNumero.add(new JLabel("Número"));
		painelNumero.add(fieldNumero);
		painelNumero.add(botaoAbrirImagem);
		return painelNumero;
	}

	private JButton criarBotaoPesquisa(String iconePath, String tooltip, TratadorEventosCadastroCandidato tratadorEventos) {
		JButton botaoPesquisa = new JButton();
		botaoPesquisa.setIcon(new ImageIcon(getClass().getResource(iconePath)));
		botaoPesquisa.setPreferredSize(new Dimension(30, 30));
		botaoPesquisa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botaoPesquisa.setToolTipText(tooltip);
		botaoPesquisa.addActionListener(tratadorEventos);
		return botaoPesquisa;
	}

	private JButton criarBotaoAbrirImagem() {
		JButton botaoAbrirImagem = new JButton("FOTO", new ImageIcon(getClass().getResource("/icones/abrir.png")));
		botaoAbrirImagem.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		botaoAbrirImagem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botaoAbrirImagem.setToolTipText("Selecione aqui a foto do candidato.");
		botaoAbrirImagem.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				String caminhoFoto = dialogoAbrirArquivo(getThis(), "Selecionar Foto");
				if (caminhoFoto != null) {
					addFotoCandidato(caminhoFoto);
				}
			}
		});
		return botaoAbrirImagem;
	}

	private JPanel criarPainelCentro(String caminhoFoto) {
		JPanel painelCentro = new JPanel();
		Image img;
		if (caminhoFoto.equalsIgnoreCase("/icones/cadastro.png"))
			img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(caminhoFoto));
		else
			img = Toolkit.getDefaultToolkit().getImage(caminhoFoto);
		Image menor = img.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
		FotoCandidato = new ImageIcon(menor);
		JLabel labelFoto = new JLabel(FotoCandidato);
		painelCentro.add(labelFoto);
		return painelCentro;
	}

	private JPanel criarPainelBotoes(TratadorEventosCadastroCandidato tratadorEventos) {
		JPanel painelBotoes = new JPanel();
		botaoGravar = criarBotao("GRAVAR", "/icones/gravar.png", tratadorEventos);
		painelBotoes.add(botaoGravar);
		botaoCancelar = criarBotao("CANCELAR", "/icones/cancelar.png", tratadorEventos);
		painelBotoes.add(botaoCancelar);
		return painelBotoes;
	}

	private JButton criarBotao(String texto, String iconePath, TratadorEventosCadastroCandidato tratadorEventos) {
		JButton botao = new JButton(texto, new ImageIcon(getClass().getResource(iconePath)));
		botao.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		botao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		botao.addActionListener(tratadorEventos);
		return botao;
	}

	private void configurarDialogo(JPanel painelNorte, JPanel painelCentro, JPanel painelBotoes) {
		setLayout(new BorderLayout());
		add(painelNorte, BorderLayout.NORTH);
		add(painelCentro, BorderLayout.CENTER);
		add(painelBotoes, BorderLayout.SOUTH);
	}

	private String dialogoAbrirArquivo(Component parent, String titulo) {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagens", "jpg", "jpeg", "png", "gif");
		fileChooser.setFileFilter(filtro);
		int returnVal = fileChooser.showOpenDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File arquivo = fileChooser.getSelectedFile();
			return arquivo.getPath();
		}
		return null;
	}

	private DialogoCadastrarCandidato getThis() {
		return this;
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
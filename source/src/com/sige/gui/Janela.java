package com.sige.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.AbstractBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


public abstract class Janela extends JFrame {

	private static final long serialVersionUID = 1L;

	public Janela() {
		super();
		getRootPane().setBorder(new ShadowBorder());
	}

	/** Encontra o recurso no diretorio de recursos do jar
	 * @param enderecoArquivo <code>String</code> caminho do recurso
	 * @return <code>URL</code> com o endereco do recurso
	 */
	public static URL getResource(String enderecoArquivo){
		return Janela.class.getResource("/icones/" + enderecoArquivo);
	}

	/** Janela para abrir arquivos
	 * @param componentePai <code>Component</code> sobre o qual esta janela ser� aberta, a qual esta sera "filha"
	 * @param titulo <code>String</code> com o titulo da janela
	 * @param diretorioCorrente <code>String</code> com o diretorio onde a janela de abrir inicializara, pode ser <code>null</code>
	 * @param opcaoTodosArquivos <code>boolean</code> informando se a opcao(filtro) Todos Arquivos ser� mostrada ou n�o
	 * @param nomeFiltro <code>String</code> com o nome do filtro de extens�es
	 * @param extensao <code>String...</code> com as extens�es usadas no filtro
	 * @return <code>String</code> com o endere�o do arquivo selecionado, caso nenhum arquivo seja selecionado, � retornado <code>null</code>
	 * @see  JFileChooser
	 */
	public static String janelaAbrirArquivo(Component componentePai, String titulo, String diretorioCorrente, boolean opcaoTodosArquivos, String nomeFiltro, String... extensao){
		JFileChooser janelaAbrir = new JFileChooser(titulo);

		janelaAbrir.setAcceptAllFileFilterUsed(false);
		janelaAbrir.setDialogType(JFileChooser.OPEN_DIALOG);
		janelaAbrir.setFileSelectionMode(JFileChooser.FILES_ONLY);
		janelaAbrir.setDialogTitle(titulo);
		janelaAbrir.setFileFilter(new FileNameExtensionFilter(nomeFiltro, extensao));
		janelaAbrir.setAcceptAllFileFilterUsed(opcaoTodosArquivos);

		if(diretorioCorrente != null){
			if (diretorioCorrente.isEmpty()){
				janelaAbrir.setCurrentDirectory(new File("."));
			}
			else{
				janelaAbrir.setCurrentDirectory(new File(diretorioCorrente));
			}
		}

		janelaAbrir.showOpenDialog(componentePai);

		return janelaAbrir.getSelectedFile() != null ? janelaAbrir.getSelectedFile().getPath() : null;
	}

	/** 
	 * Exibe uma caixa de dialogo <code>javax.swing.JFileChooser</code> para 
	 * o usuario indicar o nome do diretorio que sera aberto. 
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
	public static String dialogoAbrirDiretorio(Component janelaPai, String titulo) {

		JFileChooser dialogoAbrir = new JFileChooser();

		dialogoAbrir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		dialogoAbrir.setDialogTitle(titulo);
		dialogoAbrir.setApproveButtonText("Abrir");
		dialogoAbrir.setApproveButtonToolTipText("Abre um diretorio em disco.");
		dialogoAbrir.setAcceptAllFileFilterUsed(false);

		int opcao = dialogoAbrir.showOpenDialog(janelaPai);

		if (opcao == JFileChooser.CANCEL_OPTION)
			return null;

		return dialogoAbrir.getSelectedFile().getPath();
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
	public static String dialogoAbrirArquivo(Component janelaPai, String titulo) {

		JFileChooser dialogoAbrir = new JFileChooser();

		dialogoAbrir.setFileSelectionMode(JFileChooser.FILES_ONLY);
		dialogoAbrir.setDialogTitle(titulo);
		dialogoAbrir.setApproveButtonText("Abrir");
		dialogoAbrir.setApproveButtonToolTipText("Abre um arquivo em disco.");
		dialogoAbrir.setFileFilter(new FileNameExtensionFilter("Mjpeg", "Mjpeg"));
		dialogoAbrir.setAcceptAllFileFilterUsed(false);

		int opcao = dialogoAbrir.showOpenDialog(janelaPai);

		if (opcao == JFileChooser.CANCEL_OPTION)
			return null;

		File arquivo = dialogoAbrir.getSelectedFile();
		String nomeArquivo = arquivo.getPath();

		return nomeArquivo;
	}

	public class Painel extends JPanel {
		private static final long serialVersionUID = 1L;
		private Image bg;

		public Painel(ImageIcon imagem) {
			super();
			this.bg = imagem.getImage();
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
		}
	}
}
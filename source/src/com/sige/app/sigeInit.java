package com.sige.app;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import javax.swing.UIManager;

import com.sige.gui.JanelaPrincipal;
import com.sige.persistencia.BancoDados;

import de.javasoft.plaf.synthetica.SyntheticaWhiteVisionLookAndFeel;

/**
 * Esta e a classe principal do programa. O programa inicia aqui.
 * 
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class sigeInit {

	/**
	 * Este e o metodo que da inicio ao programa. Ele define a aparencia padrao e verifica o banco de dados da aplicacao.
	 * 
	 * @param args Conjunto de argumentos.
	 */
	public static void main(String args[]) {

		// Inicializa o banco de dados e verifica de o banco existe.
		try {
			UIManager.setLookAndFeel(new SyntheticaWhiteVisionLookAndFeel());
			java.io.File IMAGENS;
			IMAGENS = new java.io.File (
					"Recursos"
					+ System.getProperty("file.separator")
					+ "Imagens");

			if (!IMAGENS.exists())
				IMAGENS.mkdir();
			
		} catch (Exception e) {
			showMessageDialog(null, "Erro ao iniciar o SIGE.\n\n" +
					"Informe o Seguinte Erro ao Analista: " + e.toString(), "Erro", ERROR_MESSAGE);
		}
		
		try {
			BancoDados bd = new BancoDados();
			bd.verificaBancoDados();
		} catch (Exception e) {
			showMessageDialog(null, "Erro ao iniciar o banco de dados.\n\n" +
					"Informe o Seguinte Erro ao Analista: " + e.toString(), "Erro", ERROR_MESSAGE);
		}
		new JanelaPrincipal();
	}
}
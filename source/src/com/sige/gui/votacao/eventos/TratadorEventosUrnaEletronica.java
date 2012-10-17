package com.sige.gui.votacao.eventos;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sige.gui.votacao.DialogoUrnaEletronica;
import com.sige.persistencia.BancoDadosCandidato;
import com.sige.persistencia.BancoDadosPartido;
import com.sige.persistencia.BancoDadosVotacaoCandidatos;
import com.sige.persistencia.BancoDadosVotacaoCargos;

/**
 * Esta classe implementa um <code>ActionListener</code> e trata os eventos da classe <code>DialogoUrnaEletronica</code>.
 *  
 * @author Charles Garrocho
 * @author Barbara Silveiro
 * 
 * @see DialogoUrnaEletronica
 */
public class TratadorEventosUrnaEletronica implements ActionListener {

	private DialogoUrnaEletronica gui;
	private String BOTOES = getClass().getResource("/sons/botoes.wav").toString();
	private String FIM    = getClass().getResource("/sons/fim.wav").toString();
	private BancoDadosVotacaoCandidatos dataBaseVotacaoCandidatos;
	private BancoDadosVotacaoCargos dataBaseVotacaoCargos;
	private BancoDadosCandidato dataBaseCandidato;
	private BancoDadosPartido dataBasePartido;
	private ResultSet resultado;
	private boolean votou;

	/**
	 * Este e o construtor. Ele recebe como argumento o <code>DialogoUrnaEletronica</code> guarda a referencia dela em uma 
	 * variavel para poder tratar os eventos. O construtor instancia as classes de banco de dados necessarias para poder fazer
	 * as consultas e insercoes no banco de dados.
	 * 
	 * @param dialogoUrnaEletronica um <code>DialogoUrnaEletronica</code>.
	 * 
	 * @see BancoDadosVotacaoCandidatos
	 * @see BancoDadosCandidato
	 * @see BancoDadosPartido
	 * @see BancoDadosVotacaoCargos
	 */
	public TratadorEventosUrnaEletronica(DialogoUrnaEletronica dialogoUrnaEletronica) {
		super();
		this.gui = dialogoUrnaEletronica;
		this.dataBaseVotacaoCandidatos = new BancoDadosVotacaoCandidatos();
		this.dataBaseCandidato = new BancoDadosCandidato();
		this.dataBasePartido = new BancoDadosPartido();
		this.dataBaseVotacaoCargos = new BancoDadosVotacaoCargos();
		this.votou = false;
	}

	/**
	 * Este metodo toca o som da urna eletronica.
	 */
	private void tocaSom() {
		try {
			AudioClip somCliqueConfirma = null;
			if (gui.getQtdeCargos()+1 < gui.getTotalCargos())
				somCliqueConfirma = Applet.newAudioClip(new URL(BOTOES));
			else
				somCliqueConfirma = Applet.newAudioClip(new URL(FIM));
			somCliqueConfirma.play();
		} catch (Exception e) {
			showMessageDialog(gui, "Informe o Seguinte Erro ao Analista: " + e.toString(), "ERROR", ERROR_MESSAGE);
		}  
	}

	/** 
	 * Este metodo e implementado por que a classe <code>TratadorEventosDialogoUrnaEletronica</code> implementa um 
	 * ActionListener, e por isso ela deve obrigatoriamente implementar os metodos abstratados dessa classe. Este metodo 
	 * trata os eventos de todos os botoes da urna eletronica.
	 */
	public void actionPerformed(ActionEvent evento) {

		// Verifica se o usuario ja acabou de votar.
		if (!this.votou == true) {

			// Verifica se o usuario clicou no botao Branco.
			if (evento.getSource() == gui.getBotaoBranco() ) {

				// Verifica se o label Numero do Candidato e igual a 0, se sim modifica o painel para voto em branco.
				if (gui.getNumeroCandidato().getText().trim().length() == 0) {
					gui.getSeuVotoParaLabel().setVisible(true);
					gui.getAperte().setVisible(true);
					gui.getLabelLinha().setVisible(true);
					gui.getLabelLinha2().setVisible(true);
					gui.getLaranjaCorrige().setVisible(true);
					gui.getVerdeConfirma().setVisible(true);
					gui.getNumeroLabel().setText("  VOTO EM");
					gui.getNumeroLabel().setFont(new Font(null, Font.PLAIN, 31));
					gui.getNumeroCandidato().setText("BRANCO");
					gui.getNumeroCandidato().setBorder(BorderFactory.createEmptyBorder());
					gui.getNumeroCandidato().setFont(new Font(null, Font.PLAIN, 31));
					gui.setCaminhoFoto(gui.TRANSPARENTE);
				}
			}

			// Verifica se o evento aconteceu no votao Corrige. Se sim chama o metodo corrigeTela().
			else if (evento.getSource() == gui.getBotaoCorrige() ) {
				corrigeTela();
			}

			// Verifica se o usuario clicou no botao Confirma.
			else if (evento.getSource() == gui.getBotaoConfirma()) {

				// Verifica se a quantidade de cargos atual e menor que o total.
				if ( gui.getTotalCargos() != gui.getQtdeCargos() ) {

					try {
						// Caso seja escolhido um candidato correto, a imagem do candidato sera trocada.
						if ( !gui.getCaminhoFoto().equalsIgnoreCase(gui.TRANSPARENTE) ) {
							tocaSom();

							dataBaseVotacaoCandidatos.iniciaConexao();
							int votos = dataBaseVotacaoCandidatos.obterVotosCandidato(gui.getNomeCandidato().getText(), gui.getDATA_VOTACAO());

							if (votos == 0)
								dataBaseVotacaoCandidatos.adicionarVotacaoCandidatos(gui.getDATA_VOTACAO(), gui.getCargos()[gui.getQtdeCargos()], gui.getNomeCandidato().getText(), votos + 1);
							else
								dataBaseVotacaoCandidatos.atualizarVotacaoCandidatos(gui.getNomeCandidato().getText(), votos + 1);
							dataBaseVotacaoCandidatos.fechaConexao();

							dataBaseVotacaoCargos.iniciaConexao();
							dataBaseVotacaoCargos.atualizarEleitores(dataBaseVotacaoCargos.obterQuantidadeEleitores(gui.getCargos()[gui.getQtdeCargos()], gui.getDATA_VOTACAO()) + 1, gui.getCargos()[gui.getQtdeCargos()], gui.getDATA_VOTACAO());
							dataBaseVotacaoCargos.fechaConexao();

							if ( gui.getQtdeCargos() < gui.getTotalCargos()) {
								gui.setQtdeCargos(gui.getQtdeCargos() + 1);
							}
							corrigeTela();
						}

						// Verifica se o voto e nulo.
						else if (gui.getPartidoCandidato().getText().equalsIgnoreCase("VOTO NULO")) {
							tocaSom();

							dataBaseVotacaoCargos.iniciaConexao();
							dataBaseVotacaoCargos.atualizarNulo(dataBaseVotacaoCargos.obterQuantidadeNulo(gui.getCargos()[gui.getQtdeCargos()], gui.getDATA_VOTACAO()) + 1, gui.getCargos()[gui.getQtdeCargos()], gui.getDATA_VOTACAO());
							dataBaseVotacaoCargos.atualizarEleitores(dataBaseVotacaoCargos.obterQuantidadeEleitores(gui.getCargos()[gui.getQtdeCargos()], gui.getDATA_VOTACAO()) + 1, gui.getCargos()[gui.getQtdeCargos()], gui.getDATA_VOTACAO());
							dataBaseVotacaoCargos.fechaConexao();

							if ( gui.getQtdeCargos() < gui.getTotalCargos()) {
								gui.setQtdeCargos(gui.getQtdeCargos() + 1);
							}
							corrigeTela();

						}

						// Verifica se o voto e Branco.
						else if (gui.getNumeroCandidato().getText().equalsIgnoreCase("BRANCO")) {
							tocaSom();

							dataBaseVotacaoCargos.iniciaConexao();
							dataBaseVotacaoCargos.atualizarBranco(dataBaseVotacaoCargos.obterQuantidadeBranco(gui.getCargos()[gui.getQtdeCargos()], gui.getDATA_VOTACAO()) + 1, gui.getCargos()[gui.getQtdeCargos()], gui.getDATA_VOTACAO());
							dataBaseVotacaoCargos.atualizarEleitores(dataBaseVotacaoCargos.obterQuantidadeEleitores(gui.getCargos()[gui.getQtdeCargos()], gui.getDATA_VOTACAO()) + 1, gui.getCargos()[gui.getQtdeCargos()], gui.getDATA_VOTACAO());
							dataBaseVotacaoCargos.fechaConexao();

							if ( gui.getQtdeCargos() < gui.getTotalCargos()) {
								gui.setQtdeCargos(gui.getQtdeCargos() + 1);
							}
							corrigeTela();
						}
					} catch (Exception e) {
						showMessageDialog(gui, "Informe o Seguinte Erro ao Analista: " + e.toString(), "Atencao", INFORMATION_MESSAGE);
					}
				}

				// Verifica se o usuario ja votou em todos os cargos. Se sim Cria um painelFim e adiciona no painelTela.
				if ( gui.getTotalCargos() == gui.getQtdeCargos() ) {
					gui.getSeuVotoParaLabel().setVisible(false);
					gui.getAperte().setVisible(false);
					gui.getLabelLinha().setVisible(false);
					gui.getLabelLinha2().setVisible(false);
					gui.getLaranjaCorrige().setVisible(false);
					gui.getVerdeConfirma().setVisible(false);
					gui.getNumeroLabel().setVisible(false);
					gui.getNumeroCandidato().setVisible(false);
					gui.getCargoLabel().setVisible(false);

					JPanel painelFIM = new JPanel();
					JLabel partidoCandidato = new JLabel("FIM");
					partidoCandidato.setFont(new Font(null, Font.BOLD, 150));
					painelFIM.add(partidoCandidato);
					painelFIM.setPreferredSize(new Dimension(500, 500));
					painelFIM.setBackground(Color.LIGHT_GRAY);

					GridBagConstraints c = new GridBagConstraints();

					c.insets = new Insets(500, 300, 300, 150);
					c.fill = GridBagConstraints.HORIZONTAL;
					c.anchor = GridBagConstraints.FIRST_LINE_END;
					c.gridx = 0;
					c.gridy = 0;
					gui.setPainelTela(painelFIM, c);

					JPanel painelVotou = new JPanel();
					painelVotou.setBackground(Color.LIGHT_GRAY);
					JLabel votou = new JLabel("VOTOU");
					votou.setFont(new Font(null, Font.PLAIN, 40));
					painelVotou.add(votou);

					c.insets = new Insets(10, 500, 187, 33);
					c.fill = GridBagConstraints.HORIZONTAL;
					c.anchor = GridBagConstraints.LAST_LINE_END;
					c.gridx = 0;
					c.gridy = 0;
					gui.setPainelTela(painelVotou, c);
					this.votou = true;
				}
			}

			// Verifica se o usuario clicou no botao 0. Se sim chama o tratador de botoes passando como argumento o numero dele.
			else if (evento.getSource() == gui.getBotoesNumericos()[0] ) {
				trataBotoes(0);
			}

			// Verifica se o usuario clicou no botao 1. Se sim chama o tratador de botoes passando como argumento o numero dele.
			else if (evento.getSource() == gui.getBotoesNumericos()[1] ) {
				trataBotoes(1);
			}

			// Verifica se o usuario clicou no botao 2. Se sim chama o tratador de botoes passando como argumento o numero dele.
			else if (evento.getSource() == gui.getBotoesNumericos()[2] ) {
				trataBotoes(2);
			}

			// Verifica se o usuario clicou no botao 3. Se sim chama o tratador de botoes passando como argumento o numero dele.
			else if (evento.getSource() == gui.getBotoesNumericos()[3] ) {
				trataBotoes(3);
			}

			// Verifica se o usuario clicou no botao 4. Se sim chama o tratador de botoes passando como argumento o numero dele.
			else if (evento.getSource() == gui.getBotoesNumericos()[4] ) {
				trataBotoes(4);
			}

			// Verifica se o usuario clicou no botao 5. Se sim chama o tratador de botoes passando como argumento o numero dele.
			else if (evento.getSource() == gui.getBotoesNumericos()[5] ) {
				trataBotoes(5);
			}

			// Verifica se o usuario clicou no botao 6. Se sim chama o tratador de botoes passando como argumento o numero dele.
			else if (evento.getSource() == gui.getBotoesNumericos()[6] ) {
				trataBotoes(6);
			}

			// Verifica se o usuario clicou no botao 7. Se sim chama o tratador de botoes passando como argumento o numero dele.
			else if (evento.getSource() == gui.getBotoesNumericos()[7] ) {
				trataBotoes(7);
			}

			// Verifica se o usuario clicou no botao 8. Se sim chama o tratador de botoes passando como argumento o numero dele.
			else if (evento.getSource() == gui.getBotoesNumericos()[8] ) {
				trataBotoes(8);
			}

			// Verifica se o usuario clicou no botao 9. Se sim chama o tratador de botoes passando como argumento o numero dele.
			else if (evento.getSource() == gui.getBotoesNumericos()[9] ) {
				trataBotoes(9);
			}
		}
	}

	/**
	 * Este metodo corrige a visualizacao do painel.
	 */
	private void corrigeTela() {
		gui.atualizaCargoLabel();
		gui.getNumeroCandidato().setFont(new Font(null, Font.BOLD, 29));
		gui.getNumeroCandidato().setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		gui.getNumeroLabel().setText("  Numero: ");
		gui.getNumeroCandidato().setText(" ");
		gui.getNomeCandidato().setText("");
		gui.getPartidoCandidato().setText("");
		gui.getNumeroLabel().setFont(new Font(null, Font.PLAIN, 22));
		gui.getNomeLabel().setVisible(false);
		gui.getPartidoLabel().setVisible(false);
		gui.getSeuVotoParaLabel().setVisible(false);
		gui.getAperte().setVisible(false);
		gui.getLabelLinha().setVisible(false);
		gui.getLabelLinha2().setVisible(false);
		gui.getLaranjaCorrige().setVisible(false);
		gui.getVerdeConfirma().setVisible(false);
		gui.addFotoCandidato(gui.TRANSPARENTE);
	}

	/**
	 * Este metodo trata o botao que e passado como argumento.
	 * 
	 * @param botao um <code>int</code> com o numero do botao que sera tratado.
	 */
	private void trataBotoes(int botao) {
		gui.getNumeroCandidato().setText(gui.getNumeroCandidato().getText().trim());

		// Verifica se a quantidade de digitos do labelNumero e menor que a quantidade total de digitos do cargo atual.
		if (gui.getNumeroCandidato().getText().length() < gui.getDigitos()[gui.getQtdeCargos()]) {
			gui.getNumeroCandidato().setText(gui.getNumeroCandidato().getText() + botao);
		}

		/* verifica se a quantidade de digitos do labelNumero e igual a quantidade total de digitos do cargo atual. Se sim
		   o metodo pesquisaCandidato e chamado. */
		if (gui.getNumeroCandidato().getText().length() == gui.getDigitos()[gui.getQtdeCargos()])
			pesquisaCandidato();

		// Verifica se o usuario clicou no botao Branco. Se nao e feito uma busca de partido no banco de dados.
		if (!gui.getNumeroCandidato().getText().equalsIgnoreCase("BRANCO") ) {
			pesquisaPartido();
		}
	}

	/**
	 * Este metodo busca no banco de dados um partido a partir dos numeros digitos na urna eletronica.
	 */
	private void pesquisaPartido() {
		try {
			int verifica = 0;

			// Verifica se a quantidade de digitos e menor ou igual a quantidade total de digitos daquele partido.
			if (gui.getNumeroCandidato().getText().length() <= gui.getDigitos()[gui.getQtdeCargos()]) {
				dataBasePartido.iniciaConexao();

				// Verifica se existe um partido com este numero.
				if ( dataBasePartido.verificaPartido(Integer.parseInt(gui.getNumeroCandidato().getText())) != 0) {
					dataBasePartido.fechaConexao();

					dataBaseCandidato.iniciaConexao();
					verifica = dataBaseCandidato.verificaCandidato(Integer.parseInt(gui.getNumeroCandidato().getText()));
					dataBaseCandidato.fechaConexao();

					// Se existir um partido com este numero, e adicionado a sigla do partido no labelPartido.
					if ( verifica != 0 ) {
						dataBasePartido.iniciaConexao();
						resultado = dataBasePartido.obterPartido(Integer.parseInt(gui.getNumeroCandidato().getText()));
						while(resultado.next()) {
							gui.getPartidoCandidato().setText(resultado.getString("sigla"));
						}
						gui.getPartidoLabel().setVisible(true);
						gui.getPartidoCandidato().setFont(new Font(null, Font.BOLD, 18));
						dataBasePartido.fechaConexao();
					}
				}
				else
					dataBasePartido.fechaConexao();
			}
			dataBaseCandidato.iniciaConexao();
			verifica = dataBaseCandidato.verificaCandidatoPorCargo(Integer.parseInt(gui.getNumeroCandidato().getText()), gui.getCargos()[gui.getQtdeCargos()] );
			dataBaseCandidato.fechaConexao();

			/* Caso a quantidade de digitos tenha chegado ao total de digitos daquele cargo e nao tennha sido encontrado nenhum
			   partido para aquele numero uma mensagem de numero errado e Nulo e enviado a tela.*/
			if (gui.getNumeroCandidato().getText().length() == gui.getDigitos()[gui.getQtdeCargos()] && verifica == 0) {
				gui.getNomeLabel().setText("  NUMERO ERR");
				gui.getNomeCandidato().setText("ADO");
				gui.getNomeCandidato().setFont(new Font(null, Font.PLAIN, 20));
				gui.getPartidoCandidato().setText("VOTO NULO");
				gui.getPartidoCandidato().setFont(new Font(null, Font.PLAIN, 28));

				gui.getPartidoLabel().setVisible(false);
				gui.getNomeLabel().setVisible(true);
				gui.getSeuVotoParaLabel().setVisible(true);
				gui.getAperte().setVisible(true);
				gui.getLabelLinha().setVisible(true);
				gui.getLabelLinha2().setVisible(true);
				gui.getLaranjaCorrige().setVisible(true);
				gui.getVerdeConfirma().setVisible(true);
			}

		} catch (Exception e) {
			showMessageDialog(gui, "Informe o Seguinte Erro ao Analista: " + e.toString(), "Atencao", INFORMATION_MESSAGE);
		}
	}

	/**
	 * Este metodo pesquisa um candidato a partir dos numeros fornecidos na urna eletronica.
	 */
	public void pesquisaCandidato() {

		try {
			dataBaseCandidato.iniciaConexao();

			// Verifica se existe um candidato com este numero.
			if (dataBaseCandidato.verificaCandidatoPorCargo(Integer.parseInt(gui.getNumeroCandidato().getText()), gui.getCargos()[gui.getQtdeCargos()]) != 0) {
				resultado = dataBaseCandidato.obterCandidatoPorCargo(Integer.parseInt(gui.getNumeroCandidato().getText()), gui.getCargos()[gui.getQtdeCargos()]);

				// Adiciona os dados do candidatos na tela.
				while(resultado.next()) {
					gui.getNomeCandidato().setText(resultado.getString("nome"));
					gui.addFotoCandidato(resultado.getString("caminhoFoto"));
				}

				// Verifica se existe espaco no nome do candidato. Caso haja copia apenas o primeiro nome do candidato.
				if (gui.getNomeCandidato().getText().indexOf(" ") != -1)
					gui.getNomeCandidato().setText(gui.getNomeCandidato().getText().substring(0, gui.getNomeCandidato().getText().indexOf(" ")));
				else
					// Caso nao haja espacos, copia todo o nome do candidato.
					gui.getNomeCandidato().setText(gui.getNomeCandidato().getText());
				gui.getNomeCandidato().setFont(new Font(null, Font.BOLD, 18));
				gui.getNomeLabel().setText("  Nome:");
				gui.getNomeLabel().setVisible(true);
				gui.getPartidoLabel().setVisible(true);
				gui.getSeuVotoParaLabel().setVisible(true);
				gui.getAperte().setVisible(true);
				gui.getLabelLinha().setVisible(true);
				gui.getLabelLinha2().setVisible(true);
				gui.getLaranjaCorrige().setVisible(true);
				gui.getVerdeConfirma().setVisible(true);
				gui.getNumeroCandidato().setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
				dataBaseCandidato.fechaConexao();
			}
			else
				dataBaseCandidato.fechaConexao();
		} catch (Exception e) {
			showMessageDialog(gui, "Informe o Seguinte Erro ao Analista: " + e.toString(), "Atencao", INFORMATION_MESSAGE);
		}
	}
}

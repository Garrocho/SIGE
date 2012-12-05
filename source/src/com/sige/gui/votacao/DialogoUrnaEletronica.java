package com.sige.gui.votacao;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import com.sige.gui.ShadowBorder;
import com.sige.gui.votacao.eventos.TratadorEventosUrnaEletronica;
import com.sige.persistencia.BancoDadosVotacao;
import com.sige.persistencia.BancoDadosVotacaoCargos;
import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;

import de.javasoft.plaf.synthetica.SyntheticaWhiteVisionLookAndFeel;

/**
 * Esta classe extende um <code>JDialog</code> e cria uma interface grafica que permite o usuario votar em um candidato.
 * 
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class DialogoUrnaEletronica extends JDialog {

	private static final long serialVersionUID = 1L;

	private String cargos[];
	private int digitos[];
	private int qtdeCargos;
	private JLabel cargoLabel, numeroLabel, nomeLabel, partidoLabel,
	numeroCandidato, nomeCandidato, partidoCandidato,
	seuVotoParaLabel, verdeConfirma, laranjaCorrige, aperte, labelFoto, labelLinha, labelLinha2;
	private ImageIcon fotoCandidato;
	private String caminhoFoto;

	private JPanel painelTela;

	private JButton botoesNumericos[] = new JButton[10];
	private JButton botaoBranco, botaoCorrige, botaoConfirma;

	private TratadorEventosUrnaEletronica tratadorEventos;
	
	/**
	 * Endereco da imagem transparente que e adicionado no painel para auziliar a urna eletronica.
	 */
	public final String TRANSPARENTE = "/icones/transparente.png";
	private String DATA_VOTACAO;
	private int totalCargos;
	private BancoDadosVotacao dataBaseVotacao;
	private BancoDadosVotacaoCargos dataBaseVotacaoCargo;

	/**
	 * Este e o construtor. Ele cria a interface grafica da Urna Eletronica.
	 */
	public DialogoUrnaEletronica () {

		super();
		getRootPane().setBorder(new ShadowBorder());

		// Caso a hora esteja no horario correto da votacao o Dialogo da Urna eletronica e criado.
		if ( verificaVotacao() != -1) {
			
			try {
				UIManager.setLookAndFeel(new NimbusLookAndFeel());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Aconteceu um Erro Inesperado. Informe ao Analista do Sistema. Erro: " + e);
			}
			
			addWindowListener( new WindowAdapter() {
				public void windowClosing(WindowEvent arg0) {
					try {
						UIManager.setLookAndFeel(new SyntheticaWhiteVisionLookAndFeel());
					} catch (Exception e) {
						showMessageDialog(null, "Informe o Seguinte Erro ao Analista: " + e.toString(), "Atencao", INFORMATION_MESSAGE);
					}
				}
			});
			
			setTitle("Urna Eletronica - Votacao");
			tratadorEventos = new TratadorEventosUrnaEletronica(this);

			qtdeCargos = 0;

			// Define todos os paineis como GridBagLayout.
			JPanel painelPrincipal = new JPanel(new GridBagLayout());
			JPanel painelBrasao = new JPanel(new GridBagLayout());
			painelTela = new JPanel(new GridBagLayout());
			JPanel painelBotoes = new JPanel(new GridBagLayout());
			JPanel painelBotoesNumericos = new JPanel(new GridBagLayout());
			JPanel painelBraCorCon = new JPanel(new GridBagLayout());

			// Customizacao dos paineis.
			painelBotoes.setBackground(Color.DARK_GRAY);
			painelBotoesNumericos.setBackground(Color.DARK_GRAY);
			painelBraCorCon.setBackground(Color.DARK_GRAY);
			painelBotoes.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

			// Customizacao dos paineis.
			painelBrasao.setBackground(Color.LIGHT_GRAY);
			painelBrasao.setPreferredSize(new Dimension(286, 86));
			painelBrasao.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			
			// Adicionando o logo da justica eleitoral.
			ImageIcon logoJusticaEleitoral = new ImageIcon(getClass().getResource("/icones/tse-logo.gif"));
			logoJusticaEleitoral.setImage(logoJusticaEleitoral.getImage().getScaledInstance(280, 80, 100));

			// Adicionando o label do logo da justica eleitoral no painelBrasao
			painelBrasao.add(new JLabel(logoJusticaEleitoral));

			// Customizando o painelTela.
			painelTela.setBackground(Color.LIGHT_GRAY);
			painelTela.setPreferredSize(new Dimension(500, 412));
			painelTela.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

			// Instanciando e definindo as propriedades dos botoes 0-9.
			for (int linha = 0; linha < 10; linha++)  {
				botoesNumericos[linha] = new JButton(" " + linha + " ");
				botoesNumericos[linha].setFont(new Font(null, Font.BOLD, 27));
				botoesNumericos[linha].setBackground(Color.BLACK);
				botoesNumericos[linha].setForeground(Color.WHITE);
				botoesNumericos[linha].addActionListener(tratadorEventos);
				botoesNumericos[linha].setFocusable(false);
				botoesNumericos[linha].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
			}

			// Criando os GridBagConstraints dos botoes.
			GridBagConstraints gridBotoes;
			gridBotoes = new GridBagConstraints();
			gridBotoes.insets = new Insets(5, 5, 5, 5);

			// Adicionando os botoes numericos no painelBotoesNumericos;
			int aux = 1;
			for (int x = 0; x < 3;x++) {
				int y = 1;
				while(y <= 3) {
					gridBotoes.gridx = y++;
					gridBotoes.gridy = x;
					painelBotoesNumericos.add(botoesNumericos[aux++],gridBotoes);
				}
			}

			// Adiciona o painelBotoesNumericos ao painelPrincipal.
			gridBotoes.gridx = 2;
			gridBotoes.gridy = 3;
			painelBotoesNumericos.add(botoesNumericos[0],gridBotoes);

			// Instanciando e definindo as propriedades do botao Branco.
			botaoBranco = new JButton("BRANCO");
			botaoBranco.setPreferredSize(new Dimension(79,40));
			botaoBranco.setBackground(Color.WHITE);
			botaoBranco.setFont(new Font(null, Font.BOLD, 12));
			botaoBranco.addActionListener(tratadorEventos);
			botaoBranco.setVerticalAlignment(JButton.TOP);
			botaoBranco.setFocusable(false);
			botaoBranco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

			// Instanciando e definindo as propriedades do botao Corrige.
			botaoCorrige = new JButton("CORRIGE");
			botaoCorrige.setPreferredSize(new Dimension(79,40));
			botaoCorrige.setBackground(new Color(250250250));
			botaoCorrige.setFont(new Font(null, Font.BOLD, 12));
			botaoCorrige.addActionListener(tratadorEventos);
			botaoCorrige.setVerticalAlignment(JButton.TOP);
			botaoCorrige.setFocusable(false);
			botaoCorrige.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

			// Instanciando e definindo as propriedades do botao Confirma.
			botaoConfirma = new JButton("CONFIRMA");
			botaoConfirma.setPreferredSize(new Dimension(90,50));
			botaoConfirma.setBackground(Color.GREEN);
			botaoConfirma.setFont(new Font(null, Font.BOLD, 12));
			botaoConfirma.addActionListener(tratadorEventos);
			botaoConfirma.setVerticalAlignment(JButton.TOP);
			botaoConfirma.setFocusable(false);
			botaoConfirma.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

			// Criando um novo GridBagConstraint para adicionar os botoes Branco, Corrige e Confirma no painelBraCorCon.
			GridBagConstraints gridBagConstraint = new GridBagConstraints();
			gridBagConstraint.insets = new Insets(5, 5, 5, 5);

			gridBagConstraint.anchor = GridBagConstraints.SOUTH;
			painelBraCorCon.add(botaoBranco, gridBagConstraint);
			gridBagConstraint.gridx = 1;
			painelBraCorCon.add(botaoCorrige, gridBagConstraint);
			gridBagConstraint.gridx = 2;
			painelBraCorCon.add(botaoConfirma, gridBagConstraint);
			gridBagConstraint.anchor = GridBagConstraints.CENTER;

			// Adicionando o painelBotoesNumericos e o painelBraCorCon no painelBotoes.
			gridBagConstraint.gridx = 0;
			gridBagConstraint.gridy = 0;
			gridBagConstraint.insets = new Insets(20, 0, 0, 0);
			painelBotoes.add(painelBotoesNumericos, gridBagConstraint);
			gridBagConstraint.gridy = 1;
			gridBagConstraint.insets = new Insets(0, 0, 0, 0);
			painelBotoes.add(painelBraCorCon, gridBagConstraint);

			gridBagConstraint.insets = new Insets(5, 5, 5, 5);

			// Adicioando o painelTela  ao painelPrincipal.
			gridBagConstraint.gridx = 0;
			gridBagConstraint.gridy = 0;
			gridBagConstraint.gridheight = 2;
			gridBagConstraint.insets = new Insets(20, 20, 10, 0);
			painelPrincipal.add(painelTela, gridBagConstraint);

			// Adicioando o painelBrasao ao painelPrincipal.
			gridBagConstraint.insets = new Insets(5, 5, 5, 5);
			gridBagConstraint.gridheight = 1;
			gridBagConstraint.gridx = 1;
			gridBagConstraint.gridy = 0;
			gridBagConstraint.insets = new Insets(20, 20, 0, 20);
			painelPrincipal.add(painelBrasao, gridBagConstraint);

			// Adicionando o painelBotoes ao painelPrincipal.
			gridBagConstraint.insets = new Insets(0, 0, 0, 0);
			gridBagConstraint.gridy = 1;
			gridBagConstraint.gridx = 1;
			painelPrincipal.add(painelBotoes, gridBagConstraint);

			// Adiciona o painelPrincipal ao Dialogo Urna Eletronica.
			add(painelPrincipal);

			GridBagConstraints c = new GridBagConstraints();

			JPanel painelNorte = new JPanel(new GridLayout(2,0));
			painelNorte.setBackground(Color.LIGHT_GRAY);

			seuVotoParaLabel = new JLabel("  SEU VOTO PARA");
			seuVotoParaLabel.setFont(new Font(null, Font.BOLD, 20));
			cargoLabel = new JLabel();
			
			atualizaCargoLabel();
			painelNorte.add(seuVotoParaLabel);
			painelNorte.add(cargoLabel);

			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.gridx = 0;
			c.gridy = 0;
			painelTela.add(painelNorte, c);

			setCaminhoFoto(TRANSPARENTE);
			Image img   = Toolkit.getDefaultToolkit().getImage(getClass().getResource(TRANSPARENTE));  
			Image menor = img.getScaledInstance(160, 175, Image.SCALE_DEFAULT);
			fotoCandidato = new ImageIcon(menor);
			labelFoto = new JLabel(fotoCandidato);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.FIRST_LINE_END;
			c.gridx = 1;
			c.gridy = 0;
			painelTela.add(labelFoto, c);

			JPanel painelCentro = new JPanel(new GridLayout(3,2));
			painelCentro.setBackground(Color.LIGHT_GRAY);
			painelCentro.setPreferredSize(new Dimension(400, 400));

			numeroLabel = new JLabel("  Numero: ");
			numeroLabel.setFont(new Font(null, Font.PLAIN, 22));
			painelCentro.add(numeroLabel);

			numeroCandidato = new JLabel(" ");
			numeroCandidato.setFont(new Font(null, Font.BOLD, 29));
			numeroCandidato.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); 
			painelCentro.add(numeroCandidato);

			nomeLabel = new JLabel("  Nome: ");
			nomeLabel.setFont(new Font(null, Font.PLAIN, 22));
			painelCentro.add(nomeLabel);

			nomeCandidato = new JLabel();
			nomeCandidato.setFont(new Font(null, Font.BOLD, 18));
			painelCentro.add(nomeCandidato);

			partidoLabel = new JLabel("  Partido: ");
			partidoLabel.setFont(new Font(null, Font.PLAIN, 22));
			painelCentro.add(partidoLabel);

			partidoCandidato = new JLabel();
			partidoCandidato.setFont(new Font(null, Font.BOLD, 18));
			painelCentro.add(partidoCandidato);

			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.LINE_START;
			c.gridx = 0;
			c.gridy = 1;
			painelTela.add(painelCentro, c);

			JPanel painelSul = new JPanel(new GridLayout(4,0));
			painelSul.setBackground(Color.LIGHT_GRAY);

			labelLinha = new JLabel("________________________________________________");
			painelSul.add(labelLinha);

			aperte = new JLabel("  Aperte a Tecla");
			painelSul.add(aperte);

			verdeConfirma = new JLabel("      VERDE para CONFIRMAR");
			painelSul.add(verdeConfirma);

			laranjaCorrige = new JLabel("  LARANJA para CORRIGIR");
			painelSul.add(laranjaCorrige);

			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.PAGE_END;
			c.insets = new Insets(40, 0, 5, 0);
			c.gridx = 0;
			c.gridy = 2;
			painelTela.add(painelSul, c);

			JPanel painelSulAux = new JPanel(new GridLayout(4,0));
			painelSulAux.setBackground(Color.LIGHT_GRAY);

			labelLinha2 = new JLabel("______________________");
			painelSulAux.add(labelLinha2);

			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.PAGE_END;
			c.insets = new Insets(40, 0, 5, 0);
			c.gridx = 1;
			c.gridy = 2;
			painelTela.add(painelSulAux, c);

			labelLinha.setVisible(false);
			labelLinha2.setVisible(false);

			// Definindo como falso a visualizacao de alguns componentes
			getNomeLabel().setVisible(false);
			getPartidoLabel().setVisible(false);
			getSeuVotoParaLabel().setVisible(false);
			getAperte().setVisible(false);
			getLaranjaCorrige().setVisible(false);
			getVerdeConfirma().setVisible(false);

			// Define as propriedades do Dialogo.
			pack();
			setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icones/icone.png")));
			setLocationRelativeTo(null);
			setModal(true);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setResizable(false);
			setVisible(true);
		}
	}

	/**
	 * Este metodo verifica se existe uma votacao cadastrada neste dia. Se sim ele retorna 1 caso contrario ele manda uma mensagem na tela informando o usuario que
	 * nao ha uma votacao marcada para este dia.
	 * 
	 * @return <code>int</code> com um numero de verificacao.
	 */
	private int verificaVotacao() {

		dataBaseVotacao = new BancoDadosVotacao();
		dataBaseVotacaoCargo = new BancoDadosVotacaoCargos();
		ResultSet resultado;

		Date data = new Date();

		// Obtem a data atual do sistema.
		String dataAtual = obterData(data);

		// Obtem a hora atual do sistema.
		int horaAtual = Integer.parseInt(data.toString().substring(11,13));

		try {

			// Inicia a conexao com o banco de dados.
			dataBaseVotacao.iniciaConexao();

			// Verifica se existe uma votacao marcada neste dia.
			int verifica = dataBaseVotacao.verificaVotacaoPorData(dataAtual);

			// Se nao existir uma votacao marcada para este dia, manda uma mensagem na tela informando o usuario.
			if (verifica == 0) {
				showMessageDialog(null, "Nao Existe Votacao Marcada Para Hoje", "Atencao", INFORMATION_MESSAGE);
			}

			// Caso contrario, se existir uma votacao marcada para hoje, verifica a hora da votacao.
			else {
				int horaInicio = 0;
				int horaFim = 0;

				// Obtem a hora inicio e fim da votacao cadastrada no sistema.
				resultado = dataBaseVotacao.obterVotacao(dataAtual);
				while (resultado.next()) {
					horaInicio = resultado.getInt("hora_inicio");
					horaFim = resultado.getInt("hora_fim");
				}

				// Fecha a conexao com o banco de dados.
				dataBaseVotacao.fechaConexao();

				// verifica se a hora atual e maior que a hora inicial e menor que a hora final que esta cadastrada no sistema.
				if (horaAtual < horaInicio) {
					showMessageDialog(null, "A Votacao Ira Comecar As " + horaInicio + "hs", "Atencao", INFORMATION_MESSAGE);
					return -1;
				}
				else if (horaAtual > horaFim) {
					showMessageDialog(null, "A Votacao Ja Encerrou As " + horaFim + "hs", "Atencao", INFORMATION_MESSAGE);
					return -1;
				}
				else {
					// inicia a conexao com o banco de dados.
					dataBaseVotacaoCargo.iniciaConexao();

					// Obtem a quantidade de cargos da votacao.
					qtdeCargos = dataBaseVotacaoCargo.quantidadeVotacaoCargos(dataAtual);
					DATA_VOTACAO = dataAtual;
					totalCargos = qtdeCargos;

					// Instancia dois vetores de cargos e digitos com a quantidade de cargos.
					cargos = new String[qtdeCargos];
					digitos = new int[qtdeCargos];

					// Busca no banco de dados a votacao cadastrada para a data atual.
					resultado = dataBaseVotacaoCargo.obterVotacaoCargos(dataAtual);
					qtdeCargos = 0;

					// Adiciona em cada posicao dos vetores os cargos e os digitos de cada cargo da votacao.
					while(resultado.next()) {
						cargos[qtdeCargos] = resultado.getString("cargo");
						digitos[qtdeCargos++] = resultado.getInt("digitos");
					}
					return 1;
				}
			}
			return -1;
		} catch (Exception e) {
			showMessageDialog(null, "Informe o Seguinte Erro ao Analista: " + e.toString(), "Atencao", INFORMATION_MESSAGE);
		}
		return -1;
	}	

	/**
	 * Este metodo atualiza a foto do candidato na urna eletronica.
	 * 
	 * @param caminhoFoto <code>String</code> com o endereco da imagem do candidato.
	 */
	public void addFotoCandidato(String caminhoFoto) {
		this.caminhoFoto = caminhoFoto;
		if (caminhoFoto.equalsIgnoreCase(TRANSPARENTE))
			fotoCandidato = new ImageIcon(getClass().getResource(caminhoFoto));
		else
			fotoCandidato = new ImageIcon(caminhoFoto);
		fotoCandidato.setImage(fotoCandidato.getImage().getScaledInstance(160, 175, Image.SCALE_DEFAULT));
		labelFoto.setIcon(fotoCandidato);
	}

	/**
	 * Este metodo recebe a data do sistema e cria uma string formatada com apenas o dia, mes e ano da data.
	 * 
	 * @param data <code>Date</code> com a data do sistema.
	 * @return <code>String</code> com a data atual do sistema,
	 */
	@SuppressWarnings("deprecation")
	public String obterData(Date data) {
		String ano = String.format("%d", data.getYear());
		ano = ano.substring(1);
		ano = "20" + ano;

		String mes = String.format("%d", data.getMonth()+1);
		if (mes.length() == 1)
			mes = "0" + mes;

		String dia = String.format("%d", data.getDate());
		if (dia.length() == 1)
			dia = "0" + dia;

		return ano + "-" + mes + "-" + dia;
	}

	/**
	 * Este metodo atualiza o CargoLabel do Dialogo da Urna Eletronica.
	 */
	public void atualizaCargoLabel() {
		if (getQtdeCargos() < getTotalCargos()) {

			if ( cargos[qtdeCargos].indexOf(" ") != -1 )
				cargoLabel.setText( "  " + cargos[qtdeCargos].substring(0,cargos[qtdeCargos].indexOf(" "))+ "(A)" + cargos[qtdeCargos].substring(cargos[qtdeCargos].indexOf(" ")));
			else
				cargoLabel.setText("  " + cargos[qtdeCargos] + "(A)");
			cargos[getQtdeCargos()].toString();
			cargoLabel.setFont(new Font(null, Font.PLAIN, 24));
		}
	}

	/**
	 * Este metodo retorna uma referencia dos cargos da votacao.
	 * 
	 * @return <code>String[]</code> com os cargos.
	 */
	public String[] getCargos() {
		return cargos;
	}

	/**
	 * Este metodo retorna uma referencia dos digitos da votacao.
	 * 
	 * @return <code>int[]</code> com os digitos.
	 */
	public int[] getDigitos() {
		return digitos;
	}

	/**
	 * Este metodo retorna uma referencia da quantidade de cargos da votacao.
	 * 
	 * @return <code>int</code> com a quantidade de cargos.
	 */
	public int getQtdeCargos() {
		return qtdeCargos;
	}

	/**
	 * Este metodo modifica a quantidade de cargos da votacao.
	 * 
	 * @param qtdeCargos <code>int</code> com a nova quantidade de cargos.
	 */
	public void setQtdeCargos(int qtdeCargos) {
		this.qtdeCargos = qtdeCargos;
	}

	/**
	 * Este metodo retorna uma referencia da labelCargo atual da votacao.
	 * 
	 * @return <code>JLabel</code> com o nome do cargo atual.
	 */
	public JLabel getCargoLabel() {
		return cargoLabel;
	}

	/**
	 * Este metodo retorna uma referencia da labelNumero da votacao.
	 * 
	 * @return <code>JLabel</code> com o numero.
	 */
	public JLabel getNumeroLabel() {
		return numeroLabel;
	}

	/**
	 * Este metodo retorna uma referencia da labelNome do candidato.
	 * 
	 * @return <code>JLabel</code> com o nome.
	 */
	public JLabel getNomeLabel() {
		return nomeLabel;
	}

	/**
	 * Este metodo retorna uma referencia da labelPartido do candidato.
	 * 
	 * @return <code>JLabel</code> com o partido.
	 */
	public JLabel getPartidoLabel() {
		return partidoLabel;
	}

	/**
	 * Este metodo retorna uma referencia da Label Numero do candidato.
	 * 
	 * @return <code>JLabel</code> com o numero.
	 */
	public JLabel getNumeroCandidato() {
		return numeroCandidato;
	}

	/**
	 * Este metodo retorna uma referencia da Label Nome do candidato.
	 * 
	 * @return <code>JLabel</code> com o nome.
	 */
	public JLabel getNomeCandidato() {
		return nomeCandidato;
	}

	/**
	 * Este metodo retorna uma referencia da Label Partido do candidato.
	 * 
	 * @return <code>JLabel</code> com o partido.
	 */
	public JLabel getPartidoCandidato() {
		return partidoCandidato;
	}

	/**
	 * Este metodo retorna uma referencia da Label SeuVotoPara.
	 * 
	 * @return <code>JLabel</code> com o seuVotoPara.
	 */
	public JLabel getSeuVotoParaLabel() {
		return seuVotoParaLabel;
	}

	/**
	 * Este metodo retorna uma referencia da Label VerdeConfirma.
	 * 
	 * @return <code>JLabel</code> com o verdeConfirma.
	 */
	public JLabel getVerdeConfirma() {
		return verdeConfirma;
	}

	/**
	 * Este metodo retorna uma referencia da Label LaranjaCorrige.
	 * 
	 * @return <code>JLabel</code> com o laranjaCorrige.
	 */
	public JLabel getLaranjaCorrige() {
		return laranjaCorrige;
	}

	/**
	 * Este metodo retorna uma referencia da Label Aperte.
	 * 
	 * @return <code>JLabel</code> com o aperte.
	 */
	public JLabel getAperte() {
		return aperte;
	}

	/**
	 * Este metodo retorna uma referencia da Label Linha.
	 * 
	 * @return <code>JLabel</code> com a linha.
	 */
	public JLabel getLabelLinha() {
		return labelLinha;
	}

	/**
	 * Este metodo retorna uma referencia da Label Linha2.
	 * 
	 * @return <code>JLabel</code> com a linha2.
	 */
	public JLabel getLabelLinha2() {
		return labelLinha2;
	}

	/**
	 * Este metodo retorna uma referencia do endereco da foto do candidato.
	 * 
	 * @return <code>String</code> com o endereco da Foto.
	 */
	public String getCaminhoFoto() {
		return caminhoFoto;
	}

	/**
	 * Este metodo modifica o endereco da foto do candidato.
	 * 
	 * @param caminhoFoto <code>String</code> com o endereco da Foto.
	 */
	public void setCaminhoFoto(String caminhoFoto) {
		this.caminhoFoto = caminhoFoto;
	}

	/**
	 * Este metodo adiciona no painelTela um novo painel com suas respectivas posicoes.
	 * 
	 * @param painelVotou <code>JPanel</code> com o painel Fim.
	 * @param c <code>GridBagConstraints</code> com a localizacao do BagLayout.
	 */
	public void setPainelTela(JPanel painelVotou, GridBagConstraints c) {
		this.painelTela.add(painelVotou, c);
	}

	/**
	 * Este metodo retorna uma referencia dos botoes numericos de 0 ate 9.
	 * 
	 * @return <code>JButton[]</code> com os botoes numericos.
	 */
	public JButton[] getBotoesNumericos() {
		return botoesNumericos;
	}

	/**
	 * Este metodo retorna uma referencia do botaoBranco do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao branco.
	 */
	public JButton getBotaoBranco() {
		return botaoBranco;
	}

	/**
	 * Este metodo retorna uma referencia do botaoCorrige do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao corrige.
	 */
	public JButton getBotaoCorrige() {
		return botaoCorrige;
	}

	/**
	 * Este metodo retorna uma referencia do botaoConfirma do Dialogo.
	 * 
	 * @return <code>JButton</code> com o botao confirma.
	 */
	public JButton getBotaoConfirma() {
		return botaoConfirma;
	}

	/**
	 * Este metodo retorna uma referencia da Data da votacao.
	 * 
	 * @return <code>String</code> com a data.
	 */
	public String getDATA_VOTACAO() {
		return DATA_VOTACAO;
	}

	/**
	 * Este metodo retorna uma referencia da quantidade total de cargos desta votacao.
	 * 
	 * @return <code>int</code> com o total de cargos.
	 */
	public int getTotalCargos() {
		return totalCargos;
	}
}
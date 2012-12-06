package com.sige.gui.pesquisa.eventos;

import static com.sige.recursos.Recurso.formataData;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sige.gui.pesquisa.DialogoCadastrarPesquisa;
import com.sige.gui.recursos.DialogoErro;
import com.sige.gui.recursos.DialogoSucesso;
import com.sige.persistencia.BancoDadosPesquisa;
import com.sige.persistencia.BancoDadosPesquisaCandidatos;

/**
 * Esta classe implementa um <code>ActionListener</code>. Esta classe trata os eventos da classe 
 * <code>DialogoCadastrarPesquisa</code>.
 *  
 * @author Charles Garrocho
 * 
 * @see DialogoCadastrarPesquisa
 */
public class TratadorEventosCadastroPesquisa implements ActionListener, MouseListener {

	private DialogoCadastrarPesquisa gui;
	private DefaultTableModel modeloTabela;
	private BancoDadosPesquisa dataBasePesquisa;
	private BancoDadosPesquisaCandidatos dataBasePesquisaCandidatos;

	/**
	 * Este e o construtor. Ele recebe como argumento o <code>DialogoCadastrarPesquisa</code> guarda a referencia dela em uma 
	 * variavel para poder tratar os eventos. O construtor instancia as classes de banco de dados necessarias para poder fazer
	 * as consultas e insercoes no banco de dados.
	 * 
	 * @param dialogoCadastrarPesquisa um <code>DialogoCadastrarPesquisa</code>
	 * @see DialogoCadastrarPesquisa
	 * @see BancoDadosPesquisa
	 * @see BancoDadosPesquisaCandidatos
	 */
	public TratadorEventosCadastroPesquisa(DialogoCadastrarPesquisa dialogoCadastrarPesquisa) {
		super();
		this.gui = dialogoCadastrarPesquisa;
		this.dataBasePesquisa = new BancoDadosPesquisa();
		this.dataBasePesquisaCandidatos = new BancoDadosPesquisaCandidatos();
	}

	/** Este metodo e implementado por que a classe <code>TratadorEventosCadastroPesquisa</code> implementa um ActionListener.
	 *  E por isso ela deve obrigatoriamente implementar os metodos abstratados dessa classe. Este metodo trata os eventos dos
	 *  botoes gravar e limpar.
	 */
	public void actionPerformed(ActionEvent evento) {

		// Verifica se o evento ocorreu no Botao cancelar.
		if (evento.getSource() == gui.getBotaoCancelar())
			gui.dispose();

		// Verifica se o evento ocorreu no Botao Gravar.
		else if (evento.getSource() == gui.getBotaoGravar()) {

			// Obtem os dados da nova pesquisa.
			String cargo = gui.getCargoField().getText();
			String branco = gui.getNumeroBrancoNuloField().getText();
			String indeciso = gui.getNumeroIndecisosField().getText();
			String entrevistado = gui.getNumeroEntrevistadosField().getText();
			String municipio = gui.getNumeroMunicipiosField().getText();
			String dataInicio = ((JTextField)gui.getDataInicioCalendario().getComponent(0)).getText();
			dataInicio = dataInicio.replace(".", "/");
			String dataFim = ((JTextField)gui.getDataFimCalendario().getComponent(0)).getText();
			dataFim = dataFim.replace(".", "/");

			// Verifica se os dados estao inseridos corretamente.
			if (dataInicio.length() == 0)
				new DialogoErro(gui, "Erro", "Forneca a Data Inicio da Pesquisa.");
			else if (dataInicio.length() > 14 || dataInicio.length() < 10) 
				new DialogoErro(gui, "Erro", "Forneca uma Data Inicio Valida para a Pesquisa.");
			else if (dataFim.length() == 0) 
				new DialogoErro(gui, "Erro", "Forneca a Data Fim da Pesquisa.");
			else if (dataFim.length() > 14 || dataFim.length() < 10) 
				new DialogoErro(gui, "Erro", "Forneca uma Data Fim Valida para a Pesquisa.");
			else {

				int dataInicioAux = Integer.parseInt(dataInicio.substring(6,10) + dataInicio.substring(3,5) + dataInicio.substring(0,2));
				int dataFimAux = Integer.parseInt(dataFim.substring(6,10) + dataFim.substring(3,5) + dataFim.substring(0,2));
				int ano = Integer.parseInt(dataInicio.substring(6,10));

				if (ano < 1995) {
					new DialogoErro(gui, "Erro", "O Ano Nao Pode Ser Menor Que 1995.");
				}
				else if (dataInicioAux > dataFimAux) {
					new DialogoErro(gui, "Erro", "A Data Fim Nao Pode Ser Anterior Que a Data Inicio.");
				}
				else if (branco.length() == 0 || branco.length() >= 10) {
					new DialogoErro(gui, "Erro", "Forneca uma Quantidade Valida de Votos Brancos ou Nulos da Pesquisa.");
				}
				else if (indeciso.length() == 0  || indeciso.length() >= 10) {
					new DialogoErro(gui, "Erro", "Forneca uma Quantidade Valida de Eleitores Indecisos da Pesquisa.");
				}
				else if (entrevistado.length() == 0 || entrevistado.length() >= 10) {
					new DialogoErro(gui, "Erro", "Forneca uma Quantidade Valida de Eleitores Entrevistados da Pesquisa.");
				}
				else if (Integer.parseInt(entrevistado) == 0) {
					new DialogoErro(gui, "Erro", "Forneca uma Quantidade Valida de Eleitores Entrevistados da Pesquisa.");
				}
				else if (municipio.length() == 0 || municipio.length() >= 10) {
					new DialogoErro(gui, "Erro", "Forneca uma Quantidade Valida de Municipios em Que a Pesquisa Foi Realizada.");
				}
				else if (Integer.parseInt(municipio) == 0) {
					new DialogoErro(gui, "Erro", "Forneca uma Quantidade Valida de Municipios em Que a Pesquisa Foi Realizada.");
				}
				else if (cargo.length() == 0) {
					new DialogoErro(gui, "Erro", "Forneca o Cargo da Pesquisa.");
				}
				else if (gui.getTabelaCandidato().getRowCount() == 0) {
					new DialogoErro(gui, "Erro", "Selecione um Candidato Para a Pesquisa.");
				}
				else {

					// Formata a data no formato de insercao no banco de dados.
					dataInicio = formataData(dataInicio);
					dataFim = formataData(dataFim);

					// Obtem a referencia da tabela e obtem a quantidade de linhas na tabela.
					modeloTabela = ((DefaultTableModel)(gui.getTabelaCandidato().getModel()));
					int qtdeCandidatos = modeloTabela.getRowCount();

					int soma = Integer.parseInt(indeciso) + Integer.parseInt(branco);

					for (int x = 0; x < qtdeCandidatos; x++)
						soma += Integer.parseInt(modeloTabela.getValueAt(x, 2).toString());

					if (soma != Integer.parseInt(entrevistado) )
						new DialogoErro(gui, "Erro", "A Quantidade de Entrevistados Nao Bate Com a Soma Dos Votos.");
					else {
						try {
							dataBasePesquisa.iniciaConexao();
							int verifica = dataBasePesquisa.verificaPesquisaDataInicio(cargo, dataInicio);
							verifica += dataBasePesquisa.verificaPesquisaDataInicio(cargo, dataInicio);
							if (verifica != 0){
								new DialogoErro(gui, "Erro", "Ja Existe Uma Pesquisa Cadastrada Nesse Periodo");
								dataBasePesquisa.fechaConexao();
							}
							else {
								int numero_pesquisa = dataBasePesquisa.quantidadePesquisa()+1;
								dataBasePesquisa.adicionarPesquisa(numero_pesquisa, dataInicio, dataFim, cargo, Integer.parseInt(branco), Integer.parseInt(indeciso), Integer.parseInt(entrevistado), Integer.parseInt(municipio));
								dataBasePesquisa.fechaConexao();

								dataBasePesquisaCandidatos.iniciaConexao();
								for (int x = 0; x < qtdeCandidatos; x++) 
									dataBasePesquisaCandidatos.adicionarPesquisaCandidatos(numero_pesquisa, modeloTabela.getValueAt(x, 1).toString(), Integer.parseInt(modeloTabela.getValueAt(x, 2).toString()));

								dataBasePesquisaCandidatos.fechaConexao();
								new DialogoSucesso(gui, "Sucesso", "Pesquisa Adicionada.");
								gui.dispose();
							}
						} catch (Exception e) {
							new DialogoErro(gui, "Erro", "Informe o Seguinte Erro ao Analista: " + e.toString());
						}
					}
				}
			}

		}

		/* Verifica se o evento ocorreu no Botao Limpar. Se sim seta o numero de linhas da tabela com 0, e limpa os dados dos
		   campos do dialogo. */
		else if (evento.getSource() == gui.getBotaoLimpar()) {
			modeloTabela = ((DefaultTableModel)(gui.getTabelaCandidato().getModel()));
			modeloTabela.setNumRows(0);
			gui.getCargoField().setText("");
			((JTextField)gui.getDataInicioCalendario().getComponent(0)).setText("");
			((JTextField)gui.getDataFimCalendario().getComponent(0)).setText("");
			gui.getNumeroBrancoNuloField().setText("");
			gui.getNumeroEntrevistadosField().setText("");
			gui.getNumeroIndecisosField().setText("");
			gui.getNumeroMunicipiosField().setText("");
		}
	}

	/** Este metodo e implementado por que a classe <code>TratadorEventosCadastroPesquisa</code> implementa um MouseListener.
	 *  E por isso ela deve obrigatoriamente implementar os metodos abstratados dessa classe. Quando o usuario da um clique
	 *  na tabela de candidatos, um evento e disparado, o metodo pega a posicao em que tabela foi clicada, obtem os dados dessa 
	 *  linha e exibe uma mensagem de confirmacao da exclusao desta linha da tabela.
	 */
	public void mouseClicked(MouseEvent arg0) {

		// Obtem a posicao onde a tabela foi clicada.
		int posicao = gui.getTabelaCandidato().getSelectedRow();

		// Obtem o nome do candidato.
		String nome = gui.getTabelaCandidato().getValueAt(posicao, 1).toString();

		// Exibe uma mensagem de confirmacao de exclusao do candidato.
		int op = JOptionPane.showConfirmDialog(gui, "Gostaria de Excluir " + nome + "?");
		if (op == 0) {
			modeloTabela = ((DefaultTableModel)(gui.getTabelaCandidato().getModel()));
			modeloTabela.removeRow(posicao);
		}
	}

	// Estes metodos nao sao implementados.
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
}

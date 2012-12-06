package com.sige.gui.votacao.eventos;

import static com.sige.recursos.Recurso.formataData;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sige.gui.recursos.DialogoErro;
import com.sige.gui.recursos.DialogoSucesso;
import com.sige.gui.votacao.DialogoCadastrarVotocao;
import com.sige.persistencia.BancoDadosCandidato;
import com.sige.persistencia.BancoDadosVotacao;
import com.sige.persistencia.BancoDadosVotacaoCargos;

/**
 * Esta classe implementa um <code>ActionListener</code> e um <code>MouseListener</code> e trata os eventos da 
 * classe <code>DialogoCadastrarVotocao</code>.
 *  
 * @author Charles Garrocho
 * @author Barbara Silveiro
 * 
 * @see DialogoCadastrarVotocao
 */
public class TratadorEventosCadastrarVotacao extends MouseAdapter implements ActionListener {

	private DialogoCadastrarVotocao gui;
	private DefaultTableModel modeloTabela;
	private BancoDadosVotacao dataBaseVotacao;
	private BancoDadosVotacaoCargos dataBaseVotacaoCargos;
	private BancoDadosCandidato dataBaseCandidato;

	/**
	 * Este e o construtor. Ele recebe como argumento o <code>DialogoCadastrarVotocao</code> guarda a referencia dela em uma 
	 * variavel para poder tratar os eventos. O construtor instancia as classes de banco de dados necessarias para poder fazer
	 * as consultas e insercoes no banco de dados.
	 * 
	 * @param dialogoCadastrarVotocao um <code>DialogoCadastrarVotocao</code>.
	 * 
	 * @see DialogoCadastrarVotocao
	 * @see BancoDadosVotacao
	 * @see BancoDadosVotacaoCargos
	 * @see BancoDadosCandidato
	 */
	public TratadorEventosCadastrarVotacao(DialogoCadastrarVotocao dialogoCadastrarVotocao) {
		super();
		this.gui = dialogoCadastrarVotocao;
		this.dataBaseVotacao = new BancoDadosVotacao();
		this.dataBaseVotacaoCargos = new BancoDadosVotacaoCargos();
		this.dataBaseCandidato = new BancoDadosCandidato();
	}

	/** 
	 * Este metodo e implementado por que a classe <code>TratadorEventosCadastrarVotacao</code> implementa um ActionListener.
	 * E por isso ela deve obrigatoriamente implementar os metodos abstratados dessa classe. Este metodo trata os eventos dos
	 * botoes gravar, cancelar e limpar.
	 */
	public void actionPerformed(ActionEvent evento) {

		// Verifica se o evento ocorreu no Botao Gravar.
		if (evento.getSource() == gui.getBotaoGravar()) {

			// Obtem os dados da nova pesquisa.
			String data = ((JTextField)gui.getData().getComponent(0)).getText();
			data = data.replace(".", "/");
			String horaInicio = gui.getHoraInicio().getText();
			String horaFim = gui.getHoraFim().getText();
			modeloTabela = ((DefaultTableModel)(gui.getTabelaCargo().getModel()));

			// Verifica se os dados estao inseridos corretamente.
			if (data.length() == 0) {
				new DialogoErro(gui, "Erro", "Forneca a Data da Votacao.");
			}
			else if (data.length() > 14 || data.length() < 10) {
				new DialogoErro(gui, "Erro", "Forneca uma Data Inicio Valida para a Votacao.");
			}
			else if (horaInicio.substring(0, 1).equalsIgnoreCase(" ")) {
				new DialogoErro(gui, "Erro", "Forneca a Hora Inicio da Votacao.");
			}
			else if (horaFim.substring(0, 1).equalsIgnoreCase(" ")) {
				new DialogoErro(gui, "Erro", "Forneca a Hora Fim da Votacao.");
			}
			else {

				// Formata a data no formato de insercao no banco de dados.
				data = formataData(data);

				int auxHoraInicio = Integer.parseInt(horaInicio);
				int auxHoraFim =  Integer.parseInt(horaFim);
				
				if (auxHoraInicio > 23) {
					new DialogoErro(gui, "Erro", "A Hora Inicio Nao Pode Ser Maior Que 23Hrs.");
				}
				else if (auxHoraFim > 23) {
					new DialogoErro(gui, "Erro", "A Hora Fim Nao Pode Ser Maior Que 23Hrs.");
				}
				else if (auxHoraInicio > auxHoraFim) {
					new DialogoErro(gui, "Erro", "A Hora Inicio Nao Pode Ser Maior Que a Hora Fim.");
				}
				else if (modeloTabela.getRowCount() == 0) {
					new DialogoErro(gui, "Erro", "Forneca Um Cargo Para a Eleicao.");
				}
				else {
					try {
						String Error = "Nao Existe Candidatos Cadastrados Para os Seguintes Cargos:";
						int verifica2 = 0;

						// Verifica se existem candidatos cadastrados para os cargos escolhidos.
						for (int x = 0; x < modeloTabela.getRowCount(); x++) {
							dataBaseCandidato.iniciaConexao();
							verifica2 = dataBaseCandidato.obterQuantidadeCandidatoCargo(modeloTabela.getValueAt(x, 1).toString());
							if (verifica2 == 0)
								Error += "\n" + modeloTabela.getValueAt(x, 1).toString();
							dataBaseCandidato.fechaConexao();
						}
						if ( !Error.equalsIgnoreCase("Nao Existe Candidatos Cadastrados Para os Seguintes Cargos:")) {
							new DialogoErro(gui, "Erro", Error);
						}
						else {

							dataBaseVotacao.iniciaConexao();
							int verifica = dataBaseVotacao.verificaVotacaoPorData(data);

							// Verifica se ja nao existe uma votacao cadastra na mesma data.
							if (verifica == 0) {
								dataBaseVotacao.adicionarVotacao(data, auxHoraInicio, auxHoraFim);
								dataBaseVotacao.fechaConexao();

								dataBaseVotacaoCargos.iniciaConexao();
								for (int x = 0; x < modeloTabela.getRowCount(); x++ )
									dataBaseVotacaoCargos.adicionarVotacaoCargos(data, modeloTabela.getValueAt(x,1).toString(),
											Integer.parseInt( modeloTabela.getValueAt(x,2).toString() ));
								dataBaseVotacaoCargos.fechaConexao();

								new DialogoSucesso(gui, "Sucesso", "Votacao Cadastrada.");
								gui.dispose();
							}
							else {
								new DialogoErro(gui, "Erro", "Ja Existe Uma Votacao Cadastrada Nessa Data");
								dataBaseVotacao.fechaConexao();
							}
						}
					} catch (Exception e) {
						new DialogoErro(gui, "Erro", "Informe o Seguinte Erro ao Analista: " + e.toString());
					}
				}
			}
		}

		// Verifica se o evento ocorreu no Botao cancelar. Se fim da um dispose no Dialogo e e encerrado o Dialogo.
		else if (evento.getSource() == gui.getBotaoCancelar()) {
			gui.dispose();
		}

		// Verifica se o evento ocorreu no Botao Limpar. Se sim todos os campos sao limpos.
		else if (evento.getSource() == gui.getBotaoLimpar()) {
			((JTextField)gui.getData().getComponent(0)).setText("");
			gui.getHoraInicio().setText("");
			gui.getHoraFim().setText("");

			// Seta a quantidade de linhas da tabela como 0.
			modeloTabela = ((DefaultTableModel)(gui.getTabelaCargo().getModel()));
			modeloTabela.setNumRows(0);
		}
	}

	/** Este metodo e implementado por que a classe <code>TratadorEventosCadastrarVotacao</code> extende um MouseAdapter.
	 *  E por isso ela deve obrigatoriamente implementar os metodos abstratados dessa classe. Quando o usuario da um clique
	 *  na tabela de cargos, um evento e disparado, o metodo pega a posicao em que tabela foi clicada, obtem os dados dessa 
	 *  linha e exibe uma mensagem de confirmacao da exclusao desta linha da tabela.
	 */
	public void mouseClicked(MouseEvent arg0) {
		
		// Obtem a posicao da linha em que a tabela foi clicada.
		int posicao = gui.getTabelaCargo().getSelectedRow();
		String nome = gui.getTabelaCargo().getValueAt(posicao, 1).toString();

		int op = JOptionPane.showConfirmDialog(gui, "Gostaria de Excluir " + nome + "?");
		if (op == 0) {
			modeloTabela = ((DefaultTableModel)(gui.getTabelaCargo().getModel()));
			modeloTabela.removeRow(posicao);
		}
	}
}

package com.sige.gui.partido.eventos;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.sige.gui.partido.DialogoCadastrarPartido;
import com.sige.persistencia.BancoDadosPartido;

/**
 * Esta classe implementa um <code>ActionListener</code>. Esta classe trata os eventos da classe 
 * <code>DialogoCadastrarPartido</code>.
 *  
 * @author Charles Garrocho
 * @author Barbara Silveiro
 * 
 * @see DialogoCadastrarPartido
 */
public class TratadorEventosCadastroPartido implements ActionListener {
	private DialogoCadastrarPartido gui;
	private BancoDadosPartido dataBasePartido;

	/**
	 * Este e o construtor. Ele recebe como argumento o <code>DialogoCadastrarPartido</code> guarda a referencia dela em uma 
	 * variavel para poder tratar os eventos. O construtor instancia as classes de banco de dados necessarias para poder fazer
	 * as consultas e insercoes no banco de dados.
	 * 
	 * @param dialogoCadastrarPartido <code>DialogoCadastrarPartido</code>.
	 * @see DialogoCadastrarPartido
	 * @see BancoDadosPartido
	 */
	public TratadorEventosCadastroPartido(DialogoCadastrarPartido dialogoCadastrarPartido) {
		this.gui = dialogoCadastrarPartido;
		this.dataBasePartido= new BancoDadosPartido();
	}

	/** Este metodo e implementado por que a classe <code>TratadorEventosCadastroPartido</code> implementa um ActionListener.
	 *  E por isso ela deve obrigatoriamente implementar os metodos abstratados dessa classe. Este metodo trata os eventos dos
	 *  botoes gravar, cancelar e dos Fields Nome, Sigla e Numero.
	 */
	public void actionPerformed(ActionEvent evento) {


		// Verifica se o evento ocorreu no Botao Gravar. Se sim chama o metodo gravarDados().
		if (evento.getSource() == gui.getBotaoGravar()) {
			
			// Obtem os dados do partido.
			String nome = gui.getFieldNome().getText();
			String sigla = gui.getFieldSigla().getText();
			String numero = gui.getFieldNumero().getText();

			// Verifica os dados do novo partido.
			if (nome.length() <= 5) {
				showMessageDialog(gui, "Especifique Melhor o Nome do Partido.", "Atencao", INFORMATION_MESSAGE);
			}
			else if (sigla.length() <= 1) {
				showMessageDialog(gui, "Especifique Melhor a Sigla.", "Atencao", INFORMATION_MESSAGE);
			}
			else {
				try {
					dataBasePartido.iniciaConexao();
					int verifica = dataBasePartido.verificaPartido(Integer.parseInt(numero));
		
					/* Verifica se nao existe um cargo com o mesmo numero. Se nao os dados sao gravados, caso contrario uma mensagem 
					 * de erro e exibida na tela informando o usuario que ja existe um partido cadastrado com o mesmo numero. */
					if (verifica != 0)
						showMessageDialog(gui, "Ja Existe Um Partido Com Esse Nome.", "Atencao", INFORMATION_MESSAGE);
					else {
						dataBasePartido.adicionarPartido(Integer.parseInt(numero), nome, sigla);
						showMessageDialog(gui, "Partido Adicionado.", "Sucesso", INFORMATION_MESSAGE);
						gui.dispose();
					}
					dataBasePartido.fechaConexao();
				} catch (Exception e) {
					showMessageDialog(gui, "Informe o Seguinte Erro ao Analista: " + e.toString(), "Atencao", INFORMATION_MESSAGE);
				}
			}
		}
		
		// Verifica se o evento ocorreu no Botao Gravar. Se sim chama o metodo gravarDados().
		else if (evento.getSource() == gui.getBotaoAlterar())  {
			
			// Obtem os dados do partido.
			String nome = gui.getFieldNome().getText();
			String sigla = gui.getFieldSigla().getText();
			String numero = gui.getFieldNumero().getText();

			// Obtem a opcao (cadastro, Alteracao) do usuario.
			int id = gui.getOpcao();

			// Verifica os dados do novo partido.
			if (nome.length() <= 5) {
				showMessageDialog(gui, "Especifique Melhor o Nome do Partido.", "Atencao", INFORMATION_MESSAGE);
			}
			else if (sigla.length() <= 1) {
				showMessageDialog(gui, "Especifique Melhor a Sigla.", "Atencao", INFORMATION_MESSAGE);
			}
			else {
				try {
					dataBasePartido.iniciaConexao();
					if (id != Integer.parseInt(numero)) {
						int verifica = dataBasePartido.verificaPartido(Integer.parseInt(numero));
						if (verifica != 0)
							showMessageDialog(gui, "Ja Existe Um Partido Com Esse Nome.", "Atencao", INFORMATION_MESSAGE);
						else {
							dataBasePartido.alterarPartido(id,Integer.parseInt(numero), nome, sigla);
							showMessageDialog(gui, "Partido Alterado.", "Sucesso", INFORMATION_MESSAGE);
							gui.dispose();
						}
					}
					else {
						dataBasePartido.alterarPartido(id,Integer.parseInt(numero), nome, sigla);
						showMessageDialog(gui, "Partido Alterado.", "Sucesso", INFORMATION_MESSAGE);
						gui.dispose();
					}
					dataBasePartido.fechaConexao();
				} catch (Exception e) {
					showMessageDialog(gui, "Informe o Seguinte Erro ao Analista: " + e.toString(), "Atencao", INFORMATION_MESSAGE);
				}	
			}
		}
		// Verifica se o evento ocorreu no Botao Cancelar. Se sim da um dispose e o Dialogo e encerrado.
		else if (evento.getSource() == gui.getBotaoCancelar()) {
			gui.dispose();
		}
		
		else if ( evento.getSource() == gui.getBotaoExcluir()) {
			// Obtem os dados do partido.
			String nome = gui.getFieldNome().getText();
			int op = showConfirmDialog(gui, "Gostaria de Excluir " + nome + "?");
			if (op == 0) {
				try {
					dataBasePartido.iniciaConexao();
					dataBasePartido.excluirPartido(gui.getOpcao());
					dataBasePartido.fechaConexao();			
				} catch (Exception e1) {
					showMessageDialog(gui, "Informe o Seguinte Erro ao Analista: " + e1.toString(), "Atencao", INFORMATION_MESSAGE);
				}
			}
			gui.dispose();
		}
	} // actionPerformed().

} // class TratadorEventosCadastroPartido.
package com.sige.gui.cargo.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.sige.gui.cargo.DialogoCadastrarCargo;
import com.sige.gui.recursos.DialogoErro;
import com.sige.gui.recursos.DialogoSucesso;
import com.sige.persistencia.BancoDadosCargo;

/**
 * Esta classe implementa um <code>ActionListener</code>. Esta classe trata os eventos da classe 
 * <code>DialogoCadastrarCargo</code>.
 *  
 * @author Charles Garrocho
 * @author Barbara Silveiro
 * 
 * @see DialogoCadastrarCargo
 */
public class TratadorEventosCadastroCargo implements ActionListener {
	private DialogoCadastrarCargo gui;
	private int id;
	private BancoDadosCargo dataBaseCargo;

	/**
	 * Este e o construtor. Ele recebe como argumento o <code>DialogoCadastrarCargo</code> guarda a referencia dela em uma 
	 * variavel para poder tratar os eventos. O construtor instancia as classes de banco de dados necessarias para poder fazer
	 * as consultas e insercoes no banco de dados.
	 * 
	 * @param dialogoCadastrarCargo um <code>DialogoCadastrarCargo</code>.
	 * @param id <code>int</code> com o numero do cargo a ser alterado.
	 * @see DialogoCadastrarCargo
	 * @see BancoDadosCargo
	 */
	public TratadorEventosCadastroCargo(DialogoCadastrarCargo dialogoCadastrarCargo, int id) {
		this.gui = dialogoCadastrarCargo;
		this.id = id;
		this.dataBaseCargo= new BancoDadosCargo();
	}

	/** Este metodo e implementado por que a classe <code>TratadorEventosCadastroCargo</code> implementa um ActionListener.
	 *  E por isso ela deve obrigatoriamente implementar os metodos abstratados dessa classe. Este metodo trata os eventos dos
	 *  botoes cancelar, gravar e dos Fields Nome e Digitos.
	 */
	public void actionPerformed(ActionEvent evento) {
		
		// Verifica se o evento ocorreu no botao Gravar. Se sim chama o metodo GravarDados();
		if (evento.getSource() == gui.getBotaoGravar()) {
			// Obtem os dados do cargo.
			String nome = gui.getFieldNome().getText();
			String digitos = gui.getFieldDigitos().getText();
			
			// Verifica os dados do novo cargo.
			if (nome.length() <= 5) {
				new DialogoErro(gui, "Erro", "Especifique Melhor o Nome do Cargo.");
			}
			else if (digitos.length() == 0) {
				new DialogoErro(gui, "Erro", "Defina a Quantidade de Digitos.");
			}
			else if (Integer.parseInt(digitos) == 0) {
				new DialogoErro(gui, "Erro", "Os Digitos Nao Poder Ser Zero.");
			}
			else {

				/* Verifica se nao existe um cargo com o mesmo nome. Se nao os dados sao gravados, caso contrario uma mensagem de
				 * erro e exibida na tela informando o usuario que ja existe um cargo cadastrado com o mesmo nome. */
				try {
					dataBaseCargo.iniciaConexao();
					int verifica = dataBaseCargo.verificaCargo(nome);
					if (verifica != 0)
						new DialogoErro(gui, "Erro", "Ja Existe Um Cargo Com Esse Nome.");
					else {
						dataBaseCargo.adicionarCargo(dataBaseCargo.quantidadeCargos()+1, nome, Integer.parseInt(digitos));
						new DialogoSucesso(gui, "Sucesso", "Cargo Adicionado");
						gui.dispose();
					}
					dataBaseCargo.fechaConexao();
				} catch (Exception e) {
					new DialogoErro(gui, "Erro", "Informe o Seguinte Erro ao Analista: " + e.toString());
				}
			}
		}

		// Verifica se o evento ocorreu no botao Cancelar. Se sim da um dispose no dialogo e o dialogo e encerrado.
		else if (evento.getSource() == gui.getBotaoCancelar()) {
			gui.dispose();
		}
		
		// Verifica se o evento ocorreu no botao Cancelar. Se sim da um dispose no dialogo e o dialogo e encerrado.
		else if (evento.getSource() == gui.getBotaoAlterar()) {
			
			// Obtem os dados do cargo.
			String nome = gui.getFieldNome().getText();
			String digitos = gui.getFieldDigitos().getText();
			
			// Verifica os dados do novo cargo.
			if (nome.length() <= 5) {
				new DialogoErro(gui, "Erro", "Especifique Melhor o Nome do Cargo.");
			}
			else if (digitos.length() == 0) {
				new DialogoErro(gui, "Erro", "Defina a Quantidade de Digitos.");
			}
			else if (Integer.parseInt(digitos) == 0) {
				new DialogoErro(gui, "Erro", "Os Digitos Nao Poder Ser Zero.");
			}
			else {
				try {
					dataBaseCargo.iniciaConexao();
					dataBaseCargo.alterarCargo(id, nome, Integer.parseInt(digitos));
					new DialogoSucesso(gui, "Sucesso", "Cargo Alterado.");
					gui.dispose();
					dataBaseCargo.fechaConexao();
				} catch (Exception e) {
					new DialogoErro(gui, "Erro", "Informe o Seguinte Erro ao Analista: " + e.toString());
				}	
				gui.dispose();
			}
		}

		// Verifica se o evento ocorreu no botao Cancelar. Se sim da um dispose no dialogo e o dialogo e encerrado.
		else if (evento.getSource() == gui.getBotaoExcluir()) {
			String nome = gui.getFieldNome().getText();
			int op = JOptionPane.showConfirmDialog(gui, "Gostaria de Excluir " + nome + "?");
			if (op == 0) {
				try {
					dataBaseCargo.iniciaConexao();
					dataBaseCargo.excluirCargo(id);
					dataBaseCargo.fechaConexao();
					gui.dispose();
				} catch (Exception e1) {
					new DialogoErro(gui, "Erro", "Informe o Seguinte Erro ao Analista: " + e1.toString());
				}
			}
		}
	}  // actionPerformed

} // classe TratadorEventosCadastroCargo.
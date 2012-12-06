package com.sige.gui.lookup.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sige.gui.lookup.LookUpCargo;
import com.sige.gui.recursos.DialogoErro;
import com.sige.persistencia.BancoDadosCargo;

/**
 * Esta classe extende um <code>KeyAdapter</code> e implementa ActionListener e MouseListener. Esta classe trata os eventos da
 * classe <code>LookUpCargo</code>.
 *  
 * @author Charles Garrocho
 * 
 * @see LookUpCargo
 */
public class TratadorEventosLookUpCargo extends KeyAdapter implements ActionListener, MouseListener {
	private LookUpCargo gui;
	private BancoDadosCargo dataBaseCargo;
	private DefaultTableModel modeloTabela;

	/**
	 * Este e o construtor. Ele recebe como argumento o <code>LookUpCargo</code> guarda a referencia dela em uma 
	 * variavel para poder tratar os eventos. O construtor instancia um novo <code>BancoDadosCargo</code> para poder
	 * fazer as consultas no banco de dados.
	 * 
	 * @param lookUpCargo um <code>LookUpCargo</code> com o dialogo.
	 * @see BancoDadosCargo
	 * @see LookUpCargo
	 */
	public TratadorEventosLookUpCargo(LookUpCargo lookUpCargo) {
		this.gui = lookUpCargo;
		this.dataBaseCargo = new BancoDadosCargo();
	}

	/** Este metodo e implementado por que a classe <code>TratadorEventosLookUpCargo</code> implementa um ActionListener.
	 *  E por isso ela deve obrigatoriamente implementar os metodos abstratados dessa classe. Este metodo trata os eventos dos
	 *  botoes sair e limpar.
	 */
	public void actionPerformed(ActionEvent evento) {

		// Caso o evento tenha ocorrido no botao cancelar.
		if (evento.getSource() == gui.getBotaoCancelar()) {
			gui.dispose();
		}

		// Caso o evento tenha ocorrido no botao limpar.
		else if (evento.getSource() == gui.getBotaoLimpar()) {
			modeloTabela = ((DefaultTableModel)(gui.getTabelaCargos().getModel()));
			modeloTabela.setNumRows(0);
		}
	} // actionPerformed()

	/** A classe <code>TratadorEventosLookUpCargo</code> extende um KeyAdapter. Por esse motivo ela pode implementar o metodo
	 * keyReleased. Este metodo trata os eventos do teclado. A cada digito teclado uma acao ocorre.
	 */
	public void keyReleased(KeyEvent evento) {

		// Obtem o nome digitado.
		String nome = gui.getFieldNome().getText();

		modeloTabela = ((DefaultTableModel)(gui.getTabelaCargos().getModel()));
		modeloTabela.setNumRows(0);

		// Adiciona a variavel "%" para pesquisar todos os cargos a partir das letras ja inseridas.
		if (nome.length() != 0) {
			nome += "%";
			try {
				dataBaseCargo.iniciaConexao();
				int verifica = dataBaseCargo.verificaCargo(nome);

				// Verifica se existe algum cargo com o nome digitado.
				if (verifica != 0) {

					// Faz a busca no banco de dados, e armazena o resultado em uma linha da tabela.
					Object[] linha = new Object[3];
					ResultSet resultado = dataBaseCargo.obterCargo(nome + "%");
					while(resultado.next())  
					{  
						linha[0] = String.format("%d",resultado.getInt("numero"));
						linha[1] = resultado.getString("nome");  
						linha[2] = String.format("%d",resultado.getInt("digitos"));		      
						modeloTabela.addRow(linha);
					}
				}
				dataBaseCargo.fechaConexao();
			} catch (Exception e) {
				new DialogoErro(gui, "Erro", "Informe o Seguinte Erro ao Analista: " + e.toString());
			}
		}
	}

	/** Este metodo e implementado por que a classe <code>TratadorEventosLookUpCargo</code> implementa um MouseListener.
	 *  E por isso ela deve obrigatoriamente implementar os metodos abstratados dessa classe. Quando o usuario da um clique
	 *  na tabela de cargos, um evento e disparado, o metodo pega a posicao em que tabela foi clicada, obtem os dados dessa 
	 *  linha e manda uma mensagem ao usuario perguntado o numero de votos do candidato selecionado.
	 */
	public void mouseClicked(MouseEvent e) {

		// Obtem a posicao da linha que foi clicada.
		int posicao = gui.getTabelaCargos().getSelectedRow();

		// Obtem os dados da linha.
		String numero = gui.getTabelaCargos().getValueAt(posicao, 0).toString();
		String nome = gui.getTabelaCargos().getValueAt(posicao, 1).toString();
		String digitos = gui.getTabelaCargos().getValueAt(posicao, 2).toString();

		// Verifica se o componente que o usuario passou e um JTextField, se sim adiciona o nome do cargo selecionado no JTextField Cargo.
		if (gui.getCargoAux() instanceof JTextField) {
			if (nome.length() != 0)
				gui.getCargo().setText(nome);
			gui.dispose();
		}

		// Verifica se o componente que o usuario passou e um JTable, se sim adiciona o numero, nome e digitos na tabela de cargos.
		if (gui.getCargoAux() instanceof JTable) {
			modeloTabela = ( (DefaultTableModel)(gui.getTabelaCargo().getModel()) );

			// Verifica se o cargo nao ja foi adicionado anteriormente na tabela de cargos.
			posicao = 0;
			for (int x = 0; x < modeloTabela.getRowCount(); x++)
				if (nome.equalsIgnoreCase(modeloTabela.getValueAt(x, 1).toString()))
					posicao = 1;

			if (posicao == 0) {
				modeloTabela = ((DefaultTableModel)(gui.getTabelaCargo().getModel()));
				Object[] linha = new Object[3];
				linha[0] = numero;
				linha[1] = nome;
				linha[2] = digitos;
				modeloTabela.addRow(linha);
				gui.dispose();
			}
			else
				new DialogoErro(gui, "Erro", "Este Cargo Ja Esta Adicionado.");
		}
	}

	// Os metodos abaixo nao sao implementados.
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {	
	}
	public void mouseReleased(MouseEvent e) {
	}
} // classe TratadorEventosLookUpCargo. 
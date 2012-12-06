package com.sige.gui.lookup.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.sige.gui.lookup.LookUpCandidato;
import com.sige.gui.recursos.DialogoErro;
import com.sige.persistencia.BancoDadosCandidato;

/**
 * Esta classe extende um <code>KeyAdapter</code> e implementa ActionListener e MouseListener. Esta classe trata os eventos da
 * classe <code>LookUpCandidato</code>.
 *  
 * @author Charles Garrocho
 * 
 * @see LookUpCandidato
 */
public class TratadorEventosLookUpCandidato extends KeyAdapter implements ActionListener, MouseListener {
	private LookUpCandidato gui;
	private BancoDadosCandidato dataBaseCandidato;
	private DefaultTableModel modeloTabela;

	/**
	 * Este e o construtor. Ele recebe como argumento o <code>LookUpCandidato</code> guarda a referencia dela em uma 
	 * variavel para poder tratar os eventos. O construtor instancia um novo <code>BancoDadosCandidato</code> para poder
	 * fazer as consultas no banco de dados.
	 * 
	 * @param lookUpCandidato um <code>LookUpCandidato</code> com o dialogo.
	 * @see BancoDadosCandidato
	 * @see LookUpCandidato
	 */
	public TratadorEventosLookUpCandidato(LookUpCandidato lookUpCandidato) {
		this.gui = lookUpCandidato;
		this.dataBaseCandidato = new BancoDadosCandidato();
	}

	/** Este metodo e implementado por que a classe <code>TratadorEventosLookUpCandidato</code> implementa um ActionListener.
	 *  E por isso ela deve obrigatoriamente implementar os metodos abstratados dessa classe. Este metodo trata os eventos dos
	 *  botoes sair e limpar.
	 */
	public void actionPerformed(ActionEvent event) {

		// Caso o evento tenha ocorrido no botao limpar.
		if (event.getSource() == gui.getBotaoLimpar()) {

			modeloTabela = ((DefaultTableModel)(gui.getTabelaCandidatos().getModel()));
			modeloTabela.setNumRows(0);
		}

		// Caso o evento tenha ocorrido no botao sair.
		else if (event.getSource() == gui.getBotaoSair()) {
			gui.dispose();
		}		
	} // actionPerformed()

	/** A classe <code>TratadorEventosLookUpCandidato</code> extende um KeyAdapter. Por esse motivo ela pode implementar o metodo
	 * keyReleased. Este metodo trata os eventos do teclado. A cada digito teclado uma acao ocorre.
	 */
	public void keyReleased(KeyEvent event) {

		// Obtem os dados dos campos.
		String nome = gui.getFieldNome().getText();
		String cargo = gui.getCargo();
		ResultSet resultado = null;

		int verifica = 0;

		modeloTabela = ((DefaultTableModel)(gui.getTabelaCandidatos().getModel()));
		modeloTabela.setNumRows(0);

		// Adiciona a variavel "%" para pesquisar todos os candidatos a partir das letras ja inseridas.
		if (nome.length() != 0)
			nome += "%";

		try {
			dataBaseCandidato.iniciaConexao();
			verifica = dataBaseCandidato.verificaCandidatoPorCargo(nome,cargo);

			// Verifica se existe algum candidato com a partir do cargo especificado.
			if ( verifica > 0) {
				
				// Faz a busca no banco de dados, e armazena o resultado em uma linha da tabela.
				resultado = dataBaseCandidato.obterCandidatoPorCargo(nome,cargo);
				Object[] linha = new Object[4];
				while(resultado.next())  
				{  
					linha[0] = String.format("%d",resultado.getInt("numero"));
					linha[1] = resultado.getString("nome");  
					linha[2] = resultado.getString("partido");  
					linha[3] = resultado.getString("cargo");		      
					modeloTabela.addRow(linha);
				}
			}
			dataBaseCandidato.fechaConexao();
		} catch (Exception e) {
			new DialogoErro(gui, "Erro", "Informe o Seguinte Erro ao Analista: " + e.toString());
		}
	}

	/** Este metodo e implementado por que a classe <code>TratadorEventosLookUpCandidato</code> implementa um MouseListener.
	 *  E por isso ela deve obrigatoriamente implementar os metodos abstratados dessa classe. Quando o usuario da um clique
	 *  na tabela de candidatos, um evento e disparado, o metodo pega a posicao em que tabela foi clicada, obtem os dados dessa 
	 *  linha e manda uma mensagem ao usuario perguntado o numero de votos do candidato selecionado.
	 */
	public void mouseClicked(MouseEvent e) {

		// Obtem a linha que a linha foi clicada.
		int posicao = gui.getTabelaCandidatos().getSelectedRow();

		// Obtem os dados dessa linha.
		String numero = gui.getTabelaCandidatos().getValueAt(posicao, 0).toString();
		String nome = gui.getTabelaCandidatos().getValueAt(posicao, 1).toString();
		String votos;
		boolean achou = false;

		// Veriica se ja nao existe um candidato adicionado na tabela de candidatos.
		if (verificaCandidato(numero))
			achou = true;
		while (achou == true) {
			votos = JOptionPane.showInputDialog(gui,"Quantidade de Votos");
			if (votos != null) {
				if (votos.matches("[0-9]+") && votos.length() < 10) {
					Object[] linha = new Object[3];
					modeloTabela = ((DefaultTableModel)(gui.getTabelaCandidato().getModel()));
					linha[0] = numero;
					linha[1] = nome;
					linha[2] = votos;  
					modeloTabela.addRow(linha);
					achou = false;
					gui.dispose();
				}
			}
			else
				achou = false;
		}
	}

	/**
	 * Verifica se nao ja existe um candidato adicionado na tabela de candidatos. Se sim retorna false, caso contrario retorna true.
	 * 
	 * @param numero um <code>String</code> com o numero do candidato.
	 * @return um <code>boolean</code> com true caso haja um candidato ou falso caso contrario.
	 */
	private boolean verificaCandidato(String numero) {
		DefaultTableModel modeloTabelaAux = ((DefaultTableModel)(gui.getTabelaCandidato().getModel()));
		int tamanhoTabela = modeloTabelaAux.getRowCount();
		for (int x = 0; x < tamanhoTabela; x++)
			if (modeloTabelaAux.getValueAt(x, 0).toString().equalsIgnoreCase(numero))
				return false;
		return true;
	}

	// Os metodos abaixo nao sao implementados.
	public void mouseExited(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
} // classe TratadorEventosLookUpCandidato.
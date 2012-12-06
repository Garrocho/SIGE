package com.sige.gui.lookup.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;

import javax.swing.table.DefaultTableModel;

import com.sige.gui.lookup.LookUpPartido;
import com.sige.gui.recursos.DialogoErro;
import com.sige.persistencia.BancoDadosCandidato;
import com.sige.persistencia.BancoDadosPartido;

/**
 * Esta classe extende um <code>KeyAdapter</code> e implementa ActionListener e MouseListener. Esta classe trata os eventos da
 * classe <code>LookUpPartido</code>.
 *  
 * @author Charles Garrocho
 * 
 * @see LookUpPartido
 */
public class TratadorEventosLookUpPartido extends KeyAdapter implements ActionListener, MouseListener {
	private LookUpPartido gui;
	private BancoDadosPartido dataBasePartido;
	private DefaultTableModel modeloTabela;

	/**
	 * Este e o construtor. Ele recebe como argumento o <code>LookUpPartido</code> guarda a referencia dela em uma 
	 * variavel para poder tratar os eventos. O construtor instancia um novo <code>BancoDadosPartido</code> para poder
	 * fazer as consultas no banco de dados.
	 * 
	 * @param lookUpPartido um <code>LookUpPartido</code> com o dialogo.
	 * @see BancoDadosCandidato
	 * @see BancoDadosPartido
	 */
	public TratadorEventosLookUpPartido(LookUpPartido lookUpPartido) {
		this.gui = lookUpPartido;
		this.dataBasePartido = new BancoDadosPartido();
	}
	
	/** Este metodo e implementado por que a classe <code>TratadorEventosLookUpPartido</code> implementa um ActionListener.
	 *  E por isso ela deve obrigatoriamente implementar os metodos abstratados dessa classe. Este metodo trata os eventos dos
	 *  botoes sair e limpar.
	 */
	public void actionPerformed(ActionEvent evento) {
		
		// Caso o evento tenha ocorrido no botao sair.
		if (evento.getSource() == gui.getBotaoSair()) {
			gui.dispose();
		}
		
		// Caso o evento tenha ocorrido no botao limpar.
		else if (evento.getSource() == gui.getBotaoLimpar()) {
			
			modeloTabela = ((DefaultTableModel)(gui.getTabelaPartidos().getModel()));
			modeloTabela.setNumRows(0);
		}
		
	} // actionPerformed()
	
	/** A classe <code>TratadorEventosLookUpPartido</code> extende um KeyAdapter. Por esse motivo ela pode implementar o metodo
	 * keyReleased. Este metodo trata os eventos do teclado. A cada digito teclado uma acao ocorre.
	 */
	public void keyReleased(KeyEvent evento) {
		
		// Obtem o nome digitado do partido.
		String nome = gui.getFieldNome().getText();
		
		modeloTabela = ((DefaultTableModel)(gui.getTabelaPartidos().getModel()));
		modeloTabela.setNumRows(0);
		
		// Adiciona a variavel "%" para pesquisar todos os partidos a partir das letras ja inseridas.
		if (nome.length() != 0) {
			nome += "%";
			
			try {
				dataBasePartido.iniciaConexao();
				int verifica = dataBasePartido.verificaPartido(nome);
				
				// Verifica se existe algum partido a partido do nome especificado.
				if (verifica != 0) {
					
					// Faz a busca no banco de dados, e armazena o resultado em uma linha da tabela.
					Object[] linha = new Object[3];
					ResultSet resultado = dataBasePartido.obterPartido(nome + "%");
					while(resultado.next())  
					{  
						linha[0] = String.format("%d",resultado.getInt("numero"));
						linha[1] = resultado.getString("nome");  
						linha[2] = resultado.getString("sigla");		      
						modeloTabela.addRow(linha);
					}
					dataBasePartido.fechaConexao();
				}
			} catch (Exception e) {
				new DialogoErro(gui, "Erro", "Informe o Seguinte Erro ao Analista: " + e.toString());
			}
		}
	}
	
	/** Este metodo e implementado por que a classe <code>TratadorEventosLookUpPartido</code> implementa um MouseListener.
	 *  E por isso ela deve obrigatoriamente implementar os metodos abstratados dessa classe. Quando o usuario da um clique
	 *  na tabela de partidos, um evento e disparado, o metodo pega a posicao em que tabela foi clicada, obtem os dados dessa 
	 *  linha e seta o JTextField Partido.
	 */
	public void mouseClicked(MouseEvent e) {
		
		// Obtem a posicao em que a linha da tabela foi clicada.
		int posicao = gui.getTabelaPartidos().getSelectedRow();
		
		// Obtem o nome do partido da linha.
		String nome = modeloTabela.getValueAt(posicao, 2).toString();
		
		// Adiciona o nome no JTextField Partido.
		if (nome.length() != 0)
			gui.getPartido().setText(nome);
		gui.dispose();
	}
	
	// Os metodos abaixo nao sao implementados
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {	
	}
	public void mouseReleased(MouseEvent e) {
	}
} // classe TratadorEventosLookUpPartido.
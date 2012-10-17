package com.sige.gui.partido.eventos;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;

import javax.swing.table.DefaultTableModel;

import com.sige.gui.partido.DialogoCadastrarPartido;
import com.sige.gui.partido.DialogoConsultarPartido;
import com.sige.persistencia.BancoDadosPartido;

/**
 * Esta classe extende um <code>KeyAdapter</code> e implementa ActionListener e MouseListener. Esta classe trata os eventos da
 * classe <code>DialogoConsultarPartido</code>.
 *  
 * @author Charles Garrocho
 * @author Barbara Silveiro
 * 
 * @see DialogoConsultarPartido
 */
public class TratadorEventosConsultaPartido extends KeyAdapter implements ActionListener, MouseListener {
	private DialogoConsultarPartido gui;
	private BancoDadosPartido dataBasePartido;
	private DefaultTableModel modeloTabela;

	/**
	 * Este e o construtor. Ele recebe como argumento o <code>DialogoConsultarPartido</code> guarda a referencia dela em uma 
	 * variavel para poder tratar os eventos. O construtor instancia um novo <code>BancoDadosPartido</code> para poder
	 * fazer as consultas no banco de dados.
	 * 
	 * @param dialogoConsultarPartido um <code>DialogoConsultarPartido</code>.
	 * 
	 * @see DialogoConsultarPartido
	 * @see BancoDadosPartido
	 */
	public TratadorEventosConsultaPartido(DialogoConsultarPartido dialogoConsultarPartido) {
		this.gui = dialogoConsultarPartido;
		this.dataBasePartido = new BancoDadosPartido();
	}

	/** Este metodo e implementado por que a classe <code>TratadorEventosConsultaPartido</code> implementa um ActionListener.
	 *  E por isso ela deve obrigatoriamente implementar os metodos abstratados dessa classe. Este metodo trata os eventos dos
	 *  botoes sair e limpar.
	 */
	public void actionPerformed(ActionEvent evento) {

		// Caso o usuario tenha clicado no botao sair. Da um dispose na janela e o dialogo e encerrado.
		if (evento.getSource() == gui.getBotaoSair()) {
			gui.dispose();
		}

		// Caso o usuario tenha clicado no botao limpar. Seta o numero de linhas da tabela com 0.
		else if (evento.getSource() == gui.getBotaoLimpar()) {
			modeloTabela = ((DefaultTableModel)(gui.getTabelaPartidos().getModel()));
			modeloTabela.setNumRows(0);
		}
	} // actionPerformed()

	/** A classe <code>TratadorEventosConsultaPartido</code> extende um KeyAdapter. Por esse motivo ela pode implementar o metodo
	 * keyReleased. Este metodo trata os eventos do teclado. A cada digito teclado uma acao ocorre, e o metodo chama o metodo 
	 * <code>pesquisaPorNome</code>.
	 * 
	 * @see TratadorEventosConsultaPartido#pesquisaPorNome()
	 */
	public void keyReleased(KeyEvent evento) {
		pesquisaPorNome();
	}

	/** Este metodo e implementado por que a classe <code>TratadorEventosConsultaPartido</code> implementa um MouseListener.
	 *  E por isso ela deve obrigatoriamente implementar os metodos abstratados dessa classe. Quando o usuario da um clique
	 *  na tabela de partidos, um evento e disparado, o metodo pega a posicao em que tabela foi clicada, obtem os dados dessa 
	 *  linha e verifica qual a opcao que o usuario passou para o <code>DialogoConsultarPartido</code>.
	 *  
	 *  @see DialogoConsultarPartido
	 */
	public void mouseClicked(MouseEvent e) {
		
		if ( e.getClickCount() == 2) {
		
			// Obtem a posicao da linha onde a tabela foi clicada.
			int posicao = gui.getTabelaPartidos().getSelectedRow();
			
			// Obtem os dados dessa linha.
			String id = modeloTabela.getValueAt(posicao, 0).toString();
			String nome = modeloTabela.getValueAt(posicao, 1).toString();
			String sigla = modeloTabela.getValueAt(posicao, 2).toString();
	
			// Verifica qual a opcao que usuario passou.
			new DialogoCadastrarPartido(Integer.parseInt(id), nome, sigla);
			pesquisaPorNome();
		}
	}

	/**
	 * Este metodo busca no banco de dados os dados de um partido. A pesquisa e feita a partir de um nome especificado.
	 * Apos a busca a tabela e preenchida com os dados do resultado da pesquisa.
	 */
	public void pesquisaPorNome() {

		// Obtem o nome a ser pesquisado.
		String nome = gui.getFieldNome().getText();

		// Obtem a referencia da tabela e seta a quantidade de linhas como 0.
		modeloTabela = ((DefaultTableModel)(gui.getTabelaPartidos().getModel()));
		modeloTabela.setNumRows(0);

		/* Verifica se existe dados no nome. Se sim incrementa na variavel um "%" para pesquisar todos os partidos a partir das
		letras ja inseridas. */
		if (nome.length() != 0) {
			nome += "%";
			try {
				dataBasePartido.iniciaConexao();
				int verifica = dataBasePartido.verificaPartido(nome);

				// Verifica se existe algum partido com este nome, caso sim busca os dados e adiciona-os em uma linha da tabela.
				if (verifica != 0) {
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
				showMessageDialog(gui, "Informe o Seguinte Erro ao Analista: " + e.toString(), "Atencao", INFORMATION_MESSAGE);
			}
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

} // class TratadorEventosConsultaPartido.
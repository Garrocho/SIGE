package com.sige.gui.candidato.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;

import javax.swing.table.DefaultTableModel;

import com.sige.gui.candidato.DialogoCadastrarCandidato;
import com.sige.gui.candidato.DialogoConsultarCandidato;
import com.sige.gui.recursos.DialogoErro;
import com.sige.persistencia.BancoDadosCandidato;

/**
 * Esta classe extende um <code>KeyAdapter</code> e implementa ActionListener e MouseListener. Esta classe trata os eventos da
 * classe <code>DialogoConsultarCandidato</code>.
 *  
 * @author Charles Garrocho
 * 
 * @see DialogoConsultarCandidato
 */
public class TratadorEventosConsultaCandidato extends KeyAdapter implements ActionListener, MouseListener {
	private DialogoConsultarCandidato gui;
	private BancoDadosCandidato dataBaseCandidato;
	private DefaultTableModel modeloTabela;

	/**
	 * Este e o construtor. Ele recebe como argumento o <code>DialogoConsultarCandidato</code> guarda a referencia dela em uma 
	 * variavel para poder tratar os eventos. O construtor instancia um novo <code>BancoDadosCandidato</code> para poder
	 * fazer as consultas no banco de dados.
	 * 
	 * @param dialogoConsultarCandidato um <code>DialogoConsultarCandidato</code>.
	 * @see DialogoConsultarCandidato
	 * @see BancoDadosCandidato
	 */
	public TratadorEventosConsultaCandidato(DialogoConsultarCandidato dialogoConsultarCandidato) {
		this.gui = dialogoConsultarCandidato;
		this.dataBaseCandidato = new BancoDadosCandidato();
	}

	/** Este metodo e implementado por que a classe <code>TratadorEventosConsultaCandidato</code> implementa um ActionListener.
	 *  E por isso ela deve obrigatoriamente implementar os metodos abstratados dessa classe. Este metodo trata os eventos dos
	 *  botoes sair e limpar.
	 */
	public void actionPerformed(ActionEvent event) {

		// Caso o usuario tenha clicado no botao limpar. Seta o numero de linhas da tabela com 0.
		if (event.getSource() == gui.getBotaoLimpar()) {

			modeloTabela = ((DefaultTableModel)(gui.getTabelaCandidatos().getModel()));
			modeloTabela.setNumRows(0);
		}
		
		// Caso o usuario tenha clicado no botao sair. Da um dispose na janela e o dialogo e encerrado.
		else if (event.getSource() == gui.getBotaoSair()) {
			gui.dispose();
		}	

	} // actionPerformed()

	/** A classe <code>TratadorEventosConsultaCandidato</code> extende um KeyAdapter. Por esse motivo ela pode implementar o metodo
	 * keyReleased. Este metodo trata os eventos do teclado. A cada digito teclado uma acao ocorre, e o metodo verifica se o local
	 * onde o evento ocorreu, se foi no FieldNome ele chama o metodo de <code>pesquisaPorNome</code>, caso contrario se o evento ocorreu no
	 * FieldNumero, o metodo <code>pesquisaPorNumero</code> e chamado.
	 * 
	 * @see TratadorEventosConsultaCandidato#pesquisaPorNumero()
	 * @see TratadorEventosConsultaCandidato#pesquisaPorNome()
	 */
	public void keyReleased(KeyEvent event) {

		// Verifica se o evento ocorreu no FieldNome ou no FieldNumero.
		if (event.getSource() == gui.getFieldNome())
			pesquisaPorNome();
		else
			pesquisaPorNumero();
	}

	/**
	 * Este metodo busca no banco de dados os dados de um candidato. A pesquisa e feita a partir de um numero especificado.
	 * Apos a busca a tabela e preenchida com os dados do resultado da pesquisa.
	 */
	private void pesquisaPorNumero() {

		String numero = gui.getFieldNumero().getText();
		ResultSet resultado = null;
		int verifica = 0;

		// Obtem a referencia da tabela de candidatos. Seta a numero de linhas data tabela de candidatos com 0.
		modeloTabela = ((DefaultTableModel)(gui.getTabelaCandidatos().getModel()));
		modeloTabela.setNumRows(0);

		if (numero.length() != 0 && numero.length() < 6) {

			// Faz a busca no banco de dados, e armazena o resultado em uma linha da tabela.
			try {
				dataBaseCandidato.iniciaConexao();
				verifica = dataBaseCandidato.verificaCandidato(Integer.parseInt(numero));
				if ( verifica > 0) {
					resultado = dataBaseCandidato.obterCandidato(Integer.parseInt(numero));
					Object[] linha = new Object[4];
					while(resultado.next())  
					{  
						linha[0] = String.format("%d",resultado.getInt("numero"));
						linha[1]  = resultado.getString("nome");  
						linha[2] = resultado.getString("partido");  
						linha[3] = resultado.getString("cargo");		      
						modeloTabela.addRow(linha);
					}
				}
				dataBaseCandidato.fechaConexao();
			} catch (Exception e) {
				new DialogoErro(gui, "Erro", "Informe o Seguinte Erro ao Analista:\n" + e.toString());
			}
		}
	}

	/**
	 * Este metodo busca no banco de dados os dados de um candidato. A pesquisa e feita a partir de um nome especificado.
	 * Apos a busca a tabela e preenchida com os dados do resultado da pesquisa.
	 */
	private void pesquisaPorNome() {

		String nome = gui.getFieldNome().getText();
		ResultSet resultado = null;
		dataBaseCandidato = new BancoDadosCandidato();
		int verifica = 0;

		// Obtem a referencia da tabela de candidatos. Seta a numero de linhas data tabela de candidatos com 0.
		modeloTabela = ((DefaultTableModel)(gui.getTabelaCandidatos().getModel()));
		modeloTabela.setNumRows(0);

		// Adiciona a variavel "%" para pesquisar todos os nomes a partir das letras ja inseridas.
		if (nome.length() != 0)
			nome += "%";

		// Faz a busca no banco de dados, e armazena o resultado em uma linha da tabela.
		try {
			dataBaseCandidato.iniciaConexao();
			verifica = dataBaseCandidato.verificaCandidato(nome);
			if ( verifica > 0) {
				resultado = dataBaseCandidato.obterCandidato(nome);
				Object[] linha = new Object[4];
				while(resultado.next())  
				{  
					linha[0] = String.format("%d",resultado.getInt("numero"));
					linha[1]  = resultado.getString("nome");  
					linha[2] = resultado.getString("partido");  
					linha[3] = resultado.getString("cargo");		      
					modeloTabela.addRow(linha);
				}
			}
			dataBaseCandidato.fechaConexao();
		} catch (Exception e) {
			new DialogoErro(gui, "Erro", "Informe o Seguinte Erro ao Analista:\n" + e.toString());
		}
	}

	/** Este metodo e implementado por que a classe <code>TratadorEventosConsultaCandidato</code> implementa um MouseListener.
	 *  E por isso ela deve obrigatoriamente implementar os metodos abstratados dessa classe. Quando o usuario da um clique
	 *  na tabela de candidatos, um evento e disparado, o metodo pega a posicao em que tabela foi clicada, obtem os dados dessa 
	 *  linha e verifica qual a opcao que o usuario passou para o <code>DialogoConsultarCandidato</code>.
	 *  
	 *  @see DialogoConsultarCandidato
	 */
	public void mouseClicked(MouseEvent e) {
		
		if (e.getClickCount() == 2) {

			// Obtem a posicao da linha onde a tabela foi clicada.
			int posicao = gui.getTabelaCandidatos().getSelectedRow();
	
			// Obtem os dados dessa linha.
			String numero = gui.getTabelaCandidatos().getValueAt(posicao, 0).toString();
			String nome = gui.getTabelaCandidatos().getValueAt(posicao, 1).toString();
			String partido = gui.getTabelaCandidatos().getValueAt(posicao, 2).toString();
			String cargo = gui.getTabelaCandidatos().getValueAt(posicao, 3).toString();
	
			String caminhoFoto = "";
			try {
				dataBaseCandidato.iniciaConexao();
				caminhoFoto = dataBaseCandidato.obterFoto( Integer.parseInt(numero), cargo);
				dataBaseCandidato.fechaConexao();
				new DialogoCadastrarCandidato(numero, nome, partido, cargo, caminhoFoto);
				pesquisaPorNome();
			} catch (Exception e1) {
				new DialogoErro(gui, "Erro", "Informe o Seguinte Erro ao Analista:\n" + e.toString());
			}
		}
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

} // class TratadorEventosConsultaCandidato.
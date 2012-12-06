package com.sige.gui.votacao.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;

import javax.swing.table.DefaultTableModel;

import com.sige.graficos.GraficoBarra;
import com.sige.gui.recursos.DialogoErro;
import com.sige.gui.votacao.DialogoConsultarVotacao;
import com.sige.persistencia.BancoDadosVotacaoCargos;

/**
 * Esta classe extende um <code>KeyAdapter</code> e implementa ActionListener e MouseListener. Esta classe trata os eventos da
 * classe <code>DialogoConsultarVotacao</code>.
 *  
 * @author Charles Garrocho
 * @author Barbara Silveiro
 * 
 * @see DialogoConsultarVotacao
 */
public class TratadorEventosConsultaVotacao extends KeyAdapter implements ActionListener, MouseListener {
	private DialogoConsultarVotacao gui;
	private BancoDadosVotacaoCargos dataBaseVotacaoCargos;
	private DefaultTableModel modeloTabela;

	/**
	 * Este e o construtor. Ele recebe como argumento o <code>DialogoConsultarVotacao</code> guarda a referencia dela em uma 
	 * variavel para poder tratar os eventos. O construtor instancia um novo <code>BancoDadosVotacaoCargos</code> para poder
	 * fazer as consultas no banco de dados.
	 * 
	 * @param dialogoConsultarVotacao um <code>DialogoConsultarVotacao</code>.
	 * @see DialogoConsultarVotacao
	 * @see BancoDadosVotacaoCargos
	 */
	public TratadorEventosConsultaVotacao(DialogoConsultarVotacao dialogoConsultarVotacao) {
		this.gui = dialogoConsultarVotacao;
		this.dataBaseVotacaoCargos = new BancoDadosVotacaoCargos();
	}

	/** Este metodo e implementado por que a classe <code>TratadorEventosConsultaVotacao</code> implementa um ActionListener.
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
			modeloTabela = ((DefaultTableModel)(gui.getTabelaVotacao().getModel()));
			modeloTabela.setNumRows(0);
		}

	} // actionPerformed()

	/** A classe <code>TratadorEventosConsultaVotacao</code> extende um KeyAdapter. Por esse motivo ela pode implementar o metodo
	 * keyReleased. Este metodo trata os eventos do teclado. A cada digito teclado uma acao ocorre, e o metodo faz uma busca no
	 * banco de dados pesquisando um nome de um cargo, a partir das letras tecladas. Apos a busca ela joga em uma tabela os resultados.
	 */
	public void keyReleased(KeyEvent evento) {
		String cargo = gui.getFieldCargo().getText();
		modeloTabela = ((DefaultTableModel)(gui.getTabelaVotacao().getModel()));
		modeloTabela.setNumRows(0);
		if (cargo.length() != 0) {
			cargo += "%";
			try {
				dataBaseVotacaoCargos.iniciaConexao();
				int verifica = dataBaseVotacaoCargos.verificaVotacaoCargos(cargo);
				if (verifica != 0) {
					Object[] linha = new Object[2];
					ResultSet resultado = dataBaseVotacaoCargos.obterVotacaoPorCargo(cargo);
					while(resultado.next())  
					{  
						linha[0] = resultado.getString("data"); 
						linha[1] = resultado.getString("cargo"); 
						modeloTabela.addRow(linha);
					}
				}
				dataBaseVotacaoCargos.fechaConexao();
			} catch (Exception e) {
				new DialogoErro(gui, "Erro", "Informe o Seguinte Erro ao Analista: " + e.toString());
			}
		}
	}

	/** Este metodo e implementado por que a classe <code>TratadorEventosConsultaVotacao</code> implementa um MouseListener.
	 *  E por isso ela deve obrigatoriamente implementar os metodos abstratados dessa classe. Quando o usuario da um clique
	 *  na tabela de votacao, um evento e disparado, o metodo pega a posicao em que tabela foi clicada, obtem os dados dessa 
	 *  linha e cria um grafico de barra passando a data, cargo e a quantidade de eleitores dessa votacao.
	 */
	public void mouseClicked(MouseEvent e) {

		int posicao = gui.getTabelaVotacao().getSelectedRow();
		String data = modeloTabela.getValueAt(posicao, 0).toString();
		String cargo = modeloTabela.getValueAt(posicao, 1).toString();
		try {
			dataBaseVotacaoCargos.iniciaConexao();
			new GraficoBarra(data, cargo, dataBaseVotacaoCargos.obterQuantidadeEleitores(cargo, data));
			dataBaseVotacaoCargos.fechaConexao();
		} catch (Exception e1) {
			new DialogoErro(gui, "Erro", "Informe o Seguinte Erro ao Analista: " + e1.toString());
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
} // class TratadorEventosConsultaVotacao.
package com.sige.gui.pesquisa.eventos;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.sige.gui.ShadowBorder;
import com.sige.gui.pesquisa.DialogoConsultarPesquisa;
import com.sige.gui.recursos.DialogoErro;
import com.sige.persistencia.BancoDadosPesquisaCandidatos;

/**
 * Esta classe extende um <code>MouseAdapter</code> e trata os eventos da classe <code>DialogoConsultarPesquisa</code>.
 *  
 * @author Charles Garrocho
 * 
 * @see DialogoConsultarPesquisa
 */
public class TratadorEventosConsultaPesquisaCandidatos extends MouseAdapter {
	private BancoDadosPesquisaCandidatos dataBasePesquisaCandidato;
	private DefaultTableModel modeloTabela;
	private DialogoConsultarPesquisa gui;
	
	/**
	 * Este e o construtor. Ele recebe como argumento o <code>DialogoConsultarPesquisa</code> guarda a referencia dela em uma 
	 * variavel para poder tratar os eventos. O construtor instancia um novo <code>BancoDadosPesquisaCandidatos</code> para poder
	 * fazer as consultas no banco de dados.
	 * 
	 * @param dialogoConsultarPesquisa um <code>DialogoConsultarPesquisa</code>.
	 * @see DialogoConsultarPesquisa
	 * @see BancoDadosPesquisaCandidatos
	 */
	public TratadorEventosConsultaPesquisaCandidatos(DialogoConsultarPesquisa dialogoConsultarPesquisa) {
		super();
		this.gui = dialogoConsultarPesquisa;
		this.dataBasePesquisaCandidato = new BancoDadosPesquisaCandidatos();
	}
	
	/** Este metodo e implementado por que a classe <code>TratadorEventosConsultaPesquisaCandidatos</code> extende um 
	 *  um <code>MouseAdapter</code>. Quando o usuario da um clique na tabela de partidos, um evento e disparado, o metodo 
	 *  pega a posicao em que tabela foi clicada, obtem os dados dessa linha e consulta os candidatos dessa pesquisa que foi
	 *  selecionada.
	 */
	public void mouseClicked(MouseEvent evento) {
		JDialog popMenu = new JDialog(gui,"Candidatos Relacionados a Pesquisa");
		popMenu.getRootPane().setBorder(new ShadowBorder());
		
		JPanel painelCentro = new JPanel();
		String colunasTabelaCandidato[] = {"Nome","Votos"},
	           dadosTabelaCandidato[][] = new String[0][0];
		
		int posicao = evento.getY() / gui.getTabelaPesquisa().getRowHeight();
		int numero_pesquisa = Integer.parseInt(gui.getTabelaPesquisa().getValueAt(posicao, 0).toString());
		
		JTable tabelaCandidatos = new JTable(new DefaultTableModel(dadosTabelaCandidato, colunasTabelaCandidato));
		tabelaCandidatos.setPreferredScrollableViewportSize(new Dimension(400, 150));
		tabelaCandidatos.getColumn("Nome").setPreferredWidth(120);
		tabelaCandidatos.getColumn("Votos").setPreferredWidth(30);
		tabelaCandidatos.setEnabled(false);
		
		modeloTabela = ((DefaultTableModel)(tabelaCandidatos.getModel()));
		modeloTabela.setNumRows(0);
		try {
			dataBasePesquisaCandidato.iniciaConexao();
			ResultSet resultado = dataBasePesquisaCandidato.obterPesquisaCandidatos(numero_pesquisa);	
			Object[] linha2 = new Object[2];
			while(resultado.next())  
			{  
				linha2[0] = resultado.getString("nome_candidato");
				linha2[1] = resultado.getInt("numero_votos");
				modeloTabela.addRow(linha2);
			}
			dataBasePesquisaCandidato.fechaConexao();
		} catch (Exception e) {
			new DialogoErro(gui, "Erro", "Informe o Seguinte Erro ao Analista: " + e.toString());
		}
		
		JScrollPane barraRolagem2 = new JScrollPane(tabelaCandidatos);
		painelCentro.add(barraRolagem2);
		popMenu.add(painelCentro);
		popMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		popMenu.setModal(true);
		popMenu.pack();
		popMenu.setLocationRelativeTo(null);
		popMenu.setVisible(true);
	}
}

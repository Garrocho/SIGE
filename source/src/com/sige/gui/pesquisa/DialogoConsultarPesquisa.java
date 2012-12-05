package com.sige.gui.pesquisa;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import com.sige.gui.ShadowBorder;
import com.sige.gui.pesquisa.eventos.TratadorEventosConsultaPesquisaCandidatos;
import com.sige.persistencia.BancoDadosPesquisa;

/**
 * Esta classe extende um <code>JDialog</code> e cria uma a interface grafica que permite o usuario consultar todas as
 * pesquisas cadastradas.
 * 
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class DialogoConsultarPesquisa extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTable tabelaPesquisa;

	/**
	 * Este e o construtor. Ele constroi a interface grafica do dialogo Consultar Pesquisa.
	 */
	public DialogoConsultarPesquisa() {

		super();
		getRootPane().setBorder(new ShadowBorder());
		setTitle("Pesquisas Eleitorais Cadastradas");

		JPanel painelCentro = new JPanel();

		BancoDadosPesquisa dataBasePesquisa = new BancoDadosPesquisa();
		TratadorEventosConsultaPesquisaCandidatos tratadorEventos = new TratadorEventosConsultaPesquisaCandidatos(this);

		String colunasTabela[] = {"ID","Data Inicio","Data Fim","Cargo", "Branco/Nulo","Indecisos","Entrevistados","Municipios"},
			   dadosTabela[][] = new String[0][0];

		tabelaPesquisa = new JTable(new DefaultTableModel(dadosTabela, colunasTabela));
		
		// Define as preferencias da Tabela de Pesquisas.
		tabelaPesquisa.setPreferredScrollableViewportSize(new Dimension(680, 250));
		tabelaPesquisa.getColumn("ID").setPreferredWidth(3);
		tabelaPesquisa.getColumn("Data Inicio").setPreferredWidth(50);
		tabelaPesquisa.getColumn("Data Fim").setPreferredWidth(50);
		tabelaPesquisa.getColumn("Cargo").setPreferredWidth(130);
		tabelaPesquisa.getColumn("Branco/Nulo").setPreferredWidth(50);
		tabelaPesquisa.getColumn("Indecisos").setPreferredWidth(35);
		tabelaPesquisa.getColumn("Entrevistados").setPreferredWidth(60);
		tabelaPesquisa.getColumn("Municipios").setPreferredWidth(45);
		tabelaPesquisa.addMouseListener(tratadorEventos);
		tabelaPesquisa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		
		// Adiciona a barraRolagem a tabelaPesquisa
		JScrollPane barraRolagem = new JScrollPane(tabelaPesquisa);
		
		// Adiciona a Barra de Rolagem com a tabela no painelCentro.
		painelCentro.add(barraRolagem);
		
		// Adiciona o painelCentro na regiao central do Dialogo Consultar Pesquisa.
		add(painelCentro, BorderLayout.CENTER);

		// Obtem a referencia o modelo da tabela de pesquisa para adicionar as linhas.
		DefaultTableModel modeloTabela = ((DefaultTableModel)(tabelaPesquisa.getModel()));
		
		// Busca todas as pesquisas cadastradas no banco de dados e joga seu conteudo na tabela de pesquisas.
		try {

			// Inicia a conexao com o banco de dados.
			dataBasePesquisa.iniciaConexao();
			ResultSet resultado = dataBasePesquisa.obterTodasPesquisas();
			Object[] linha = new Object[8];
			while(resultado.next())  
			{  
				linha[0] = resultado.getInt("numero_pesquisa");
				linha[1] = resultado.getString("data_inicio");
				linha[2] = resultado.getString("data_fim");
				linha[3] = resultado.getString("cargo");
				linha[4] = String.format("%d",resultado.getInt("numero_branco")); 
				linha[5] = String.format("%d",resultado.getInt("numero_indeciso"));
				linha[6] = String.format("%d",resultado.getInt("numero_entrevistado"));
				linha[7] = String.format("%d",resultado.getInt("numero_municipios"));
				modeloTabela.addRow(linha);
			}

			// Fecha a conexao o banco de dados.
			dataBasePesquisa.fechaConexao();
		} catch (SQLException e) {
			showMessageDialog(null, "Informe o Seguinte Erro ao Analista: " + e.toString(), "Atencao", INFORMATION_MESSAGE);
		} catch (ClassNotFoundException e) {
			showMessageDialog(null, "Informe o Seguinte Erro ao Analista: " + e.toString(), "Atencao", INFORMATION_MESSAGE);
		}

		// Define as propriedades do Dialogo.
		pack();
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icones/icone.png")));
		setLocationRelativeTo(null);
		setModal(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	/**
	 * Este metodo retorna uma referencia da tabela de pesquisas do Dialogo.
	 * 
	 * @return <code>JTable</code> com o conteudo da tabela.
	 */
	public JTable getTabelaPesquisa() {
		return tabelaPesquisa;
	}
}
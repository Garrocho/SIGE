package com.sige.gui.pesquisa.eventos;

import static com.sige.recursos.Recurso.formataData;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import com.sige.graficos.GraficoLinha;
import com.sige.gui.pesquisa.DialogoRelatorioPesquisa;
import com.sige.persistencia.BancoDadosPesquisa;

/**
 * Esta classe implementa um <code>ActionListener</code> e trata os eventos da classe <code>DialogoRelatorioPesquisa</code>.
 *  
 * @author Charles Garrocho
 * @author Barbara Silveiro
 * 
 * @see DialogoRelatorioPesquisa
 */
public class TratadorEventosRelatorioPesquisa implements ActionListener {

	private DialogoRelatorioPesquisa gui;
	private BancoDadosPesquisa dataBasePesquisa;

	/**
	 * Este e o construtor. Ele recebe como argumento o <code>DialogoRelatorioPesquisa</code> guarda a referencia dela em 
	 * uma variavel para poder tratar os eventos. O construtor instancia um novo <code>BancoDadosPesquisa</code> para poder
	 * fazer as consultas no banco de dados.
	 * 
	 * @param dialogoRelatorioPesquisa um <code>DialogoRelatorioPesquisa</code>.
	 * @see DialogoRelatorioPesquisa
	 * @see BancoDadosPesquisa
	 */
	public TratadorEventosRelatorioPesquisa(DialogoRelatorioPesquisa dialogoRelatorioPesquisa) {
		super();
		this.gui = dialogoRelatorioPesquisa;
		this.dataBasePesquisa = new BancoDadosPesquisa();
	}

	/** 
	 *  Este metodo e implementado por que a classe <code>TratadorEventosRelatorioPesquisa</code> implementa um ActionListener.
	 *  E por isso ela deve obrigatoriamente implementar os metodos abstratados dessa classe. Este metodo trata os eventos dos
	 *  botoao exibir.
	 */
	public void actionPerformed(ActionEvent evento) {

		if (evento.getSource() == gui.getBotaoSair())
			gui.dispose();

		// Caso o usuario tenha clicado no botao exibir.
		else if (evento.getSource() == gui.getBotaoExibir()) {

			// Obtem os dados fornecidos pelo usuario.
			String cargo = gui.getCargoField().getText();
			String dataInicio = ((JTextField)gui.getDataInicioCalendario().getComponent(0)).getText();
			dataInicio = dataInicio.replace(".", "/");
			String dataFim = ((JTextField)gui.getDataFimCalendario().getComponent(0)).getText();
			dataFim = dataFim.replace(".", "/");

			// Verifica se a data e hora da pesquisa sao validas.
			if (dataInicio.length() > 14 || dataInicio.length() < 10) {
				showMessageDialog(gui, "Forneca uma Data Inicio Valida para a Pesquisa.", "Atencao", INFORMATION_MESSAGE);
			}
			else if (dataFim.length() > 14 || dataFim.length() < 10) {
				showMessageDialog(gui, "Forneca a Data Fim da Pesquisa.", "Atencao", INFORMATION_MESSAGE);
			}
			else {

				int dataInicioAux = Integer.parseInt(dataInicio.substring(6,10) + dataInicio.substring(3,5) + dataInicio.substring(0,2));
				int dataFimAux = Integer.parseInt(dataFim.substring(6,10) + dataFim.substring(3,5) + dataFim.substring(0,2));

				// Verifica e a data inicio e menor que a data fim.
				if (dataInicioAux > dataFimAux) {
					showMessageDialog(gui, "A Data Fim Nao Pode Ser Anterior Que a Data Inicio.", "Atencao", INFORMATION_MESSAGE);
				}

				// Verifica se o cargo foi informado.
				else if (cargo.length() == 0) {
					showMessageDialog(gui, "Forneca o Cargo da Pesquisa.", "Atencao", INFORMATION_MESSAGE);
				}
				else {

					// Formada a DataInicio e DataFim em um formato correto para poder fazer uma pesquisa no banco de dados.
					dataInicio = formataData(dataInicio);
					dataFim = formataData(dataFim);

					try {
						dataBasePesquisa.iniciaConexao();
						int numero_pesquisa = dataBasePesquisa.obterQuantidadePesquisas(cargo, dataInicio, dataFim);
						if (numero_pesquisa == 0) {
							showMessageDialog(gui, "Nao Existe Pesquisas Cadastradas Nesse Periodo.", "Atencao", INFORMATION_MESSAGE);
							dataBasePesquisa.fechaConexao();
						}
						else {
							new GraficoLinha(cargo, dataInicio, dataFim);
							dataBasePesquisa.fechaConexao();
						}
					} catch (Exception e) {
						showMessageDialog(gui, "Informe o Seguinte Erro ao Analista: " + e.toString(), "Atencao", INFORMATION_MESSAGE);
					}
				}
			}
		}
	}
}

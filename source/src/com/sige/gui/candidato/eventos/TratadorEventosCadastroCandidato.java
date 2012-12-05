package com.sige.gui.candidato.eventos;

import static com.sige.recursos.Recurso.copy;
import static com.sige.recursos.Recurso.extensao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.sige.gui.candidato.DialogoCadastrarCandidato;
import com.sige.gui.recursos.DialogoErro;
import com.sige.gui.recursos.DialogoSucesso;
import com.sige.persistencia.BancoDadosCandidato;
import com.sige.persistencia.BancoDadosCargo;
import com.sige.persistencia.BancoDadosPartido;

/**
 * Esta classe implementa um <code>ActionListener</code>. Esta classe trata os eventos da classe 
 * <code>DialogoCadastrarCandidato</code>.
 *  
 * @author Charles Garrocho
 * @author Barbara Silveiro
 * 
 * @see DialogoCadastrarCandidato
 */
public class TratadorEventosCadastroCandidato implements ActionListener {
	private DialogoCadastrarCandidato gui;
	private BancoDadosCandidato dataBaseCandidato;
	private BancoDadosPartido dataBasePartido;
	private BancoDadosCargo dataBaseCargo;

	/**
	 * Este e o construtor. Ele recebe como argumento o <code>DialogoCadastrarCandidato</code> guarda a referencia dela em uma 
	 * variavel para poder tratar os eventos. O construtor instancia as classes de banco de dados necessarias para poder fazer
	 * as consultas e insercoes no banco de dados.
	 * 
	 * @param dialogoCadastrarCandidato um <code>DialogoCadastrarCandidato</code>.
	 * @see DialogoCadastrarCandidato
	 * @see BancoDadosCandidato
	 * @see BancoDadosPartido
	 * @see BancoDadosCargo
	 */
	public TratadorEventosCadastroCandidato(DialogoCadastrarCandidato dialogoCadastrarCandidato) {
		this.gui = dialogoCadastrarCandidato;
		this.dataBaseCandidato = new BancoDadosCandidato();
		this.dataBasePartido = new BancoDadosPartido();
		this.dataBaseCargo = new BancoDadosCargo();
	}

	/** Este metodo e implementado por que a classe <code>TratadorEventosCadastroCandidato</code> implementa um ActionListener.
	 *  E por isso ela deve obrigatoriamente implementar os metodos abstratados dessa classe. Este metodo trata os eventos dos
	 *  botoes cancelar e gravar.
	 */
	public void actionPerformed(ActionEvent event) {

		// Caso o usuario tenha clicado no botao sair. Da um dispose na janela e o dialogo e encerrado.
		if (event.getSource() == gui.getBotaoCancelar()) {
			gui.dispose();
		}
		
		else {
			String numero = gui.getFieldNumero().getText();
			String nome = gui.getFieldNome().getText();
			String partido = gui.getPartidoField().getText();
			String cargo = gui.getCargoField().getText();
			String caminhoImagem = gui.getCaminhoImagem();
			String numeroPartido = numeroPartido(partido);
			String numeroDigitos = numeroDigitos(cargo);
			
			if (event.getSource() == gui.getBotaoExcluir()) {
				excluiDados(numeroDigitos, nome, cargo);
			}
			
			// Caso o usuario tenha clicado no botao gravar. Verifica se todos os dados estao preenchidos e se estao todos corretos.
			else if (event.getSource() == gui.getBotaoGravar()) {
	
				// Verifica se os campos estao preenchidos corretamente.
				if (nome.equals(""))
					new DialogoErro(gui, "Erro", "Forneca o nome do candidato");
				else if (nome.length() < 10)
					new DialogoErro(gui, "Erro", "Especifique melhor o nome do candidato");
				else if (numero.length() == 0)
					new DialogoErro(gui, "Erro", "O Numero do Partido Nao Pode Ficar Vazio");
				else if (!numeroPartido.equalsIgnoreCase(numero.substring(0, 2))) {
					new DialogoErro(gui, "Erro", "O Numero Do Partido Deve Comecar Com " + numeroPartido);
					gui.getFieldNumero().setText(numeroPartido);
				}
				else if (!numeroDigitos.equalsIgnoreCase(String.format("%d",numero.length()))) {
					new DialogoErro(gui, "Erro", "O Numero do " + cargo + " Deve Ter " + numeroDigitos + " Digitos.");
				}
				else if (caminhoImagem.equalsIgnoreCase("Recursos/Imagens/Default/cadastro.png"))
					new DialogoErro(gui, "Erro", "Selecione uma Imagem Para o Candidato");
				else {
	
					// Verifica se o caminho da imagem e diferente do cadastrado anteriormente.
					if (!caminhoImagem.equalsIgnoreCase("Recursos/Imagens/" + numero + nome + extensao(caminhoImagem))) {
						File file = new File(caminhoImagem);  
						caminhoImagem = "Recursos/Imagens/" + numero + nome + extensao(caminhoImagem);
						File dest = new File(caminhoImagem);
						try {
							copy(file, dest);
						} catch (IOException e) {
							new DialogoErro(gui, "Erro", "Selecione uma Imagem Para o Candidato");
						}
					}
	
					if (gui.getNumeroAlterar() == -1) {
						try {
							dataBaseCandidato.iniciaConexao();
							int verifica = dataBaseCandidato.verificaCandidato(Integer.parseInt(numero));
							if (verifica == 0) {
								dataBaseCandidato.adicionarCandidato(Integer.parseInt(numero), nome, partido, cargo, caminhoImagem);
								new DialogoSucesso(gui, "Sucesso", "Candidato Cadastrado.");
								gui.dispose();
							}
							else
								new DialogoErro(gui, "Erro", "Ja Existe Um Candidato Com Esse Numero.");
							dataBaseCandidato.fechaConexao();
							
						} catch (Exception e) {
							new DialogoErro(gui, "Erro", "Informe o Seguinte Erro ao Analista: " + e.toString());
						}
					}
					else {
						int verifica;
						try {
	
							dataBaseCandidato.iniciaConexao();
	
							// Caso o cargo nao seja alterado
							if (cargo.equalsIgnoreCase(gui.getCargoAlterar())) {
	
								if (gui.getNumeroAlterar() == Integer.parseInt(numero)) {
									dataBaseCandidato.alterarCandidato(Integer.parseInt(numero), nome, partido, cargo, caminhoImagem);
									new DialogoSucesso(gui, "Sucesso", "Candidato Alterado.");
									gui.dispose();
								}
								else {
									verifica = dataBaseCandidato.verificaCandidatoPorCargo(Integer.parseInt(numero), cargo);
									if (verifica == 0) {
										dataBaseCandidato.alterarCandidatoNovoNumero(gui.getNumeroAlterar(), Integer.parseInt(numero), nome, partido, cargo, caminhoImagem);
										new DialogoSucesso(gui, "Sucesso", "Candidato Alterado.");
										gui.dispose();
									}
									else
										new DialogoErro(gui, "Erro",  "Ja Existe Um Candidato Com Esses Dados Cadastrados.");
									dataBaseCandidato.fechaConexao();
								}
							}
	
							// caso o cargo seja alterado
							else {
	
								try {
	
									verifica = dataBaseCandidato.verificaCandidatoPorCargo(Integer.parseInt(numero), cargo);
									if (verifica == 0) {
	
										if (gui.getNumeroAlterar() == Integer.parseInt(numero)) {
											dataBaseCandidato.alterarCandidatoCargo(gui.getCargoAlterar(), Integer.parseInt(numero), nome, partido, cargo, caminhoImagem);
											new DialogoSucesso(gui, "Sucesso", "Candidato Alterado.");
											gui.dispose();
										}
										else {
											dataBaseCandidato.alterarCandidatoNovoNumeroCargo(gui.getCargoAlterar(), gui.getNumeroAlterar(), Integer.parseInt(numero), nome, partido, cargo, caminhoImagem);
											new DialogoSucesso(gui, "Sucesso", "Candidato Alterado.");
											gui.dispose();
										}
									}
									else
										new DialogoErro(gui, "Erro",  "Ja Existe Um Candidato Com Esses Dados Cadastrados.");
									dataBaseCandidato.fechaConexao();
								} catch (Exception e) {
									new DialogoErro(gui, "Erro", "Informe o Seguinte Erro ao Analista:\n" + e.toString());
								}
							}
						} catch (Exception e) {
							new DialogoErro(gui, "Erro", "Informe o Seguinte Erro ao Analista:\n" + e.toString());
						}
					}
				}
			}
		}
	}

	/**
	 * Este metodo busca no banco de dados o numero de um partido a partir de uma sigla.
	 * 
	 * @param sigla <code>String</code> sigla do partido.
	 * @return um <code>String</code> com o numero do partido.
	 */
	private String numeroPartido(String sigla) {
		String numeroPartido = "";
		try {
			dataBasePartido.iniciaConexao();
			numeroPartido = String.format("%d",dataBasePartido.verificaPartidoSigla(sigla));
			dataBasePartido.fechaConexao();
		} catch (Exception e) {
			new DialogoErro(gui, "Erro", "Informe o Seguinte Erro ao Analista:\n" + e.toString());
		}
		return numeroPartido;
	}
	
	/**
	 * Este metodo exibe uma mensagem na tela perguntando o usuario se ele gostaria de excluir o candidato que foi selecionado
	 * na tabela de candidatos.
	 * 
	 * @param numero um <code>String</code> com o numero do candidato.
	 * @param nome um <code>String</code> com o nome do candidato.
	 */
	private void excluiDados(String numero, String nome, String cargo) {

		// Exibe uma mensagem ao usuario perguntando se ele gostaria de excluir o candidato.
		int op = JOptionPane.showConfirmDialog(gui, "Gostaria de Excluir " + nome + "?");
		if (op == 0) {
			try {
				System.out.println("teste");
				dataBaseCandidato.iniciaConexao();
				String caminhoFoto = dataBaseCandidato.obterFoto( Integer.parseInt(numero), cargo);
				File file = new File(caminhoFoto);  
				file.delete();
				dataBaseCandidato.excluirCandidato(Integer.parseInt(numero),nome);
				dataBaseCandidato.fechaConexao();
			} catch (Exception e) {
				new DialogoErro(gui, "Erro", "Informe o Seguinte Erro ao Analista:\n" + e.toString());
			}
		}
	}

	/**
	 * Este metodo busca no banco de dados o numero de digitos de um cargo a partir de um cargo.
	 * 
	 * @param cargo <code>String</code> cargo do candidato.
	 * @return um <code>String</code> com o numero de digitos do cargo.
	 */
	private String numeroDigitos(String cargo) {
		String numeroDigitos = "";
		try {
			dataBaseCargo.iniciaConexao();
			numeroDigitos = String.format("%d",dataBaseCargo.obterDigitosCargo(cargo));
			dataBaseCargo.fechaConexao();
		} catch (Exception e) {
			new DialogoErro(gui, "Erro", "Informe o Seguinte Erro ao Analista:\n" + e.toString());
		}
		return numeroDigitos;
	}

} // classe TratadorEventosCadastroCandidato.
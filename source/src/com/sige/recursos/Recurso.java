package com.sige.recursos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Esta classe disponibiliza varios metodos estaticos.
 *  
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class Recurso {

	private static String[] MESES = {"jan","fev","mar","abr","mai","jun","jul","ago","set","out","nov","dez"};

	/**
	 * Formata a data no formato de insercao no banco de dados.
	 * 
	 * @param data um <code>String</code> com a data.
	 * @return um <code>String</code> com a data formatada.
	 */
	public static String formataData(String data) {
		return data.substring(6,10) + "-" + data.substring(3,5) + "-" + data.substring(0,2);
	}

	/**
	 * Retorna o nome do mes e o ano de uma data.
	 * 
	 * @param data um <code>String</code> com a data.
	 * @return um <code>String</code> com o nome do mes e o ano.
	 */
	public static String descobreMes(String data) {

		int mes = Integer.parseInt(data.substring(data.indexOf("-") +1, data.indexOf("-") + 3));
		return MESES[mes-1] + "/" + data.substring(0, data.indexOf("-"));
	}

	/**
	 * Copia arquivos de um local para o outro.
	 * 
	 * @param origem <code>File</code> com o arquivo de origem.
	 * @param destino <code>File</code> com o arquivo de Destino.
	 * @throws IOException Excecao de entrada e saida de dados
	 */
	public static void copy(File origem,File destino) throws IOException{  
		FileInputStream fisOrigem = new FileInputStream(origem);  
		FileOutputStream fisDestino = new FileOutputStream(destino);  
		FileChannel fcOrigem = fisOrigem.getChannel();    
		FileChannel fcDestino = fisDestino.getChannel();    
		fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);    
		fisOrigem.close();    
		fisDestino.close();  
	} 

	/**
	 * Retira do endereco a extensao do arquivo.
	 * 
	 * @param caminhoArquivo um <code></code> com o endereco da imagem.
	 * @return um <code>String</code> com a extensao da imagem.
	 */
	public static String extensao(String caminhoArquivo) {
		return caminhoArquivo.substring(caminhoArquivo.indexOf("."));
	}
}
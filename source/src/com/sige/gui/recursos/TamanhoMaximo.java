package com.sige.gui.recursos;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Esta classe extende um <code>PlainDocument</code>. Ela aplica a um componente a insercao com um numero maximo de caracteres.
 * 
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class TamanhoMaximo extends PlainDocument {  
	private static final long serialVersionUID = 1L;
	private int iMaxLength;  

	/**
	 * Este e o construtor. Ele define o numero maximo de caracteres que aceitara na insercao.
	 * @param maximo um <code>int</code> com a quantidade maxima de caracteres.
	 */
	public TamanhoMaximo(int maximo) {  
		super();  
		iMaxLength = maximo;  
	}  

	public void insertString(int offset, String str, AttributeSet attr)  throws BadLocationException {  

		if (iMaxLength <= 0) // aceitara qualquer no. de caracteres  
		{  
			super.insertString(offset, str, attr);  
			return;  
		}  

		int ilen = (getLength() + str.length());  
		if (ilen <= iMaxLength) // se o comprimento final for menor...  
			super.insertString(offset, str, attr); // ...aceita str  
		else  
		{  
			if (getLength() == iMaxLength) return; // nada a fazer  
			String newStr = str.substring(0, (iMaxLength - getLength()));  

			super.insertString(offset, newStr, attr);  
		}  
	}
}

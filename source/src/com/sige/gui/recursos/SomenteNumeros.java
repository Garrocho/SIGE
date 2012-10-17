package com.sige.gui.recursos;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Esta classe extende um <code>PlainDocument</code>. Ela aplica a um componente a insercao de apenas numeros.
 * 
 * @author Charles Garrocho
 * @author Barbara Silveiro
 */
public class SomenteNumeros extends PlainDocument {  

	private static final long serialVersionUID = 1L;

	public void insertString(int offs, String str, AttributeSet a) {  
	        
	      try{  
	         Integer.parseInt(str);  
	      }catch(NumberFormatException ex){  
	         str = "";  
	      }  
	        
	      try {  
	         super.insertString(offs, str, a);  
	      } catch (BadLocationException e) {  
	         e.printStackTrace();  
	      }  
	   }
}
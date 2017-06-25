/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

/**
 *
 * @author matheus
 */
public class Token {
    
    private String lexema;
    private Tag classe;
	
    public Token(Tag classe, String lexema) {
	
    	this.classe = classe;
	this.lexema = lexema;
    }
	
    public Tag getClasse() {
		
	return classe;
}
	
    public void setClasse(Tag classe) {
		
	this.classe = classe;
    }
	
    public String getLexema() {
	
	return lexema;
    }
	
    public void setLexema(String lexema) {
		
	this.lexema = lexema;
    }
    
    @Override
    public String toString() {
        return "<" + classe + ", \"" + lexema + "\">";
    }
}
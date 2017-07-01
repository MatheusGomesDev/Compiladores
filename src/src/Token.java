package src;

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
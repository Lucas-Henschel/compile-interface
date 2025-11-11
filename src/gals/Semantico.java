package gals;

import gals.exceptions.SemanticError;
import java.util.Stack;

public class Semantico implements Constants
{
    private Stack<String> pilhaTipos = new Stack<>();
    private StringBuilder codigo = new StringBuilder();
    
    public void executeAction(int action, Token token)	throws SemanticError
    {
        switch (action) {
            case 100:
                acao100();
                break;
            case 101:
                acao101();
                break;
            case 102:
                acao102();
                break;
            case 103:
                acao103(token);
                break;
            case 104:
                acao104(token);
                break;
            case 105:
                acao105(token);
                break;
        }
        
        System.out.println("codigo = \n" + codigo.toString());
        
        // System.out.println("Ação #"+action+", Token: "+token);
    }	
    
    private void acao100() {
        codigo.append(".assembly extern mscorlib {}\n")
            .append(".assembly _programa{}\n")
            .append(".module _programa.exe\n\n")
            .append(".class public _unica {\n")
            .append(".method static public void _principal() {\n")
            .append(".entrypoint\n");
    }
    
    private void acao101() {
        codigo.append("ret\n")
            .append("}\n")
            .append("}\n");
    }
    
    private void acao102() {
        codigo.append(" call void [mscorlib]System.Console::Write(")
            .append(pilhaTipos.pop())
            .append(")\n");
    }
    
    private void acao103(Token token) {
        pilhaTipos.push("int64");
        codigo.append("ldc.i8 ").append(token.getLexeme()).append("\n")
            .append("conv.r8\n");
    }
    
    private void acao104(Token token) {
        pilhaTipos.push("float64");
        codigo.append("ldc.r8 ").append(token.getLexeme()).append("\n");
    }
    
    private void acao105(Token token) {
        pilhaTipos.push("string");
        codigo.append("ldstr ").append(token.getLexeme()).append("\n");
    }
}

package gals;

import gals.exceptions.SemanticError;
import java.util.Stack;

public class Semantico implements Constants
{
    private Stack<String> pilhaTipos = new Stack<>();
    private Stack<String> operadorRelacional = new Stack<>();
    private Stack<String> tipo = new Stack<>();
    private Stack<String> listaIdentificador = new Stack<>();
    private Stack<String> pilhaRotulos = new Stack<>();
    private Stack<String> tabelaSimbolos = new Stack<>();
    
    public StringBuilder codigo = new StringBuilder();
    
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
            case 118:
                acao118();
                break;
            case 115:
                acao115();
                break;
            case 116:
                acao116();
                break;
            case 110:
                acao110();
                break;
            case 106:
                acao106();
                break;
            case 107:
                acao107();
                break;
            case 108:
                acao108();
                break; 
            case 109:
                acao109();
                break; 
            case 111:
                acao111(token);
                break; 
            case 112:
                acao112();
                break;
            case 117:
                acao117();
                break;
            case 113:
                acao113();
                break;
            case 114:
                acao114();
                break;
            case 120:
                acao120(token);
                break; 
            case 121:
                acao121(token);
                break;
            case 119:
                acao119();
                break;
            case 122:
                acao122();
                break;
            case 123:
                acao123(token);
                break;
            case 124:
                acao124();
                break;
            case 130:
                acao130(token);
                break;
        }
    }
    
    public StringBuilder getCodigo() {
        return codigo;
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
    
    private void acao118() {
        codigo.append(" call void [mscorlib]System.Console::Write(")
            .append("\n")
            .append(")\n");
    }
    
    private void acao115() {
        pilhaTipos.push("bool");
        codigo.append("ldc.i4.1\n");
    }
    
    private void acao116() {
        pilhaTipos.push("bool");
        codigo.append("ldc.i4.0\n");
    }
    
    private void acao110() {
        codigo.append("ldc.i8 -1\n");
        codigo.append("conv.r8 \n");
        codigo.append("mul \n");
    }
    
    private void acao106() {
        String tipo1 = pilhaTipos.pop();
        String tipo2 = pilhaTipos.pop();
        
        if (tipo1.equals("int64") && tipo2.equals("int64")) {
            pilhaTipos.push("int64");
        } else {
            pilhaTipos.push("float64");
        }
        
        codigo.append("add \n");
    }
    
    private void acao107() {
        String tipo1 = pilhaTipos.pop();
        String tipo2 = pilhaTipos.pop();
        
        if (tipo1.equals("int64") && tipo2.equals("int64")) {
            pilhaTipos.push("int64");
        } else {
            pilhaTipos.push("float64");
        }
        
        codigo.append("sub \n");
    }
    
    private void acao108() {
        String tipo1 = pilhaTipos.pop();
        String tipo2 = pilhaTipos.pop();
        
        if (tipo1.equals("int64") && tipo2.equals("int64")) {
            pilhaTipos.push("int64");
        } else {
            pilhaTipos.push("float64");
        }
        
        codigo.append("mul \n");
    }
    
    private void acao109() {
        pilhaTipos.pop();
        pilhaTipos.pop();
        
        pilhaTipos.push("float64");
        
        codigo.append("div \n");
    }
    
    private void acao111(Token token) {
        operadorRelacional.push(token.getLexeme());
    }
    
    private void acao112() {
        pilhaTipos.pop();
        pilhaTipos.pop();
        
        pilhaTipos.push("bool");
        
        String opeRelacional = this.operadorRelacional.pop();
        
        switch (opeRelacional) {
            case "==":
                codigo.append("ceq \n");
                break;
            case "<":
                codigo.append("clt \n");
                break;
            case ">":
                codigo.append("cgt \n");
                break;
            case "~=":
                codigo.append("ldc.i4.1 \n");
                codigo.append("xor \n");
                break;
        }
    }
    
    private void acao117() {
        codigo.append("ldc.i4.1 \n");
        codigo.append("xor \n");
    }
    
    private void acao113() {
        pilhaTipos.pop();
        pilhaTipos.pop();
        
        pilhaTipos.push("bool");
        
        codigo.append("and \n");
    }
    
    private void acao114() {
        pilhaTipos.pop();
        pilhaTipos.pop();
        
        pilhaTipos.push("bool");
        
        codigo.append("or \n");
    }
    
    private void acao120(Token token) {
        tipo.push(token.getLexeme());
    }
    
    private void acao121(Token token) {
        listaIdentificador.push(token.getLexeme());
    }
    
    private void acao119() {
        while (!listaIdentificador.empty()) {
            tabelaSimbolos.push(listaIdentificador.pop());
        
            String tipoLocal = tipo.pop();

            switch (tipoLocal) {
                case "int":
                    tabelaSimbolos.push("int64");
                    break;
                case "float":
                    tabelaSimbolos.push("float64");
                    break;
                case "string":
                case "bool":
                   tabelaSimbolos.push(tipoLocal);
            }
        }
        
        codigo.append(".locals (");
        
        while (!tabelaSimbolos.empty()) {
            String type = tabelaSimbolos.pop();
            String id = tabelaSimbolos.pop();
            
            codigo.append(type).append(" ").append(id);
            
            if (!tabelaSimbolos.isEmpty()) {
                codigo.append(",");
            }
        }
        
        codigo.append(")");
        
        listaIdentificador.clear();
    }
    
    private void acao122() {
        String type = pilhaTipos.pop();
        
        if (type.equals("int64")) {
            codigo.append("conv.i8 \n");
        }
        
        String id = listaIdentificador.pop();
        
        codigo.append("stloc ")
            .append(id)
            .append("\n");
        
        listaIdentificador.clear();
    }
    
    private void acao123(Token token) {
        String id = token.getLexeme();
        
        if (id.equals("bool")) {
            // VERIFICAR COMO RETORNAR A MENSAGEM DE ERRO
            codigo.append("ret \n");
        } else {
            
        }
    }
    
    private void acao124() {
        codigo.append("ldstr \n");
        
        codigo.append("call void [mscorlib]System.Console::Write(")
            .append("string")
            .append(")\n");
    }
    
    private void acao130(Token token) {
        pilhaTipos.push(token.getLexeme());
    }
}

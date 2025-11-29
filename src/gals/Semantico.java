package gals;

import gals.exceptions.SemanticError;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Semantico implements Constants
{
    private final Stack<String> pilhaTipos = new Stack<>();
    private final Stack<String> operadorRelacional = new Stack<>();
    private final Stack<String> pilhaRotulos = new Stack<>();
    
    private final List<String> listaIdentificadores = new ArrayList<>();
    private final Map<String, String> tabelaSimbolos = new HashMap<>();
    
    private String tipo = "";
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
            case 125:
                acao125(token);
                break;
            case 127:
                acao127();
                break;
            case 126:
                acao126();
                break;
            case 128:
                acao128();
                break;
            case 129:
                acao129(token);
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
        codigo.append(" call void [mscorlib]System.Console::WriteLine()")
            .append("\n");
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
                codigo.append("ceq \n");
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
        tipo = token.getLexeme();
    }
    
    private void acao121(Token token) {
        listaIdentificadores.add(token.getLexeme());
    }
    
    private void acao119() {
        for (String id : listaIdentificadores) {
            String tipoIL = "";

            switch (tipo) {
                case "int":
                    tipoIL = "int64";
                    break;
                case "float":
                    tipoIL = "float64";
                    break;
                case "string":
                case "bool":
                   tipoIL = tipo;
                   break;
            }
            
            tabelaSimbolos.put(id, tipoIL);
        }
        
        codigo.append(".locals (");
        
        int count = 0;
        int total = tabelaSimbolos.size();

        for (Map.Entry<String, String> entry : tabelaSimbolos.entrySet()) {
            String id = entry.getKey();
            String tipoIL = entry.getValue();

            codigo.append(tipoIL).append(" ").append(id);

            count++;
            if (count < total) {
                codigo.append(", ");
            }
        }
        
        codigo.append(")");
        
        listaIdentificadores.clear();
    }
    
    private void acao122() {
        String type = pilhaTipos.pop();
        
        if (type.equals("int64")) {
            codigo.append("conv.i8 \n");
        }
        
        String id = listaIdentificadores.getLast();
        
        codigo.append("stloc ")
            .append(id)
            .append("\n");
        
        listaIdentificadores.clear();
    }
    
    private void acao123(Token token) throws SemanticError {
        String id = token.getLexeme();
        String type = tabelaSimbolos.get(id);
        
        if (type.equals("bool")) {
            throw new SemanticError(id + "inválido para comando de entrada", token.getPosition());
        }
        
        codigo.append("call string [mscorlib]System.Console::ReadLine() \n");
        
        switch (type) {
            case "int64":
                codigo.append("call int64 [mscorlib]System.Int64::Parse(string) \n");
                break;
            case "float64":
                codigo.append("call float64 [mscorlib]System.Double::Parse(string) \n");
                break;
        }
        
        codigo.append("stloc ").append(id).append("\n");
    }
    
    private void acao124() {
        codigo.append("ldstr \n");
        
        codigo.append("call void [mscorlib]System.Console::Write(string)")
            .append("\n");
    }
    
    private void acao130(Token token) {
        String id = token.getLexeme();
        String type = tabelaSimbolos.get(id);
        
        pilhaTipos.push(type);
        
        codigo.append("ldloc ").append(id).append("\n");
        
        if (type.equals("int64")) {
            codigo.append("conv.r8 \n");
        }
    }
    
    private void acao125(Token token) throws SemanticError {
        String type = pilhaTipos.pop();
        
        if (!type.equals("bool")) {
            throw new SemanticError("expressão incompatível em comando de seleção", token.getPosition());
        }
        
        String rotulo1 = "novo_rotulo1";
                
        codigo.append("brfalse ").append(rotulo1).append("\n");
        
        pilhaRotulos.push(rotulo1);
    }
    
    private void acao127() {
        String rotulo2 = "novo_rotulo2";
        
        codigo.append("br ").append(rotulo2).append("\n");
        
        String rotulo1 = pilhaRotulos.pop();
        
        codigo.append(rotulo1).append(":").append("\n");
        
        pilhaRotulos.push(rotulo2);
    }
    
    private void acao126() {
        String rotulo = pilhaRotulos.pop();
        
        codigo.append(rotulo).append(":").append("\n");
    }
    
    private void acao128() {
        String novoRotulo = "novo_rotulo";
        
        codigo.append(novoRotulo).append(":").append("\n");
        
        pilhaRotulos.push(novoRotulo);
    }
    
    private void acao129(Token token) throws SemanticError {
        String type = pilhaTipos.pop();
        
        if (!type.equals("bool")) {
            throw new SemanticError("expressão incompatível em comando de repetição", token.getPosition());
        }
        
        String rotulo = pilhaRotulos.pop();
        
        codigo.append("brfalse ").append(rotulo).append("\n");
    }
}

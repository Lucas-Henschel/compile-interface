/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import gals.Constants;

/**
 * Classe utilitária contendo métodos auxiliares para análise léxica.
 * 
 * @author lucas
 */
public class GalsUtils {
    
    /**
     * Retorna a classe léxica (tipo) de um token dado o seu ID.
     *
     * @param id o ID do token (definido na classe Constants)
     * @return a representação textual da classe léxica do token; retorna
     *         uma string vazia se o ID não corresponder a nenhuma classe conhecida
     */
    public static String getClassLexama(int id) {
        switch (id) {
            case Constants.t_identificador:
                return "identificador";
            case Constants.t_cint:
                return "constante_int";
            case Constants.t_cfloat:
                return "constante_float";
            case Constants.t_cstring:
                return "constante_string";
            case Constants.t_TOKEN_29:
            case Constants.t_TOKEN_30:
            case Constants.t_TOKEN_31:
            case Constants.t_TOKEN_32:
            case Constants.t_TOKEN_33:
            case Constants.t_TOKEN_34:
            case Constants.t_TOKEN_35:
            case Constants.t_TOKEN_36:
            case Constants.t_TOKEN_37:
            case Constants.t_TOKEN_38:
            case Constants.t_TOKEN_39:
            case Constants.t_TOKEN_40:
            case Constants.t_TOKEN_41:
            case Constants.t_TOKEN_42:
                return "símbolo especial";
            case Constants.t_pr_add:
            case Constants.t_pr_and:
            case Constants.t_pr_begin:
            case Constants.t_pr_bool:
            case Constants.t_pr_count:
            case Constants.t_pr_delete:
            case Constants.t_pr_do:
            case Constants.t_pr_elementOf:
            case Constants.t_pr_else:
            case Constants.t_pr_end:
            case Constants.t_pr_false:
            case Constants.t_pr_float:
            case Constants.t_pr_if:
            case Constants.t_pr_int:
            case Constants.t_pr_list:
            case Constants.t_pr_not:
            case Constants.t_pr_or:
            case Constants.t_pr_print:
            case Constants.t_pr_read:
            case Constants.t_pr_size:
            case Constants.t_pr_string:
            case Constants.t_pr_true:
            case Constants.t_pr_until:
                return "palavra reservada";
            case Constants.DOLLAR:
                return "EOF";
            default:
                return "";
        }
    }
    
    /**
     * Retorna a linha do texto correspondente à posição informada.
     *
     * @param input o texto completo
     * @param position a posição do caractere dentro do texto
     * @return o número da linha correspondente à posição
     */
    public static int getLineFromPosition(String input, int position) {
        int line = 1;
        
        for (int i = 0; i < position && i < input.length(); i++) {
            if (input.charAt(i) == '\n') {
                line++;
            }
        }
        
        return line;
    }
    
    /**
     * Retorna o lexema incorreto ou inválido a partir de uma posição no texto.
     *
     * @param input o texto completo
     * @param pos a posição inicial do lexema no texto
     * @return o lexema inválido encontrado; retorna string vazia se a posição
     *         estiver fora do intervalo do texto
     */
    public static String getWrongLexeme(String input, int pos) {
        if (pos < 0 || pos >= input.length()) return "";
        
        return String.valueOf(input.charAt(pos));
    }
}

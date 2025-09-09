package controller;

import gals.Constants;
import gals.Lexico;
import gals.Token;
import gals.exceptions.LexicalError;
import handler.FileHandler;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import view.CompileView;
import view.FileChooserView;

/**
 * Classe de controle do compilador.
 * Esta classe gerencia as ações do editor de código, atalhos de teclado,
 * operações de arquivos (abrir, salvar, criar) e interações com a interface gráfica.
 * Implementa funcionalidades básicas como copiar, colar, cortar e simula a compilação.
 * 
 * @author lucas, ana
 */
public class CompileController {
    
    /**
     * Instância única da classe CompileController (Singleton).
     */
    public static CompileController compileController;
    
    /**
     * Instância da view do compilador.
     */
    private CompileView compileView;
    
    /**
     * Instância do handler de arquivos.
     */
    private FileHandler fileHandler;
            
    /**
     * Retorna a instância única do CompileController.
     * @return instância singleton de CompileController
     */
    public static CompileController getCompileController() {
        if (compileController == null) {
            compileController = new CompileController();
        }
        
        return compileController;
    }
    
    /**
     * Inicializa as dependências do controller,
     * como a view do compilador e o handler de arquivos.
     */
    public void init() {
        compileView = CompileView.getCompileView();
        fileHandler = FileHandler.getFileHandler();
    }
    
    /**
     * Configura os atalhos de teclado para as ações do editor.
     * Suporta Ctrl+N, Ctrl+O, Ctrl+S, Ctrl+C, Ctrl+V, Ctrl+X, F7 e F1.
     */
    public void inputMapKeys() {
        InputMap inputMap = compileView.getjMain().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = compileView.getjMain().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("control N"), "new");
        actionMap.put("new", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolNewFile();
            }
        });
        
        inputMap.put(KeyStroke.getKeyStroke("control O"), "open");
        actionMap.put("open", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolOpenFile();
            }
        });
        
        inputMap.put(KeyStroke.getKeyStroke("control S"), "save");
        actionMap.put("save", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolSaveFile();
            }
        });
        
        inputMap.put(KeyStroke.getKeyStroke("control C"), "copy");
        actionMap.put("copy", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolCopy();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("control V"), "paste");
        actionMap.put("paste", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolPaste();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("control X"), "cut");
        actionMap.put("cut", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolCut();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("F7"), "compile");
        actionMap.put("compile", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolCompile();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("F1"), "team");
        actionMap.put("team", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolTeam();
            }
        });
    }
    
    /**
     * Limpa o editor, mensagens e reseta interações do arquivo.
     * Equivalente à ação "Novo Arquivo".
     */
    public void toolNewFile() {
        compileView.getjEditor().setText("");
        compileView.getjMessages().setText("");
        compileView.setPathFile("Nenhum arquivo selecionado");
        
        fileHandler.resetInteractions();
    }
    
    /**
     * Abre a janela de seleção de arquivo.
     * Equivalente à ação "Abrir Arquivo".
     */
    public void toolOpenFile() {
        FileChooserView fileChooserView = FileChooserView.getFileChooserView();
        fileChooserView.screen();
        fileChooserView.setVisible(true);
    }
    
    /**
     * Salva o conteúdo do editor em um arquivo existente
     * ou cria um novo arquivo caso não exista.
     * Equivalente à ação "Salvar Arquivo".
     */
    public void toolSaveFile() {
        if (fileHandler.getFile() != null) {
            fileHandler.saveContent();
        } else {
            fileHandler.createNewFile();
        }
        
        compileView.getjMessages().setText("");
    }
    
    /**
     * Copia o texto selecionado do editor para a área de transferência.
     */
    public void toolCopy() {
        compileView.getjEditor().copy();
    }
    
    /**
     * Cola o conteúdo da área de transferência no editor.
     */
    public void toolPaste() {
        compileView.getjEditor().paste();
    }
    
    /**
     * Corta o texto selecionado do editor e envia para a área de transferência.
     */
    public void toolCut() {
        compileView.getjEditor().cut();
    }
    
    /**
     * Simula a compilação do código exibindo mensagem no editor.
     */
    public void toolCompile() {
        compileView.getjMessages().setText("");
        
        Lexico lexico = new Lexico();
        lexico.setInput(compileView.getjEditor().getText());
        
        try {
            Token t = null;
            
            compileView.getjMessages().setText("linha       classe                          lexema\n");
            
            int row = 1;
            
            while ( (t = lexico.nextToken()) != null ) {
                String info = row + "              " + getClassLexama(t.getId()) + "       " + t.getLexeme() + "\n";
                
                compileView.getjMessages().setText(
                    compileView.getjMessages().getText() +
                    info
                );
                
                // só escreve o lexema, necessário escrever t.getId, t.getPosition()

                // t.getId () - retorna o identificador da classe (ver Constants.java) 
                // necessário adaptar, pois deve ser apresentada a classe por extenso

                // t.getPosition () - retorna a posição inicial do lexema no editor 
                // necessário adaptar para mostrar a linha	

                // esse código apresenta os tokens enquanto não ocorrer erro
                // no entanto, os tokens devem ser apresentados SÓ se não ocorrer erro,
                // necessário adaptar para atender o que foi solicitado		   
            }
            
            compileView.getjMessages().setText(
                compileView.getjMessages().getText() +
                "\nprograma compilado com sucesso"
            );
        } catch (LexicalError e) {           
           compileView.getjMessages().setText("linha " + e.getPosition() + ": " + e.getMessage());

           // e.getMessage() - retorna a mensagem de erro de SCANNER_ERRO (ver ScannerConstants.java)
           // necessário adaptar conforme o enunciado da parte 2

           // e.getPosition() - retorna a posição inicial do erro 
           // necessário adaptar para mostrar a linha  
        } 
        
    }
    
    private String getClassLexama(int id) {
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
            default:
                return "";
        }
    }
    
    /**
     * Exibe os nomes da equipe responsável pelo projeto.
     */
    public void toolTeam() {
        compileView.getjMessages().setText("Ana Caroline Henschel\nFrederico Rudolf Uesler\nLucas Gabriel Henschel");
    }
    
    /**
     * Preenche o editor com o conteúdo do arquivo atual
     * e atualiza o caminho do arquivo exibido na interface.
     * Mostra mensagem de erro caso não seja possível ler o arquivo.
     */
    public void fillWindow() {
        try {
            String fileContent = new String(Files.readAllBytes(fileHandler.getFile().toPath()), StandardCharsets.UTF_8);
            
            compileView.setPathFile(fileHandler.getFile().getAbsolutePath());
            compileView.getjEditor().setText(fileContent);
            compileView.getjMessages().setText("");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo: " + ex.getMessage());
        }
    }
}

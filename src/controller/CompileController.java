package controller;

import gals.Sintatico;
import gals.GalsUtils;
import gals.Lexico;
import gals.Semantico;
import gals.Token;
import gals.exceptions.LexicalError;
import gals.exceptions.SemanticError;
import gals.exceptions.SyntaticError;
import handler.FileHandler;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
        Sintatico sintatico = new Sintatico();
        Semantico semantico = new Semantico();
        
        lexico.setInput(compileView.getjEditor().getText());
        
        try {
            sintatico.parse(lexico, semantico);

            compileView.getjMessages().setText("programa compilado com sucesso");
        } catch (LexicalError e) {
            String input = compileView.getjEditor().getText();
            
            int pos = e.getPosition();
            int line = GalsUtils.getLineFromPosition(input, pos);
            
            String msg = e.getMessage();

            String messageToShow;
            if (msg.equals("símbolo inválido")) {
                String wrongLexeme = sintatico.getCurrentToken().getLexeme();
                messageToShow = String.format("linha %d: %s %s", line, wrongLexeme, msg);
            } else {
                messageToShow = String.format("linha %d: %s", line, msg);
            }

            compileView.getjMessages().setText(messageToShow);
        } catch (SyntaticError e) {            
            String input = compileView.getjEditor().getText();
            
            int pos = e.getPosition();
            int line = GalsUtils.getLineFromPosition(input, pos);
            String classLexama = GalsUtils.getClassLexama(sintatico.getCurrentToken().getId());
            
            StringBuilder sb = new StringBuilder();
            String foundValue = sintatico.getCurrentToken().getLexeme();
            
            if ("EOF".equals(classLexama) || "constante_string".equals(classLexama)) {                
                foundValue = classLexama;
            }
            
            sb
                .append("linha ")
                .append(line)
                .append(": ")
                .append("encontrado ")
                .append(foundValue)
                .append(" esperado ")
                .append(e.getMessage());
            
            compileView.getjMessages().setText(sb.toString());
        } catch (SemanticError e) {
           // trata erros semânticos na parte 4
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

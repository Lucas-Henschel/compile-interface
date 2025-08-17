/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

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
 *
 * @author lucas
 */
public class CompileController {
    public static CompileController compileController;
    
    private CompileView compileView;
    
    private FileHandler fileHandler;
        
    public static CompileController getCompileController() {
        if (compileController == null) {
            compileController = new CompileController();
        }
        
        return compileController;
    }
    
    public void init() {
        compileView = CompileView.getCompileView();
        fileHandler = FileHandler.getFileHandler();
    }
    
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
    
    public void toolNewFile() {
        compileView.getjEditor().setText("");
        compileView.getjMessages().setText("");
        compileView.setPathFile("Nenhum arquivo selecionado");
        
        fileHandler.resetInteractions();
    }
    
    public void toolOpenFile() {
        FileChooserView fileChooserView = FileChooserView.getFileChooserView();
        fileChooserView.screen();
        fileChooserView.setVisible(true);
    }
    
    public void toolSaveFile() {
        if (fileHandler.getFile() != null) {
            // salvar as informações do arquivo 
            compileView.getjMessages().setText("");
        } else {
            // abrir o modal para salvar o arquivo
        }
    }
    
    public void toolCopy() {
        System.out.println("Atalho Ctrl+C acionado (Copiar)");
    }
    
    public void toolPaste() {
        System.out.println("Atalho Ctrl+V acionado (Colar)");
    }
    
    public void toolCut() {
        System.out.println("Atalho Ctrl+X acionado (Recortar)");
    }
    
    public void toolCompile() {
        compileView.getjMessages().setText("compilação de programas ainda não foi implementada");
    }
    
    public void toolTeam() {
        compileView.getjMessages().setText("Ana Caroline Henschel\nLucas Gabriel Henschel");
    }
    
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

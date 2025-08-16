/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import view.CompileView;

/**
 *
 * @author lucas
 */
public class CompileController {
    public static CompileController compileController;
        
    public static CompileController getCompileController() {
        if (compileController == null) {
            compileController = new CompileController();
        }
        
        return compileController;
    }
    
    public void inputMapKeys() {
        CompileView compileView = CompileView.getCompileView();
        
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
    }
    
    public void toolNewFile() {
        System.out.println("Atalho Ctrl+N acionado (Novo)");
    }
    
    public void toolOpenFile() {
        System.out.println("Atalho Ctrl+O acionado (Abrir)");
    }
    
    public void toolSaveFile() {
        System.out.println("Atalho Ctrl+S acionado (Salvar)");
    }
    
    public void toolCopy() {
        
    }
    
    public void toolPast() {
        
    }
    
    public void toolCut() {
        
    }
    
    public void toolCompile() {
        
    }
    
    public void toolTeam() {
        
    }
}

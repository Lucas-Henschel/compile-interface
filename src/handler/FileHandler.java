/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handler;

import java.io.File;
import view.CompileView;

/**
 *
 * @author lucas
 */
public class FileHandler {
    public static FileHandler fileHandler;
    
    private CompileView compileView = CompileView.getCompileView();
    
    private File file;
    
    public static FileHandler getFileHandler() {
        if (fileHandler == null) {
            fileHandler = new FileHandler();
        }
        
        return fileHandler;
    }
    
    public void resetInteractions() {
        file = null;
        
        compileView.setPathFile("Nenhum arquivo selecionado");
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}

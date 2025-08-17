/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    
    public void saveContent() {
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(compileView.getjEditor().getText());
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao salvar o conteúdo no arquivo: " + ex.getMessage());
        }
    }
    
    public void createNewFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar arquivo como...");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de texto (*.txt)", "txt");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
            }

            this.file = selectedFile; 
            compileView.setPathFile(file.getAbsolutePath());
            
            saveContent();
        } 
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}

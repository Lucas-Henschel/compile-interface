/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import handler.FileHandler;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import view.FileChooserView;

/**
 * Controlador responsável por gerenciar a seleção e leitura de arquivos.
 */
public class FileChooseController {

    /**
     * Instância singleton do FileChooseController.
     */
    public static FileChooseController fileController;

    /**
     * Instância para o gerenciamento do arquivo.
     */
    private final FileHandler fileHandler = FileHandler.getFileHandler();
    
    /**
     * Instância do controller do compilador.
     */
    private final CompileController compileController = CompileController.getCompileController();
    
    /**
     * Componente de seleção de arquivos.
     */
    private JFileChooser jFileChooser;

    /**
     * Retorna a instância singleton de {@code FileChooseController}.
     *
     * @return instância de {@code FileChooseController}
     */
    public static FileChooseController getFileController() {
        if (fileController == null) {
            fileController = new FileChooseController();
        }
        
        return fileController;
    }

    /**
     * Configura o filtro de arquivos e inicia o listener do {@code JFileChooser}.
     * Permite apenas arquivos com extensão .txt e .html.
     */
    public void screen() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Arquivos TXT", "txt"
        );
        
        jFileChooser.setFileFilter(filter);
        startListener();
    }

    /**
     * Inicia o listener responsável por tratar a seleção de arquivos.
     * Após a seleção, atualiza a interface e chama o tratamento do arquivo.
     */
    private void startListener() {
        if (jFileChooser == null) return;

        jFileChooser.addActionListener(e -> {
            if (JFileChooser.APPROVE_SELECTION.equals(e.getActionCommand())) {
                File selectedFile = jFileChooser.getSelectedFile();
                fileHandler.setFile(selectedFile);
                
                compileController.fillWindow();
            }
            
            FileChooserView.getFileChooserView().dispose();
        });
    }

    /**
     * Retorna o componente {@code JFileChooser} atual.
     *
     * @return o {@code JFileChooser}
     */
    public JFileChooser getjFileChooser() {
        return jFileChooser;
    }

    /**
     * Define o componente {@code JFileChooser}.
     *
     * @param jFileChooser o novo {@code JFileChooser}
     */
    public void setjFileChooser(JFileChooser jFileChooser) {
        this.jFileChooser = jFileChooser;
    }
}

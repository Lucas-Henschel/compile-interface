/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import view.CompileView;
import view.FileChooserView;

/**
 * Controlador responsável por gerenciar a seleção e leitura de arquivos.
 */
public class FileController {

    /**
     * Instância singleton do FileController.
     */
    public static FileController fileController;

    /**
     * Componente de seleção de arquivos.
     */
    private JFileChooser jFileChooser;

    /**
     * Caminho absoluto do arquivo selecionado.
     */
    private String absolutePath;

    /**
     * Retorna a instância singleton de {@code FileController}.
     *
     * @return instância de {@code FileController}
     */
    public static FileController getFileController() {
        if (fileController == null) {
            fileController = new FileController();
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
     * Reseta o caminho absoluto.
     */
    public void resetInteractions() {
        absolutePath = "";
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
                setAbsolutePath(selectedFile.getAbsolutePath());
                
                try {
                    String fileContent = new String(Files.readAllBytes(selectedFile.toPath()), StandardCharsets.UTF_8);

                    CompileView compileView = CompileView.getCompileView();
                    compileView.setPathFile(absolutePath);

                    compileView.getjEditor().setText(fileContent);
                    compileView.getjMessages().setText("");

                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo: " + ex.getMessage());
                }
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

    /**
     * Retorna o caminho absoluto do arquivo selecionado.
     *
     * @return caminho absoluto do arquivo
     */
    public String getAbsolutePath() {
        return absolutePath;
    }

    /**
     * Define o caminho absoluto do arquivo selecionado.
     *
     * @param absolutePath novo caminho absoluto
     */
    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }
}

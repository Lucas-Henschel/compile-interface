package handler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import view.CompileView;

/**
 * Classe responsável por manipular arquivos no editor.
 * Permite criar novos arquivos, salvar conteúdo, resetar interações
 * e gerenciar o arquivo atualmente aberto.
 * Implementa o padrão Singleton.
 * 
 * @author lucas, ana
 */
public class FileHandler {

    /**
     * Instância única do FileHandler (Singleton).
     */
    public static FileHandler fileHandler;
    
    /**
     * Instância da view do compilador.
     */
    private CompileView compileView = CompileView.getCompileView();
    
    /**
     * Arquivo atualmente selecionado ou aberto.
     */
    private File file;
    
    /**
     * Retorna a instância única do FileHandler.
     * @return instância singleton de FileHandler
     */
    public static FileHandler getFileHandler() {
        if (fileHandler == null) {
            fileHandler = new FileHandler();
        }
        return fileHandler;
    }
    
    /**
     * Reseta o arquivo atual e atualiza a interface para indicar
     * que nenhum arquivo está selecionado.
     */
    public void resetInteractions() {
        file = null;
        compileView.setPathFile("Nenhum arquivo selecionado");
    }
    
    /**
     * Salva o conteúdo do editor no arquivo atualmente selecionado.
     * Caso ocorra algum erro, exibe uma mensagem na interface.
     */
    public void saveContent() {
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(compileView.getjEditor().getText());
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao salvar o conteúdo no arquivo: " + ex.getMessage());
        }
    }
    
    /**
     * Abre uma janela de diálogo para criar um novo arquivo de texto (.txt),
     * salva o conteúdo do editor no arquivo criado e atualiza a interface
     * com o caminho do arquivo.
     */
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

    /**
     * Retorna o arquivo atualmente selecionado ou aberto.
     * @return arquivo atual
     */
    public File getFile() {
        return file;
    }

    /**
     * Define o arquivo atualmente selecionado ou aberto.
     * @param file arquivo a ser definido como atual
     */
    public void setFile(File file) {
        this.file = file;
    }
}

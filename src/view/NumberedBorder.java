package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JTextArea;
import javax.swing.border.AbstractBorder;

/**
 * Borda numerada para JTextArea.
 * Exibe os números das linhas ao lado do componente de texto, 
 * adaptado de: http://www.guj.com.br/posts/list/123003.java#665398
 * 
 * Esta classe estende {@link AbstractBorder} e desenha os números das linhas
 * e uma linha vertical à esquerda do JTextArea.
 */
public class NumberedBorder extends AbstractBorder {

    private static final long serialVersionUID = -5089118025935944759L;

    /** Altura de cada linha do texto */
    private static int lineHeight;

    /** Altura de um caractere (em pixels) usada para calcular o deslocamento */
    private final int characterHeight = 8;

    /** Largura de um caractere (em pixels) usada para calcular o deslocamento */
    private final int characterWidth = 7;

    /** Cor utilizada para desenhar os números das linhas e a borda */
    private final Color myColor;

    /**
     * Construtor padrão.
     * Define a cor padrão da borda e números das linhas.
     */
    NumberedBorder() {
        myColor = new Color(164, 164, 164);
    }

    /**
     * Desenha a borda e os números das linhas ao lado do JTextArea.
     * @param c componente a ser desenhado (deve ser JTextArea)
     * @param g contexto gráfico para desenhar
     * @param x posição X da borda
     * @param y posição Y da borda
     * @param width largura da borda
     * @param height altura da borda
     */
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        JTextArea textArea = (JTextArea) c;
        Font font = textArea.getFont();
        FontMetrics metrics = g.getFontMetrics(font);
        lineHeight = metrics.getHeight();

        Color oldColor = g.getColor();
        g.setColor(myColor);

        int lineLeft = calculateLeft(height) + 10;

        int px = 0;
        int py = 0;
        int length = 0;

        int visibleLines = textArea.getHeight() / lineHeight;
        for (int i = 0; i < visibleLines; i++) {
            String str = String.valueOf(i + 1);
            length = str.length();

            py = lineHeight * i + 14;
            px = lineLeft - (characterWidth * length) - 2;

            g.drawString(str, px, py);
        }

        g.drawLine(lineLeft, 0, lineLeft, height);

        g.setColor(oldColor);
    }

    /**
     * Retorna os insets da borda, considerando a largura necessária
     * para desenhar os números das linhas.
     * @param c componente que possui a borda
     * @return insets da borda
     */
    @Override
    public Insets getBorderInsets(Component c) {
        int left = calculateLeft(c.getHeight()) + 13;
        return new Insets(1, left, 1, 1);
    }

    /**
     * Calcula a distância da borda esquerda necessária para desenhar os números das linhas.
     * @param height altura do componente
     * @return deslocamento à esquerda em pixels
     */
    private int calculateLeft(int height) {
        double r = (double) height / (double) lineHeight;
        int rows = (int) (r + 0.5);
        String str = String.valueOf(rows);
        int length = str.length();

        return characterHeight * length;
    }
}

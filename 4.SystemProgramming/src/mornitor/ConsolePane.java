package mornitor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConsolePane extends JPanel {
	private static int count;
    private static String text;
    
	public ConsolePane() {
		this.setBackground(Color.white);
		this.setBorder(BorderFactory.createLineBorder(Color.black, 3));

        JLabel sConsole = new JLabel("<Console>");
        sConsole.setHorizontalAlignment(JLabel.CENTER);
        sConsole.setFont(new Font("SansSerif", Font.PLAIN, 20));
        sConsole.setOpaque(true);
        sConsole.setBackground(Color.white);
        sConsole.setForeground(Color.black);
        this.add(sConsole);
        count++;
        count++;
	
	}
	
	
	@Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 20));
        if (getText()!=null)g2d.drawString(getText(), 10, count*25);
    }


	public static String getText() {
		return text;
	}


	public static void setText(String text) {
		ConsolePane.text = text;
	}
	
	
}

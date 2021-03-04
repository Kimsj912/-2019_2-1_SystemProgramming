package mornitor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class CodeFrame extends JFrame {

	private JPanel codePane;
	public JList<String> codeList;

	public CodeFrame() {
		super("code Frame");
		this.setBounds(100, 100, 300, 800);
        
		Container con = this.getContentPane();
		this.codePane = new JPanel();
		this.codePane.setLayout(new BorderLayout());
		this.codePane.setBackground(Color.white);
		this.codePane.setBorder(BorderFactory.createLineBorder(Color.black, 3));

		JLabel sCode = new JLabel("<code>");
		sCode.setHorizontalAlignment(JLabel.CENTER);
		sCode.setFont(new Font("SansSerif", Font.PLAIN, 20));
		sCode.setOpaque(true);
		sCode.setBackground(Color.white);
		sCode.setForeground(Color.black);
		this.codePane.add(sCode, BorderLayout.NORTH);

		codeList = new JList<String>();
		codeList.setFont(new Font("SansSerif", Font.PLAIN, 20));
		JScrollPane scrollPane = new JScrollPane(codeList);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		codePane.add(scrollPane);

		con.add(codePane);

		this.setVisible(true);
	}

}

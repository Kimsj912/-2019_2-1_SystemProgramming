package mornitor;

import java.awt.Color;
import java.awt.Font;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AddressPanel extends JPanel {

	public JLabel driveName;
//	public Vector<String> dircetoryStrings;

	public AddressPanel() {
		this.setBackground(Color.white);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		JLabel stringn = new JLabel("аж╪р ");
		stringn.setFont(new Font("SansSerif", Font.PLAIN, 20));
		stringn.setOpaque(true);
		stringn.setBackground(Color.black);
		stringn.setForeground(Color.white);
		this.add(stringn);

		this.driveName = new JLabel("");
		this.driveName.setBounds(30, 30, 100, 40);
		this.driveName.setFont(new Font("SansSerif", Font.PLAIN, 20));
		this.driveName.setOpaque(true);
		this.driveName.setBackground(Color.white);
		this.add(driveName);

	}

}

package net.pferdimanzug.hearthstone.analyzer.playmode.turn_log;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class GameActionEntry extends JPanel {
	private JLabel iconLabel;
	private JTextArea textLabel;

	public GameActionEntry() {
		setLayout(new FlowLayout());
		add(iconLabel = new JLabel());
		textLabel = new JTextArea();
		textLabel.setWrapStyleWord(true);
		textLabel.setLineWrap(true);
		textLabel.setFont(new Font("Arial", Font.PLAIN, 10));
		textLabel.setEditable(false);
		add(textLabel);
	}
	
	protected void setIcon(Icon icon) {
		iconLabel.setIcon(icon);
	}
	
	protected void setText(String text) {
		textLabel.setText(text);
	}
}

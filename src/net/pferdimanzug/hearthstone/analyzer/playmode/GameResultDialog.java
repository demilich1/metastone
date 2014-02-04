package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;

public class GameResultDialog extends JDialog {
	
	public GameResultDialog(GameContext context, Player player) {
		setLayout(new BorderLayout());
		
		boolean won = context.getWinner() == player;
		String text = won ? "You win!" : "You loose :(";
		JLabel label = new JLabel(text);
		label.setFont(new Font("Arial", Font.BOLD, 44));
		label.setForeground(won ? Color.GREEN : Color.RED);
		add(label, BorderLayout.CENTER);
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		pack();
		setVisible(true);
	}

}

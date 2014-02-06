package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.playmode.turn_log.TurnLogPanel;

public class PlayModeWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final PlayModePanel renderer;
	private final TurnLogPanel turnLog;

	public PlayModeWindow() {
		setTitle("Hearthstone Analyzer");
		setSize(1280, 800);

		// make tooltips appear instantly
		ToolTipManager.sharedInstance().setInitialDelay(0);

		// setLayout(new FlowLayout(FlowLayout.LEFT));
		renderer = new PlayModePanel();
		add(renderer);
		turnLog = new TurnLogPanel();
		add(turnLog, BorderLayout.EAST);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setLookAndFeel();

		//setResizable(false);
		setVisible(true);
	}

	private void setLookAndFeel() {
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
	}

	public void update(GameContext context) {
		renderer.update(context);
	}
	
	public void updateTurnLog(GameContext context) {
		turnLog.showActions((GameContextVisualizable) context);
	}

}

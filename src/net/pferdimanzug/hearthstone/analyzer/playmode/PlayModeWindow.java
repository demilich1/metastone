package net.pferdimanzug.hearthstone.analyzer.playmode;

import javax.swing.JFrame;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;

public class PlayModeWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final PlayModePanel renderer;

	public PlayModeWindow() {
		setTitle("Hearthstone Analyzer");
		setSize(1024, 768);

		// make tooltips appear instantly
		ToolTipManager.sharedInstance().setInitialDelay(0);

		// setLayout(new FlowLayout(FlowLayout.LEFT));
		renderer = new PlayModePanel();
		add(renderer);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setLookAndFeel();

		setResizable(false);
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

}

package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.WindowConstants;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanActionOptions;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class HumanTargetPromptDialog extends JDialog {
	
	private final GameAction action;

	public HumanTargetPromptDialog(JDialog owner, GameContext context, GameAction action) {
		super(owner);
		this.action = action;
		setTitle("Choose a target...");
		
		setLayout(new GridLayout(0, 1));
		for (Entity target : action.getValidTargets()) {
			JButton actionButton = createActionButton(target);
			add(actionButton);
		}
		JButton endTurnButton = createActionButton(null);
		add(endTurnButton);
		setModal(true);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		pack();
		setLocation(owner.getWidth(), owner.getY());
		setVisible(true);
	}
	
	private JButton createActionButton(final Entity target) {
		JButton button = new JButton(target != null ? target.toString() : "NONE");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				action.setTarget(target);
				setVisible(false);
				dispose();
			}
		});
		return button;
	}

}

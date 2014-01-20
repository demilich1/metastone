package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanActionOptions;

public class HumanActionPromptDialog extends JDialog {
	
	private final HumanActionOptions actionPrompt;

	public HumanActionPromptDialog(HumanActionOptions actionPrompt) {
		this.actionPrompt = actionPrompt;
		setTitle("Choose an action...");
		
		setLayout(new GridLayout(0, 1));
		for (GameAction action : actionPrompt.getValidActions()) {
			JButton actionButton = createActionButton(action);
			add(actionButton);
		}
		
		setModal(true);
		pack();
		setVisible(true);
	}
	
	private JButton createActionButton(final GameAction action) {
		JButton button = new JButton(action.toString());
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				actionPrompt.getBehaviour().setSelectedAction(action);
			}
		});
		return button;
	}

}

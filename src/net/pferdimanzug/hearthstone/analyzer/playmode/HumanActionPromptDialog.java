package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.WindowConstants;

import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanActionOptions;

@SuppressWarnings("serial")
public class HumanActionPromptDialog extends JDialog {
	
	private final HumanActionOptions actionPrompt;

	public HumanActionPromptDialog(Frame owner, HumanActionOptions actionPrompt) {
		super(owner);
		this.actionPrompt = actionPrompt;
		setTitle("Choose an action...");
		
		setLayout(new GridLayout(0, 1));
		for (GameAction action : actionPrompt.getValidActions()) {
			JButton actionButton = createActionButton(action);
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
	
	private JButton createActionButton(final GameAction action) {
		JButton button = new JButton(action != null ? action.toString() : "END TURN");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				actionPrompt.getBehaviour().setSelectedAction(action);
				setVisible(false);
				dispose();
			}
		});
		return button;
	}

}

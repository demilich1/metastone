package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.HeroPowerAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PhysicalAttackAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanActionOptions;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanTargetOptions;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

@SuppressWarnings("serial")
public class HumanActionPromptDialog extends JDialog {
	
	private final HumanActionOptions actionPrompt;

	public HumanActionPromptDialog(Frame owner, HumanActionOptions actionPrompt) {
		super(owner);
		this.actionPrompt = actionPrompt;
		setTitle("Choose an action...");
		
		setLayout(new GridLayout(0, 1));
		JLabel headerLabel = new JLabel("Choose your action:");
		headerLabel.setFont(new Font("Arial", Font.BOLD, 14));
		add(headerLabel);
		for (GameAction action : actionPrompt.getValidActions()) {
			JButton actionButton = createActionButton(actionPrompt.getContext(), action);
			add(actionButton);
		}
		JButton endTurnButton = createActionButton(actionPrompt.getContext(), null);
		add(endTurnButton);
		setModal(true);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		pack();
		setLocation(owner.getWidth(), owner.getY());
		setVisible(true);
	}
	
	private JButton createActionButton(final GameContext context, final GameAction action) {
		JButton button = new JButton(action != null ? getActionString(context, action) : "END TURN");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (action != null && action.getValidTargets() != null && !action.getValidTargets().isEmpty()) {
					ApplicationFacade.getInstance().sendNotification(GameNotification.HUMAN_PROMPT_FOR_TARGET, new HumanTargetOptions(actionPrompt.getBehaviour(), action));
					action.setTarget(actionPrompt.getBehaviour().getSelectedTarget());
				}
				actionPrompt.getBehaviour().setSelectedAction(action);
				setVisible(false);
				dispose();
			}
		});
		return button;
	}
	
	private String getActionString(GameContext context, GameAction action) {
		PlayCardAction playCardAction = null;
		switch (action.getActionType()) {
		case HERO_POWER:
			HeroPowerAction heroPowerAction = (HeroPowerAction) action;
			return "HERO POWER: " + heroPowerAction.getHeroPower().getName();
		case MINION_ABILITY:
			break;
		case PHYSICAL_ATTACK:
			PhysicalAttackAction physicalAttackAction = (PhysicalAttackAction) action;
			Entity attacker = context.resolveSingleTarget(0, physicalAttackAction.getAttackerReference());
			return "ATTACK with " + attacker.getName();
		case SPELL:
			playCardAction = (PlayCardAction) action;
			return "CAST SPELL: " + playCardAction.getCard().getName();
		case SUMMON:
			playCardAction = (PlayCardAction) action;
			return "SUMMON: " + playCardAction.getCard().getName();
		case UNDEFINED:
			break;
		default:
			break;
		}
		return "<unknown action " + action.getActionType() + ">";
	}

}

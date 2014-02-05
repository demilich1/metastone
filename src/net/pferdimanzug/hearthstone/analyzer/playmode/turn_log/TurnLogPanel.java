package net.pferdimanzug.hearthstone.analyzer.playmode.turn_log;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PhysicalAttackAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.playmode.GameContextVisualizable;

@SuppressWarnings("serial")
public class TurnLogPanel extends JPanel {
	
	public TurnLogPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public void showActions(GameContextVisualizable context) {
		add(getActionPanel(context));
	}
	
	private JPanel getActionPanel(GameContextVisualizable context) {
		Player player = context.getActivePlayer();
		GameAction action = context.getCurrentAction();
		if (action == null) {
			return new TurnEndActionEntry(player);
		}
		switch (action.getActionType()) {
		case HERO_POWER:
			return new PlayCardActionEntry(player, (PlayCardAction) action);
		case MINION_ABILITY:
			return new BattlecryActionEntry((Battlecry) action);
		case PHYSICAL_ATTACK:
			return new PhysicalAttackActionEntry(context, player, (PhysicalAttackAction) action);
		case SPELL:
			return new PlayCardActionEntry(player, (PlayCardAction) action);
		case SUMMON:
			return new PlayCardActionEntry(player, (PlayCardAction) action);
		case UNDEFINED:
			break;
		default:
			break;

		}
		return null;
	}

}

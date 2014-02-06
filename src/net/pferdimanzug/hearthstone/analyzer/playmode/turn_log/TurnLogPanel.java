package net.pferdimanzug.hearthstone.analyzer.playmode.turn_log;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PhysicalAttackAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.playmode.GameContextVisualizable;

@SuppressWarnings("serial")
public class TurnLogPanel extends JPanel {

	private final JPanel contentPanel;
	private final List<JPanel> messages = new ArrayList<>();

	public TurnLogPanel() {
		contentPanel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(contentPanel);
		setPreferredSize(new Dimension(200, 800));
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		add(scrollPane);
	}

	public void showActions(GameContextVisualizable context) {
		contentPanel.removeAll();
		messages.add(getActionPanel(context));
		for (int i = messages.size() - 1; i >= 0; i--) {
			JPanel message = messages.get(i);
			contentPanel.add(message);
		}
		revalidate();
		repaint();
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

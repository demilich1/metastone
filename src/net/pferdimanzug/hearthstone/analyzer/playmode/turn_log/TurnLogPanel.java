package net.pferdimanzug.hearthstone.analyzer.playmode.turn_log;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PhysicalAttackAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.events.DamageEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.HealEvent;
import net.pferdimanzug.hearthstone.analyzer.playmode.GameContextVisualizable;

@SuppressWarnings("serial")
public class TurnLogPanel extends JPanel {

	private final JPanel contentPanel;
	private final List<JPanel> messages = new ArrayList<>();

	public TurnLogPanel() {
		setLayout(new BorderLayout());
		contentPanel = new JPanel();
		setPreferredSize(new Dimension(200, 700));
		JScrollPane scrollPane = new JScrollPane(contentPanel);
		// scrollPane.setMaximumSize(new Dimension(200, 720));
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		add(scrollPane, BorderLayout.CENTER);
		// add(contentPanel);
	}

	public void showActions(GameContextVisualizable context) {
		contentPanel.removeAll();
		messages.add(getActionEntry(context));
		for (GameEvent gameEvent : context.getEventsForAction(context.getCurrentAction())) {
			GameLogEntry eventEntry = getEventEntry(gameEvent);
			if (eventEntry == null) {
				continue;
			}
			messages.add(eventEntry);
		}
		for (int i = messages.size() - 1; i >= 0; i--) {
			JPanel message = messages.get(i);
			contentPanel.add(message);
		}
		contentPanel.add(Box.createVerticalGlue());

		contentPanel.revalidate();
		contentPanel.repaint();
	}

	private GameLogEntry getActionEntry(GameContextVisualizable context) {
		Player player = context.getActivePlayer();
		GameAction action = context.getCurrentAction();

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
		case END_TURN:
			return new TurnEndActionEntry(player);
		case UNDEFINED:
			break;
		default:
			break;

		}
		return null;
	}

	private GameLogEntry getEventEntry(GameEvent gameEvent) {
		switch (gameEvent.getEventType()) {
		case DAMAGE:
			return new DamageEventEntry((DamageEvent) gameEvent);
		case HEAL:
			return new HealEventLog((HealEvent) gameEvent);
		case KILL:
			break;
		case PHYSICAL_ATTACK:
			break;
		case SPELL_CASTED:
			break;
		case SUMMON:
			break;
		case TURN_END:
			break;
		case TURN_START:
			break;
		default:
			break;
		}
		return null;
	}

}

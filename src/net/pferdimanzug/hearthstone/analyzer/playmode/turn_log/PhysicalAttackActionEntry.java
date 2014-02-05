package net.pferdimanzug.hearthstone.analyzer.playmode.turn_log;

import javax.swing.ImageIcon;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PhysicalAttackAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class PhysicalAttackActionEntry extends GameActionEntry {
	
	public PhysicalAttackActionEntry(GameContext context, Player player, PhysicalAttackAction physicalAttackAction) {
		ImageIcon icon = new ImageIcon(
				BattlecryActionEntry.class.getResource("/net/pferdimanzug/hearthstone/analyzer/resources/img/common/icon_attack_action.png"));
		setIcon(icon);
		Entity attacker = context.resolveSingleTarget(player.getId(), physicalAttackAction.getAttackerReference());
		Entity defender = context.resolveSingleTarget(player.getId(), physicalAttackAction.getTargetKey());
		setText(attacker.getName() + " attacks " + defender);
	}

}

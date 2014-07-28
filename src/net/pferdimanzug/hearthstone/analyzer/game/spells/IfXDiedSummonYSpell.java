package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.UniqueMinion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class IfXDiedSummonYSpell extends Spell {
	
	private final UniqueMinion x;
	private final MinionCard y;

	public IfXDiedSummonYSpell(UniqueMinion x, MinionCard y) {
		this.x = x;
		this.y = y;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		for (Minion deadMinion : player.getGraveyard()) {
			if (deadMinion.getTag(GameTag.UNIQUE_MINION) == x) {
				context.getLogic().summon(player.getId(), y.summon(), null, null, false);
				break;
			}
		}
	}

}

package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class ReviveMinionSpell extends Spell {
	
	private final int hpAdjustment;

	public ReviveMinionSpell(int hpAdjustment) {
		this.hpAdjustment = hpAdjustment;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Actor targetActor = (Actor) target;
		MinionCard minionCard = (MinionCard) targetActor.getSourceCard();
		Minion minion = minionCard.summon();
		minion.removeTag(GameTag.BATTLECRY);
		if (hpAdjustment != 0) {
			minion.setHp(hpAdjustment);
		}
		context.getLogic().summon(player.getId(), minion, null, null, false);
	}

}

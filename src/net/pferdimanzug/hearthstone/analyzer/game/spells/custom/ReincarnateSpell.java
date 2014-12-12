package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class ReincarnateSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(ReincarnateSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Minion minion = (Minion) target;
		int ownerId = minion.getOwner();
		MinionCard sourceCard = (MinionCard) minion.getSourceCard();
		context.getLogic().markAsDestroyed(minion);
		context.getLogic().summon(ownerId, sourceCard.summon());
	}

}

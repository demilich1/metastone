package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

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

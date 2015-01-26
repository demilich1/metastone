package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class CloneMinionSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(CloneMinionSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Minion template = (Minion) target;
		MinionCard sourceCard = (MinionCard) template.getSourceCard();
		context.getLogic().summon(player.getId(), sourceCard.summon());
	}

}

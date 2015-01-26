package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class AncestralSpiritSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(AncestralSpiritSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Actor targetMinion = (Actor) target;
		MinionCard minionCard = (MinionCard) targetMinion.getSourceCard();
		targetMinion.addDeathrattle(SummonSpell.create(minionCard));
	}
}

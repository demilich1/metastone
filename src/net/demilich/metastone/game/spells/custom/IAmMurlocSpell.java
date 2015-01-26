package net.demilich.metastone.game.spells.custom;

import java.util.concurrent.ThreadLocalRandom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.Murloc;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class IAmMurlocSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(IAmMurlocSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int numberOfMurlocs = ThreadLocalRandom.current().nextInt(3, 6);
		MinionCard murlocCard = new Murloc();
		for (int i = 0; i < numberOfMurlocs; i++) {
			context.getLogic().summon(player.getId(), murlocCard.summon());
		}
	}

}

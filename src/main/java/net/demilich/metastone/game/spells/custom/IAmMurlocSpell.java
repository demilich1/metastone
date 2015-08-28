package net.demilich.metastone.game.spells.custom;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class IAmMurlocSpell extends Spell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(IAmMurlocSpell.class);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int numberOfMurlocs = ThreadLocalRandom.current().nextInt(3, 6);
		// MinionCard murlocCard = new Murloc();
		MinionCard murlocCard = (MinionCard) CardCatalogue.getCardById("token_murloc");
		for (int i = 0; i < numberOfMurlocs; i++) {
			context.getLogic().summon(player.getId(), murlocCard.summon());
		}
	}

}

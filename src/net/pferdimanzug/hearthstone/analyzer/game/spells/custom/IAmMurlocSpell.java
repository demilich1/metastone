package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral.Murloc;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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

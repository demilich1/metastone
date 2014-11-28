package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.Environment;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class SpawnTokenSpell extends Spell {
	
	public static SpellDesc create(MinionCard... tokens) {
		SpellDesc desc = new SpellDesc(SpawnTokenSpell.class);
		desc.set(SpellArg.CARDS, tokens);
		desc.setTarget(EntityReference.NONE);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		MinionCard[] minionCards = (MinionCard[]) desc.get(SpellArg.CARDS);
		int index = (int) context.getEnvironment().get(Environment.TOKEN_INDEX);
		for (MinionCard minionCard : minionCards) {
			context.getLogic().summon(player.getId(), minionCard.summon(), null, index, false);	
		}
	}

}

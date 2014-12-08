package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.druid.Treant;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.InstantDestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class PoisonSeedsSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(PoisonSeedsSpell.class);
		desc.setTarget(EntityReference.NONE);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Player opponent = context.getOpponent(player);
		
		poisonSeeds(context, opponent);
		poisonSeeds(context, player);
	}
	
	private void poisonSeeds(GameContext context, Player player) {
		int minionCount = player.getMinions().size();
		if (minionCount == 0) {
			return;
		}
		SpellDesc destroy = InstantDestroySpell.create();
		destroy.setTarget(EntityReference.FRIENDLY_MINIONS);
		context.getLogic().castSpell(player.getId(), destroy);
		
		MinionCard[] treants = new MinionCard[minionCount];
		for (int i = 0; i < minionCount; i++) {
			treants[i] = new Treant();
		}
		SpellDesc summonTreants = SummonSpell.create(treants);
		context.getLogic().castSpell(player.getId(), summonTreants);
		
		
	}

}

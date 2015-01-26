package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.concrete.tokens.druid.Treant;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.InstantDestroySpell;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class PoisonSeedsSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(PoisonSeedsSpell.class);
		desc.setTarget(EntityReference.NONE);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Player opponent = context.getOpponent(player);
		
		poisonSeeds(context, opponent, desc.getSourceEntity());
		poisonSeeds(context, player, desc.getSourceEntity());
	}
	
	private void poisonSeeds(GameContext context, Player player, EntityReference source) {
		int minionCount = player.getMinions().size();
		if (minionCount == 0) {
			return;
		}
		SpellDesc destroy = InstantDestroySpell.create();
		destroy.setSourceEntity(source);
		destroy.setTarget(EntityReference.FRIENDLY_MINIONS);
		context.getLogic().castSpell(player.getId(), destroy);
		
		MinionCard[] treants = new MinionCard[minionCount];
		for (int i = 0; i < minionCount; i++) {
			treants[i] = new Treant();
		}
		SpellDesc summonTreants = SummonSpell.create(treants);
		summonTreants.setSourceEntity(source);
		context.getLogic().castSpell(player.getId(), summonTreants);
		
		
	}

}

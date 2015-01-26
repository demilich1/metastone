package net.demilich.metastone.game.spells;

import java.util.List;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class DamageRandomSpell extends DamageSpell {
	
	public static SpellDesc create(int damage, int iterations) {
		SpellDesc desc = new SpellDesc(DamageRandomSpell.class);
		desc.set(SpellArg.DAMAGE, damage);
		desc.set(SpellArg.ITERATIONS, iterations);
		return desc;
	}

	@Override
	public void cast(GameContext context, Player player, SpellDesc desc, List<Entity> targets) {
		int missiles = desc.getInt(SpellArg.ITERATIONS);
		int damage = desc.getInt(SpellArg.DAMAGE);
		Entity source = context.resolveSingleTarget(desc.getSourceEntity());
		if (source.getEntityType() == EntityType.CARD && ((Card)source).getCardType() == CardType.SPELL) {
			missiles = context.getLogic().applySpellpower(player, missiles);
			missiles = context.getLogic().applyAmplify(player, missiles);
		}
		for (int i = 0; i < missiles; i++) {
			List<Actor> validTargets = SpellUtils.getValidRandomTargets(targets);
			Actor randomTarget = SpellUtils.getRandomTarget(validTargets);
			context.getLogic().damage(player, randomTarget, damage, source);
		}
	}


	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
	}

}

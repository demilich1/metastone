package net.demilich.metastone.game.spells.custom;

import java.util.concurrent.ThreadLocalRandom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.Imp;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class ImplosionSpell extends Spell {
	
	public static SpellDesc create(int minDamage, int maxDamage) {
		SpellDesc desc = new SpellDesc(ImplosionSpell.class);
		desc.set(SpellArg.MIN_DAMAGE, minDamage);
		desc.set(SpellArg.MAX_DAMAGE, maxDamage);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int minDamage = desc.getInt(SpellArg.MIN_DAMAGE);
		int maxDamage = desc.getInt(SpellArg.MAX_DAMAGE);
		int damageRange = maxDamage - minDamage;
		int damageRoll = minDamage + ThreadLocalRandom.current().nextInt(damageRange + 1);
		desc.set(SpellArg.DAMAGE, damageRoll);

		Entity source = context.resolveSingleTarget(desc.getSourceEntity());
		int effectiveDamage = context.getLogic().damage(player, (Actor) target, damageRoll, source);
		for (int i = 0; i < effectiveDamage; i++) {
			context.getLogic().summon(player.getId(), new Imp().summon());
		}
	}

}

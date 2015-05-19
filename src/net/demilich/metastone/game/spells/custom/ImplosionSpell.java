package net.demilich.metastone.game.spells.custom;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class ImplosionSpell extends Spell {
	
	public static SpellDesc create(int minDamage, int maxDamage) {
		Map<SpellArg, Object> arguments = SpellDesc.build(ImplosionSpell.class);
		arguments.put(SpellArg.MIN_DAMAGE, minDamage);
		arguments.put(SpellArg.MAX_DAMAGE, maxDamage);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int minDamage = desc.getInt(SpellArg.MIN_DAMAGE);
		int maxDamage = desc.getInt(SpellArg.MAX_DAMAGE);
		int damageRange = maxDamage - minDamage;
		int damageRoll = minDamage + ThreadLocalRandom.current().nextInt(damageRange + 1);

		int effectiveDamage = context.getLogic().damage(player, (Actor) target, damageRoll, source);
		for (int i = 0; i < effectiveDamage; i++) {
			MinionCard imp = (MinionCard) CardCatalogue.getCardByName("token_imp");
			context.getLogic().summon(player.getId(), imp.summon());
		}
	}

}

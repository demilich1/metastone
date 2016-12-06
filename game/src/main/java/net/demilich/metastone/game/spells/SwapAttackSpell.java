package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class SwapAttackSpell extends Spell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(SwapAttackSpell.class);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		if (context.getSummonReferenceStack().isEmpty()) {
			return;
		}
		Minion sourceMinion = (Minion) context.resolveSingleTarget(context.getSummonReferenceStack().peek());
		Actor targetActor = (Actor) target;
		int sourceAttack = sourceMinion.getAttack();
		int targetAttack = targetActor.getAttack();
		source.setAttribute(Attribute.ATTACK, targetAttack);
		sourceMinion.setAttribute(Attribute.ATTACK_BONUS, 0);
		sourceMinion.setAttribute(Attribute.TEMPORARY_ATTACK_BONUS, 0);
		targetActor.setAttribute(Attribute.ATTACK, sourceAttack);
		targetActor.setAttribute(Attribute.ATTACK_BONUS, 0);
		targetActor.setAttribute(Attribute.TEMPORARY_ATTACK_BONUS, 0);
	}

}

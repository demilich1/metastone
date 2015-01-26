package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class DamageSpell extends Spell {
	
	public static SpellDesc create(int damage) {
		SpellDesc desc = new SpellDesc(DamageSpell.class);
		desc.set(SpellArg.DAMAGE, damage);
		return desc;
	}
	
	public static SpellDesc create(IValueProvider damageModfier) {
		SpellDesc desc = new SpellDesc(DamageSpell.class);
		desc.set(SpellArg.VALUE_PROVIDER, damageModfier);
		return desc;
	}
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int damage = desc.getInt(SpellArg.DAMAGE);
		IValueProvider damageModifier = (IValueProvider) desc.get(SpellArg.VALUE_PROVIDER);
		if (damageModifier != null) {
			damage = damageModifier.provideValue(context, player, target);
		}
		
		Entity source = context.resolveSingleTarget(desc.getSourceEntity());
		context.getLogic().damage(player, (Actor)target, damage, source);
	}

}

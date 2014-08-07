package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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
		
		context.getLogic().damage(player, (Actor)target, damage, desc.getSource());
	}

}

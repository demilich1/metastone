package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class ChangeDurabilitySpell extends Spell {
	
	public static SpellDesc create(int durability) {
		return create(null, durability);
	}
	
	public static SpellDesc create(EntityReference target, int durability) {
		Map<SpellArg, Object> arguments = SpellDesc.build(ChangeDurabilitySpell.class);
		arguments.put(SpellArg.VALUE, durability);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int durabilityChange = desc.getInt(SpellArg.VALUE); 
		Hero hero = (Hero) target;
		if (hero.getWeapon() == null) {
			return;
		}
		context.getLogic().modifyDurability(hero.getWeapon(), durabilityChange);
	}

}

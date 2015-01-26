package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class ChangeDurabilitySpell extends Spell {
	
	public static SpellDesc create(int durability) {
		SpellDesc desc = new SpellDesc(ChangeDurabilitySpell.class);
		desc.set(SpellArg.HP_BONUS, durability);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int durabilityChange = desc.getInt(SpellArg.HP_BONUS); 
		Hero hero = (Hero) target;
		if (hero.getWeapon() == null) {
			return;
		}
		context.getLogic().modifyDurability(hero.getWeapon(), durabilityChange);
	}

}

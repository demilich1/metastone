package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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

package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class DestroyWeaponSpell extends Spell {

	public static SpellDesc create(EntityReference target) {
		return ModifyDurabilitySpell.create(target, -99);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Hero hero = (Hero) target;
		if (hero.getWeapon() == null) {
			return;
		}
		context.getLogic().destroy(hero.getWeapon());
	}

}

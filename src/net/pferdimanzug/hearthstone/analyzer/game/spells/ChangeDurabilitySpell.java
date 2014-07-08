package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;

public class ChangeDurabilitySpell extends Spell {
	
	private int durabilityChange;

	public ChangeDurabilitySpell(int durabilityChange) {
		this.durabilityChange = durabilityChange;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Hero hero = (Hero) target;
		if (hero.getWeapon() == null) {
			return;
		}
		context.getLogic().modifyDurability(hero.getWeapon(), durabilityChange);
	}

}

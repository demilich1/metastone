package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;

public class ChangeDurabilitySpell implements ISpell {
	
	private int durabilityChange;

	public ChangeDurabilitySpell(int durabilityChange) {
		this.durabilityChange = durabilityChange;
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		Hero hero = (Hero) target;
		if (hero.getWeapon() == null) {
			return;
		}
		hero.getWeapon().modifyTag(GameTag.DURABILITY, durabilityChange);
		if (hero.getWeapon().isBroken()) {
			context.getLogic().destroy(hero.getWeapon());
		}
	}

}

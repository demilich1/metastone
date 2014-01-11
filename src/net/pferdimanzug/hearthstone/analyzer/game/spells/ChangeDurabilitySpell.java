package net.pferdimanzug.hearthstone.analyzer.game.spells;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;

public class ChangeDurabilitySpell implements ISpell {
	
	private static Logger logger = LoggerFactory.getLogger(ChangeDurabilitySpell.class);
	
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
		logger.debug("Durability of weapon {} is changed by {}", hero.getWeapon(), durabilityChange);
		hero.getWeapon().modifyTag(GameTag.DURABILITY, durabilityChange);
		if (hero.getWeapon().isBroken()) {
			context.getLogic().destroy(hero.getWeapon());
		}
	}

}

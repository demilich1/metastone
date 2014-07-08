package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.HeroPower;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeHeroPowerSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(ChangeHeroPowerSpell.class);

	private final HeroPower heroPower;

	public ChangeHeroPowerSpell(HeroPower heroPower) {
		this.heroPower = heroPower;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Hero targetHero = (Hero) target;
		logger.debug("{}'s hero power was changed to {}", targetHero.getName(), heroPower);
		targetHero.setHeroPower(heroPower);
	}
}

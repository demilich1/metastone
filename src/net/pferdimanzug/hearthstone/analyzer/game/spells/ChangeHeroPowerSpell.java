package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.HeroPower;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeHeroPowerSpell extends Spell {

	public static SpellDesc create(HeroPower heroPower) {
		SpellDesc desc = new SpellDesc(ChangeHeroPowerSpell.class);
		desc.set(SpellArg.HERO_POWER, heroPower);
		return desc;
	}
	
	private static Logger logger = LoggerFactory.getLogger(ChangeHeroPowerSpell.class);

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		HeroPower heroPower = (HeroPower) desc.get(SpellArg.HERO_POWER);
		Hero targetHero = (Hero) target;
		logger.debug("{}'s hero power was changed to {}", targetHero.getName(), heroPower);
		targetHero.setHeroPower(heroPower);
	}
}

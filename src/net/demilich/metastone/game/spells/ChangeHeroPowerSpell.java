package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.heroes.powers.HeroPower;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

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

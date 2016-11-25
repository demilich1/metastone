package net.demilich.metastone.game.spells;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.heroes.powers.HeroPower;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class ChangeHeroPowerSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(ChangeHeroPowerSpell.class);

	protected void changeHeroPower(GameContext context, String newHeroPower, Hero hero) {
		HeroPower heroPower = (HeroPower) context.getCardById(newHeroPower);
		logger.debug("{}'s hero power was changed to {}", hero.getName(), heroPower);
		hero.setHeroPower(heroPower);
	}
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		String heroPowerName = (String) desc.get(SpellArg.CARD);
		changeHeroPower(context, heroPowerName, player.getHero());
	}
}

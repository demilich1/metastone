package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.heroes.powers.HeroPower;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeHeroPowerSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(ChangeHeroPowerSpell.class);
	
	public static SpellDesc create(HeroPower heroPower) {
		Map<SpellArg, Object> arguments = SpellDesc.build(ChangeHeroPowerSpell.class);
		arguments.put(SpellArg.HERO_POWER, heroPower);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		String heroPowerName = (String) desc.get(SpellArg.CARD);
		HeroPower heroPower = (HeroPower) CardCatalogue.getCardById(heroPowerName);
		Hero targetHero = (Hero) target;
		logger.debug("{}'s hero power was changed to {}", targetHero.getName(), heroPower);
		targetHero.setHeroPower(heroPower);
	}
}

package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class Malygos extends MinionCard {

	public Malygos() {
		super("Malygos", 4, 12, Rarity.LEGENDARY, HeroClass.ANY, 9);
		setDescription("Spell Damage +5");
		setRace(Race.DRAGON);
	}

	@Override
	public Minion summon() {
		Minion malygos = createMinion();
		malygos.setTag(GameTag.SPELL_POWER, +5);
		return malygos;
	}

}

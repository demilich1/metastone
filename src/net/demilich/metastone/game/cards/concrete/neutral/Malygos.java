package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class Malygos extends MinionCard {

	public Malygos() {
		super("Malygos", 4, 12, Rarity.LEGENDARY, HeroClass.ANY, 9);
		setDescription("Spell Damage +5");
		setRace(Race.DRAGON);
	}

	@Override
	public int getTypeId() {
		return 161;
	}



	@Override
	public Minion summon() {
		Minion malygos = createMinion();
		malygos.setTag(GameTag.SPELL_POWER, +5);
		return malygos;
	}
}

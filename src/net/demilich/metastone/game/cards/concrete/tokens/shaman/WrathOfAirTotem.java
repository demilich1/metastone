package net.demilich.metastone.game.cards.concrete.tokens.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.UniqueEntity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class WrathOfAirTotem extends MinionCard {

	public WrathOfAirTotem() {
		super("Wrath of Air Totem", 0, 2, Rarity.FREE, HeroClass.SHAMAN, 1);
		setDescription("Spell Damage +1");
		setRace(Race.TOTEM);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 461;
	}

	@Override
	public Minion summon() {
		Minion wrathOfAirTotem = createMinion();
		wrathOfAirTotem.setTag(GameTag.UNIQUE_ENTITY, UniqueEntity.WRATH_OF_AIR_TOTEM);
		wrathOfAirTotem.setTag(GameTag.SPELL_POWER, 1);
		return wrathOfAirTotem;
	}
}

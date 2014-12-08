package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.UniqueEntity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

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

package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.UniqueMinion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class WrathOfAirTotem extends MinionCard {

	public WrathOfAirTotem() {
		super("Wrath of Air Totem", 0, 2, Rarity.FREE, HeroClass.SHAMAN, 1);
		setCollectible(false);
		setRace(Race.TOTEM);
	}

	@Override
	public Minion summon() {
		Minion wrathOfAirTotem = createMinion();
		wrathOfAirTotem.setTag(GameTag.UNIQUE_MINION, UniqueMinion.WRATH_OF_AIR_TOTEM);
		wrathOfAirTotem.setTag(GameTag.SPELL_POWER, 1);
		return wrathOfAirTotem;
	}
}
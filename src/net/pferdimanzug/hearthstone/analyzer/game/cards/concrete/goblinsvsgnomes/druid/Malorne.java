package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.druid;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ShuffleMinionToDeckSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class Malorne extends MinionCard {

	public Malorne() {
		super("Malorne", 9, 7, Rarity.LEGENDARY, HeroClass.DRUID, 7);
		setDescription("Deathrattle: Shuffle this minion into your deck.");
		setRace(Race.BEAST);
	}

	@Override
	public Minion summon() {
		Minion malorne = createMinion();
		SpellDesc shuffleToDeck = ShuffleMinionToDeckSpell.create(new Malorne());
		malorne.addDeathrattle(shuffleToDeck);
		return malorne;
	}

}

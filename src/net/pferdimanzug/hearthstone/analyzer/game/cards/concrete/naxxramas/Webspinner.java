package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.WebspinnerSpell;

public class Webspinner extends MinionCard {

	public Webspinner() {
		super("Webspinner", 1, 1, Rarity.COMMON, HeroClass.HUNTER, 1);
		setDescription("Deathrattle: Add a random Beast card to your hand.");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 412;
	}

	@Override
	public Minion summon() {
		Minion webspinner = createMinion();
		webspinner.addDeathrattle(WebspinnerSpell.create());
		return webspinner;
	}

	
}

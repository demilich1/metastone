package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.enrage.Enrage;

public class AngryChicken extends MinionCard {

	public AngryChicken() {
		super("Angry Chicken", 1, 1, Rarity.RARE, HeroClass.ANY, 1);
		setDescription("Enrage: +5 Attack.");
		setTag(GameTag.RACE, Race.BEAST);
	}

	@Override
	public Minion summon() {
		Minion angryChicken = createMinion();
		angryChicken.setTag(GameTag.ENRAGE_SPELL, new Enrage(5));
		return angryChicken;
	}

}

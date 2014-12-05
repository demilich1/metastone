package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.enrage.Enrage;

public class Warbot extends MinionCard {

	public Warbot() {
		super("Warbot", 1, 3, Rarity.COMMON, HeroClass.WARRIOR, 1);
		setDescription("Enrage: +1 Attack.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 610;
	}



	@Override
	public Minion summon() {
		Minion warbot = createMinion();
		warbot.setTag(GameTag.ENRAGE_SPELL, Enrage.create(+1));
		return warbot;
	}
}

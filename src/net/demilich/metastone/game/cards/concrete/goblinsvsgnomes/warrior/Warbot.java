package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warrior;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.enrage.Enrage;

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

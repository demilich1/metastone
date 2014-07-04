package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.aura.BuffAura;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class TimberWolf extends MinionCard {
	
	public TimberWolf() {
		super("Timber Wolf", 1, 1, Rarity.FREE, HeroClass.HUNTER, 1);
		setDescription("Your other Beasts have +1 Attack.");
		setRace(Race.BEAST);
	}

	@Override
	public Minion summon() {
		Minion timberWolf = createMinion();
		Aura timberWolfAura = new BuffAura(1, 0, EntityReference.FRIENDLY_MINIONS, Race.BEAST);
		timberWolf.setSpellTrigger(timberWolfAura);
		return timberWolf;
	}

}

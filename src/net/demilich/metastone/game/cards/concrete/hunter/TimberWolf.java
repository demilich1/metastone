package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.aura.Aura;
import net.demilich.metastone.game.spells.aura.BuffAura;
import net.demilich.metastone.game.targeting.EntityReference;

public class TimberWolf extends MinionCard {
	
	public TimberWolf() {
		super("Timber Wolf", 1, 1, Rarity.FREE, HeroClass.HUNTER, 1);
		setDescription("Your other Beasts have +1 Attack.");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 47;
	}

	@Override
	public Minion summon() {
		Minion timberWolf = createMinion();
		Aura timberWolfAura = new BuffAura(1, 0, EntityReference.FRIENDLY_MINIONS, Race.BEAST);
		timberWolf.setSpellTrigger(timberWolfAura);
		return timberWolf;
	}
}

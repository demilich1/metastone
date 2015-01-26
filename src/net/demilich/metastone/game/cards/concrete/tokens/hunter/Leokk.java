package net.demilich.metastone.game.cards.concrete.tokens.hunter;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.aura.Aura;
import net.demilich.metastone.game.spells.aura.BuffAura;
import net.demilich.metastone.game.targeting.EntityReference;

public class Leokk extends MinionCard {

	public Leokk() {
		super("Leokk", 2, 4, Rarity.FREE, HeroClass.HUNTER, 3);
		setDescription("Other friendly minions have +1 Attack.");
		setRace(Race.BEAST);
		
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 423;
	}

	@Override
	public Minion summon() {
		Minion leokk = createMinion();
		Aura leokkAura = new BuffAura(1, 0, EntityReference.FRIENDLY_MINIONS);
		leokk.setSpellTrigger(leokkAura);
		return leokk;
	}
}

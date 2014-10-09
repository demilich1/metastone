package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.aura.BuffAura;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Leokk extends MinionCard {

	public Leokk() {
		super("Leokk", 2, 4, Rarity.FREE, HeroClass.HUNTER, 3);
		setDescription("Other friendly minions have +1 Attack.");
		setRace(Race.BEAST);
		
		setCollectible(false);
	}

	@Override
	public Minion summon() {
		Minion leokk = createMinion();
		Aura leokkAura = new BuffAura(1, 0, EntityReference.FRIENDLY_MINIONS);
		leokk.setSpellTrigger(leokkAura);
		return leokk;
	}

	@Override
	public int getTypeId() {
		return 423;
	}
}

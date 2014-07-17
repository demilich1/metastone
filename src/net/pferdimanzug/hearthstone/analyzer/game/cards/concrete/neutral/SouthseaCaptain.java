package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.aura.BuffAura;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class SouthseaCaptain extends MinionCard {

	public SouthseaCaptain() {
		super("Southsea Captain", 3, 3, Rarity.EPIC, HeroClass.ANY, 3);
		setDescription("Your other Pirates have +1/+1.");
		setRace(Race.PIRATE);
	}

	@Override
	public int getTypeId() {
		return 201;
	}



	@Override
	public Minion summon() {
		Minion southseaCaptain = createMinion();
		Aura buffAura = new BuffAura(+1, +1, EntityReference.FRIENDLY_MINIONS, Race.PIRATE);
		southseaCaptain.setSpellTrigger(buffAura);
		return southseaCaptain;
	}
}

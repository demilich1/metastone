package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.aura.Aura;
import net.demilich.metastone.game.spells.aura.BuffAura;
import net.demilich.metastone.game.targeting.EntityReference;

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

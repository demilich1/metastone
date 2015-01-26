package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.aura.Aura;
import net.demilich.metastone.game.spells.aura.BuffAura;
import net.demilich.metastone.game.targeting.EntityReference;

public class StormwindChampion extends MinionCard {

	public StormwindChampion() {
		super("Stormwind Champion", 6, 6, Rarity.FREE, HeroClass.ANY, 7);
		setDescription("Your other minions have +1/+1.");
	}

	@Override
	public int getTypeId() {
		return 208;
	}



	@Override
	public Minion summon() {
		Minion stormwindChampion = createMinion();
		Aura stormwindChampionAura = new BuffAura(1, 1, EntityReference.FRIENDLY_MINIONS);
		stormwindChampion.setSpellTrigger(stormwindChampionAura);
		return stormwindChampion;
	}
}

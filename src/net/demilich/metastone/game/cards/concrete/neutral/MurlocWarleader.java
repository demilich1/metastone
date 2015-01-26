package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.aura.Aura;
import net.demilich.metastone.game.spells.aura.BuffAura;
import net.demilich.metastone.game.targeting.EntityReference;

public class MurlocWarleader extends MinionCard {

	public MurlocWarleader() {
		super("Murloc Warleader", 3, 3, Rarity.EPIC, HeroClass.ANY, 3);
		setDescription("ALL other Murlocs have +2/+1.");
		setRace(Race.MURLOC);
	}

	@Override
	public int getTypeId() {
		return 173;
	}



	@Override
	public Minion summon() {
		Minion murlocWarleader = createMinion();
		Aura warleaderAura = new BuffAura(+2, +1, EntityReference.ALL_MINIONS, Race.MURLOC);
		murlocWarleader.setSpellTrigger(warleaderAura);
		return murlocWarleader;
	}
}

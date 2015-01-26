package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.aura.Aura;
import net.demilich.metastone.game.spells.aura.BuffAura;
import net.demilich.metastone.game.targeting.EntityReference;

public class FlametongueTotem extends MinionCard {

	public FlametongueTotem() {
		super("Flametongue Totem", 0, 3, Rarity.FREE, HeroClass.SHAMAN, 2);
		setDescription("Adjacent minions have +2 Attack.");
	}

	@Override
	public int getTypeId() {
		return 320;
	}



	@Override
	public Minion summon() {
		Minion flametongueTotem = createMinion();
		flametongueTotem.setRace(Race.TOTEM);
		Aura flametongueTotemAura = new BuffAura(2, 0, EntityReference.ADJACENT_MINIONS);
		flametongueTotem.setSpellTrigger(flametongueTotemAura);
		return flametongueTotem;
	}
}

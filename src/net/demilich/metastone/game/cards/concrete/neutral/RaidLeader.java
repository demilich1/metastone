package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.aura.Aura;
import net.demilich.metastone.game.spells.aura.BuffAura;
import net.demilich.metastone.game.targeting.EntityReference;

public class RaidLeader extends MinionCard {
	
	public RaidLeader() {
		super("Raid Leader", 2, 2, Rarity.FREE, HeroClass.ANY, 3);
		setDescription("Your other minions have +1 Attack.");
	}
	
	@Override
	public int getTypeId() {
		return 187;
	}



	@Override
	public Minion summon() {
		Minion raidLeader = createMinion();
		Aura raidLeaderAura = new BuffAura(1, 0, EntityReference.FRIENDLY_MINIONS);
		raidLeader.setSpellTrigger(raidLeaderAura);
		return raidLeader;
	}
}

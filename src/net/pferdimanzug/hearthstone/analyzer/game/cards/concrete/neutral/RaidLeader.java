package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.aura.BuffAura;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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

package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.aura.AuraSpellBuff;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class RaidLeader extends MinionCard {
	
	private class RaidLeaderAura extends Aura {

		public RaidLeaderAura() {
			super(new AuraSpellBuff(1), new AuraSpellBuff(-1), EntityReference.FRIENDLY_MINIONS);
		}
		
		@Override
		protected boolean affects(GameContext context, Actor target) {
			if (!super.affects(context, target)) {
				
				return false;
			}
			return target.getOwner() == getOwner();
		}
	}

	public RaidLeader() {
		super("Raid Leader", 2, 2, Rarity.FREE, HeroClass.ANY, 3);
		setDescription("Your other minions have +1 Attack.");
	}
	
	@Override
	public Minion summon() {
		Minion raidLeader = createMinion();
		raidLeader.setSpellTrigger(new RaidLeaderAura());
		return raidLeader;
	}

}

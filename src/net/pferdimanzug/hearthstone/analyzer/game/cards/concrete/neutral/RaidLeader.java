package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class RaidLeader extends MinionCard {
	
	private class RaidLeaderAura extends Aura {

		public RaidLeaderAura(Actor source) {
			super(source);
		}

		@Override
		public boolean affects(Actor entity) {
			if (entity == getSource()) {
				return false;
			} else if (entity.getOwner() != getSource().getOwner()) {
				return false;
			}
			return entity.getEntityType() == EntityType.MINION;
		}

		@Override
		protected void onApply(Actor entity) {
			entity.modifyTag(GameTag.ATTACK_BONUS, +1);
		}

		@Override
		protected void onRemove(Actor entity) {
			entity.modifyTag(GameTag.ATTACK_BONUS, -1);
		}
		
	}

	public RaidLeader() {
		super("Raid Leader", 2, 2, Rarity.FREE, HeroClass.ANY, 3);
	}
	
	@Override
	public Minion summon() {
		Minion raidLeader = createMinion();
		raidLeader.setAura(new RaidLeaderAura(raidLeader));
		return raidLeader;
	}

}

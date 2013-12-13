package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class RaidLeader extends MinionCard {
	
	public RaidLeader() {
		super("Raid Leader", Rarity.FREE, HeroClass.ANY, 3);
	}

	@Override
	public Minion summon() {
		Minion raidLeader = createMinion(2, 2);
		raidLeader.setAura(new RaidLeaderAura(raidLeader));
		return raidLeader;
	}
	
	private class RaidLeaderAura extends Aura {

		public RaidLeaderAura(Entity source) {
			super(source);
		}

		@Override
		public boolean affects(Entity entity) {
			if (entity == getSource()) {
				return false;
			} else if (entity.getOwner() != getSource().getOwner()) {
				return false;
			}
			return entity.getEntityType() == EntityType.MINION;
		}

		@Override
		protected void onApply(Entity entity) {
			entity.modifyTag(GameTag.ATTACK_BONUS, +1);
		}

		@Override
		protected void onRemove(Entity entity) {
			entity.modifyTag(GameTag.ATTACK_BONUS, -1);
		}
		
	}

}

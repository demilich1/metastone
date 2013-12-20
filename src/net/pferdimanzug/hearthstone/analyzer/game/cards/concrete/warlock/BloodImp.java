package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class BloodImp extends MinionCard {

	public BloodImp() {
		super("Blood Imp", Rarity.COMMON, HeroClass.WARLOCK, 1);
	}

	@Override
	public Minion summon() {
		Minion bloodImp = createMinion(1, 1, Race.DEMON, GameTag.STEALTHED);
		bloodImp.setAura(new BloodImpAura(bloodImp));
		return bloodImp;
	} 
	
	private class BloodImpAura extends Aura {

		public BloodImpAura(Entity source) {
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
			entity.modifyHpBonus(+1);
		}

		@Override
		protected void onRemove(Entity entity) {
			entity.modifyHpBonus(-1);
		}
		
	}

}

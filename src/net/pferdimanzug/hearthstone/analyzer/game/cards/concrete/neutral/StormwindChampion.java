package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class StormwindChampion extends MinionCard {

	private class StormwindChampionAura extends Aura {

		public StormwindChampionAura(Actor source) {
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
			entity.modifyHpBonus(+1);
		}

		@Override
		protected void onRemove(Actor entity) {
			entity.modifyTag(GameTag.ATTACK_BONUS, -1);
			entity.modifyHpBonus(-1);
		}

	}

	public StormwindChampion() {
		super("Stormwind Champion", 6, 6, Rarity.FREE, HeroClass.ANY, 7);
	}

	@Override
	public Minion summon() {
		Minion stormwindChampion = createMinion();
		stormwindChampion.setAura(new StormwindChampionAura(stormwindChampion));
		return stormwindChampion;
	}

}

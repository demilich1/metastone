package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class StormwindChampion extends MinionCard {

	public StormwindChampion() {
		super("Stormwind Champion", Rarity.FREE, HeroClass.ANY, 7);
	}

	@Override
	public Minion summon() {
		Minion stormwindChampion = createMinion(6, 6);
		stormwindChampion.setAura(new StormwindChampionAura(stormwindChampion));
		return stormwindChampion;
	}

	private class StormwindChampionAura extends Aura {

		public StormwindChampionAura(Entity source) {
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
			entity.modifyTag(GameTag.HP_BONUS, +1);
		}

		@Override
		protected void onRemove(Entity entity) {
			entity.modifyTag(GameTag.ATTACK_BONUS, -1);
			entity.modifyTag(GameTag.HP_BONUS, -1);
		}

	}

}

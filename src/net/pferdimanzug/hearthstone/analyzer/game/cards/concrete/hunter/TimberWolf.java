package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Race;

public class TimberWolf extends MinionCard {

	public TimberWolf() {
		super("Timber Wolf", Rarity.FREE, HeroClass.HUNTER, 1);
	}

	@Override
	public Minion summon() {
		Minion timberWolf = createMinion(1, 1, Race.BEAST);
		timberWolf.setAura(new TimberWolfAura(timberWolf));
		return timberWolf;
	}

	private class TimberWolfAura extends Aura {

		public TimberWolfAura(Entity source) {
			super(source);
		}

		@Override
		public boolean affects(Entity entity) {
			if (entity == getSource()) {
				return false;
			} else if (entity.getOwner() != getSource().getOwner()) {
				return false;
			}
			if (entity.getEntityType() != EntityType.MINION) {
				return false;
			}
			Minion minion = (Minion) entity;
			return minion.getRace() == Race.BEAST;
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

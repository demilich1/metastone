package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Race;

public class TundraRhino extends MinionCard {

	public TundraRhino() {
		super("Tundra Rhino", Rarity.FREE, HeroClass.HUNTER, 5);
	}

	@Override
	public Minion summon() {
		Minion tundraRhino = createMinion(2, 5, Race.BEAST);
		tundraRhino.setAura(new TundraRhinoAura(tundraRhino));
		return tundraRhino;
	}
	
	private class TundraRhinoAura extends Aura {

		public TundraRhinoAura(Entity source) {
			super(source);
		}

		@Override
		public boolean affects(Entity entity) {
			if (entity.getOwner() != getSource().getOwner()) {
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
			entity.setTag(GameTag.CHARGE);
		}

		@Override
		protected void onRemove(Entity entity) {
			entity.removeTag(GameTag.CHARGE);
		}
		
	}

}

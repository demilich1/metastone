package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class TundraRhino extends MinionCard {

	private class TundraRhinoAura extends Aura {

		public TundraRhinoAura(Actor source) {
			super(source);
		}

		@Override
		public boolean affects(Actor entity) {
			if (entity.getOwner() != getSource().getOwner()) {
				return false;
			}
			if (entity.getEntityType() != EntityType.MINION) {
				return false;
			}
			return entity.getRace() == Race.BEAST;
		}

		@Override
		protected void onApply(Actor entity) {
			entity.setTag(GameTag.CHARGE);
		}

		@Override
		protected void onRemove(Actor entity) {
			entity.removeTag(GameTag.CHARGE);
		}
		
	}

	public TundraRhino() {
		super("Tundra Rhino", 2, 5, Rarity.FREE, HeroClass.HUNTER, 5);
		setDescription("Your Beasts have Charge.");
	}
	
	@Override
	public Minion summon() {
		Minion tundraRhino = createMinion(Race.BEAST);
		tundraRhino.setAura(new TundraRhinoAura(tundraRhino));
		return tundraRhino;
	}

}

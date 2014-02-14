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

public class TimberWolf extends MinionCard {

	private class TimberWolfAura extends Aura {

		public TimberWolfAura(Actor source) {
			super(source);
		}

		@Override
		public boolean affects(Actor actor) {
			if (actor == getSource()) {
				return false;
			} else if (actor.getOwner() != getSource().getOwner()) {
				return false;
			}
			if (actor.getEntityType() != EntityType.MINION) {
				return false;
			}
			Minion minion = (Minion) actor;
			return minion.getRace() == Race.BEAST;
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

	public TimberWolf() {
		super("Timber Wolf", 1, 1, Rarity.FREE, HeroClass.HUNTER, 1);
		setDescription("Your other Beasts have +1 Attack.");
		setTag(GameTag.RACE, Race.BEAST);
	}

	@Override
	public Minion summon() {
		Minion timberWolf = createMinion();
		timberWolf.setAura(new TimberWolfAura(timberWolf));
		return timberWolf;
	}

}

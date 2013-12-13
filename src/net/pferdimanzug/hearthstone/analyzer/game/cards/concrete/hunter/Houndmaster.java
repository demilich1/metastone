package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;

public class Houndmaster extends MinionCard {

	public Houndmaster() {
		super("Houndmaster", Rarity.FREE, HeroClass.HUNTER, 4);
	}

	@Override
	public Minion summon() {
		Minion houndmaster = createMinion(4, 3);
		houndmaster.setTag(GameTag.BATTLECRY, new BattlecryHoundmaster());
		return houndmaster;
	}

	private class BattlecryHoundmaster extends Battlecry {

		public BattlecryHoundmaster() {
			super(new HoundmasterBuff());
			setTargetRequirement(TargetRequirement.FRIENDLY_MINIONS);
			setEffectHint(EffectHint.POSITIVE);
		}

		@Override
		public boolean canBeExecutedOn(Entity entity) {
			if (entity.getEntityType() != EntityType.MINION) {
				return false;
			}
			Minion minion = (Minion) entity;
			return minion.getRace() == Race.BEAST;
		}
	}

	private class HoundmasterBuff extends BuffSpell {

		public HoundmasterBuff() {
			super(2, 2);
		}

		@Override
		public void cast(GameContext context, Player player, Entity target) {
			super.cast(context, player, target);
			target.setTag(GameTag.TAUNT);
		}

	}

}

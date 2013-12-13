package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Race;

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
			setTargetRequirement(TargetRequirement.OWN_MINIONS);
			setEffectHint(EffectHint.POSITIVE);
		}

		@Override
		public void execute(GameContext context, Player player) {
			Entity target = getTarget();
			target.modifyTag(GameTag.ATTACK_BONUS, +2);
			target.modifyTag(GameTag.HP_BONUS, +2);
			target.setTag(GameTag.TAUNT);
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
}

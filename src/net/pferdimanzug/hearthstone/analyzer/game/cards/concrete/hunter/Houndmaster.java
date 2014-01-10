package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;

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
			super(new MetaSpell(new BuffSpell(2, 2), new ApplyTagSpell(GameTag.TAUNT)));
			setTargetRequirement(TargetSelection.FRIENDLY_MINIONS);
		}

		@Override
		public boolean canBeExecutedOn(Entity entity) {
			if (entity.getEntityType() != EntityType.MINION) {
				return false;
			}
			return entity.getRace() == Race.BEAST;
		}
	}
}

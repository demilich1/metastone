package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AreaSpell;

public class TotemicMight extends SpellCard {

	public TotemicMight() {
		super("Totemic Might", Rarity.FREE, HeroClass.SHAMAN, 0);
		setSpell(new TotemicMightSpell());
		setTargetRequirement(TargetSelection.NONE);
	}

	private class TotemicMightSpell extends AreaSpell {

		public TotemicMightSpell() {
			super(TargetSelection.FRIENDLY_MINIONS);
		}

		@Override
		protected void forEachTarget(GameContext context, Player player, Entity entity) {
			Entity minion = (Entity) entity;
			if (minion.getRace() != Race.TOTEM) {
				return;
			}
			minion.modifyHpBonus(+2);
		}

	}

}

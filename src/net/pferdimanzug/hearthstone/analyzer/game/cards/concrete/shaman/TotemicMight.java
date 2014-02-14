package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class TotemicMight extends SpellCard {

	private class TotemicMightSpell extends BuffSpell {

		public TotemicMightSpell() {
			super(0, +2);
		}

		@Override
		protected void onCast(GameContext context, Player player, Actor actor) {
			Minion minion = (Minion) actor;
			if (minion.getRace() != Race.TOTEM) {
				return;
			}
			super.onCast(context, player, actor);
		}

	}

	public TotemicMight() {
		super("Totemic Might", Rarity.FREE, HeroClass.SHAMAN, 0);
		setDescription("Give your Totems +2 Health.");
		setSpell(new TotemicMightSpell());
		setTargetRequirement(TargetSelection.NONE);
		setPredefinedTarget(EntityReference.FRIENDLY_MINIONS);
	}

	@Override
	public boolean canBeCast(GameContext context, Player player) {
		return player.getMinions().size() > 0;
	}
	
	

}

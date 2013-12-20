package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetDamageSpell;

public class FrostShock extends SpellCard {

	public FrostShock() {
		super("Frost Shock", Rarity.FREE, HeroClass.ANY, 1);
		setTargetRequirement(TargetSelection.ENEMY_CHARACTERS);
		setSpell(new FrostShockSpell(1));
	}

	private class FrostShockSpell extends SingleTargetDamageSpell {

		public FrostShockSpell(int damage) {
			super(damage);
		}

		@Override
		public void cast(GameContext context, Player player, Entity target) {
			super.cast(context, player, target);
			target.setTag(GameTag.FROZEN);
		}

	}

}

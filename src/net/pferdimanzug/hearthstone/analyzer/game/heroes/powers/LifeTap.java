package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ISpell;

public class LifeTap extends HeroPower {

	private class LifeTapSpell implements ISpell {

		@Override
		public void cast(GameContext context, Player player, Entity target) {
			context.getLogic().damage(player.getHero(), DAMAGE);
			context.getLogic().drawCard(player);
		}
		
	}

	public static final int DAMAGE = 2;
	
	public LifeTap() {
		super("Life Tap");
		setTargetRequirement(TargetSelection.NONE);
		setSpell(new LifeTapSpell());
	}

}

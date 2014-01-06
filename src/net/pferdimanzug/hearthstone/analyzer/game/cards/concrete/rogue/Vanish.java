package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AreaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ISpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReturnMinionToHandSpell;

public class Vanish extends SpellCard {

	public Vanish() {
		super("Vanish", Rarity.FREE, HeroClass.ROGUE, 6);
		setSpell(new VanishSpell());
		setTargetRequirement(TargetSelection.NONE);
	}
	
	private class VanishSpell extends AreaSpell {
		
		//TODO: use a internal helper spell to avoid duplicating
		// the 'return to hand' logic. Maybe spell cards should specify a spell
		// and something like a SpellTarget (similar to AreaSpell)?
		private final ISpell returnSpell;

		public VanishSpell() {
			super(TargetSelection.ENEMY_MINIONS);
			returnSpell = new ReturnMinionToHandSpell();
		}
		
		@Override
		protected void forEachTarget(GameContext context, Player player, Entity entity) {
			returnSpell.cast(context, player, entity);
		}
		
	}

}

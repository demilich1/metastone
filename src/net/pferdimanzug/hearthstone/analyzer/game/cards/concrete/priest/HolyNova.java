package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AreaSpell;

public class HolyNova extends SpellCard {
	
	private static final int DAMAGE = 2;
	private static final int HEALING = 2;

	public HolyNova() {
		super("Holy Nova", Rarity.FREE, HeroClass.PRIEST, 5);
		setSpell(new HolyNovaSpell());
		setTargetRequirement(TargetSelection.NONE);
	}
	
	private class HolyNovaSpell extends AreaSpell {
		
		public HolyNovaSpell() {
			super(TargetSelection.ANY);
		}

		@Override
		protected void forEachTarget(GameContext context, Player player, Entity entity) {
			if (entity.getOwner() == player) {
				context.getLogic().heal(entity, HEALING);
			} else {
				context.getLogic().damage(entity, DAMAGE);
			}
			
		}

				
		
		
	}

}

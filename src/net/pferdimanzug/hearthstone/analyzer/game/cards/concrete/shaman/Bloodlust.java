package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.events.TurnEndEventlistener;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ISpell;

public class Bloodlust extends SpellCard {

	public Bloodlust() {
		super("Bloodlust", Rarity.FREE, HeroClass.SHAMAN, 5);
		setSpell(new BloodlustSpell());
		setTargetRequirement(TargetSelection.NONE);
	}
	
	private class BloodlustSpell implements ISpell {
		
		private static final int ATTACK_BONUS = 3;

		@Override
		public void cast(GameContext context, Player player, Entity target) {
			for (Minion minion : player.getMinions()) {
				minion.modifyTag(GameTag.ATTACK_BONUS, +ATTACK_BONUS);
				context.getEventManager().registerGameEventListener(new TurnEndEventlistener(new EndBloodlustSpell(), target));
			}
		}
		private class EndBloodlustSpell implements ISpell {

			@Override
			public void cast(GameContext context, Player player, Entity target) {
				target.modifyTag(GameTag.ATTACK_BONUS, -ATTACK_BONUS);
			}
			
		}
		
	}

}

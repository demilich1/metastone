package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.events.TurnEndEventlistener;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;

public class AbusiveSergeant extends MinionCard {
	
	private class AbusiveSergeantSpell extends BuffSpell {
		
		public AbusiveSergeantSpell() {
			super(ATTACK_BONUS, 0);
		}

		@Override
		public void cast(GameContext context, Player player, Entity target) {
			super.cast(context, player, target);
			
			context.getEventManager().registerGameEventListener(new TurnEndEventlistener(new BuffSpell(-ATTACK_BONUS, 0), target));
		}
	}

	private static final int ATTACK_BONUS = 2;

	public AbusiveSergeant() {
		super("Abusive Sergeant", Rarity.COMMON, HeroClass.ANY, 1);
	}
	
	@Override
	public Minion summon() {
		Minion abusiveSergeant = createMinion(2, 1);
		Battlecry battlecryAbusive = Battlecry.createBattlecry(new AbusiveSergeantSpell(), TargetSelection.FRIENDLY_MINIONS);
		abusiveSergeant.setTag(GameTag.BATTLECRY, battlecryAbusive);
		return abusiveSergeant;
	}
	

}

package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class SouthseaDeckhand extends MinionCard {

	public SouthseaDeckhand() {
		super("Southsea Deckhand", Rarity.COMMON, HeroClass.ANY, 1);
	}

	@Override
	public Minion summon() {
		Battlecry battlecry = Battlecry.createBattlecry(new ChargeWhileWeaponEquipped(), TargetSelection.SELF);
		Minion southseaDeckhand = createMinion(2, 1, Race.PIRATE);
		southseaDeckhand.setTag(GameTag.BATTLECRY, battlecry);
		return southseaDeckhand;
	}
	
	private class ChargeWhileWeaponEquipped extends ApplyTagSpell {
		
		public ChargeWhileWeaponEquipped() {
			super(GameTag.CHARGE);
		}

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			if (player.getHero().getWeapon() == null) {
				return;
			}
			super.onCast(context, player, target);
		}
		
		
		
	}

}

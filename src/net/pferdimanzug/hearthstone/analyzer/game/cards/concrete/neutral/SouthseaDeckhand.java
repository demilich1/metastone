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
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class SouthseaDeckhand extends MinionCard {

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

	public SouthseaDeckhand() {
		super("Southsea Deckhand", 2, 1, Rarity.COMMON, HeroClass.ANY, 1);
		setDescription("Has Charge while you have a weapon equipped.");
		setRace(Race.PIRATE);
	}

	@Override
	public Minion summon() {
		Spell chargeWhileWeaponEquipped = new ChargeWhileWeaponEquipped();
		chargeWhileWeaponEquipped.setTarget(EntityReference.SELF);
		Battlecry battlecry = Battlecry.createBattlecry(chargeWhileWeaponEquipped);
		Minion southseaDeckhand = createMinion();
		southseaDeckhand.setBattlecry(battlecry);
		return southseaDeckhand;
	}

}

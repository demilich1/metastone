package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Demonfire extends SpellCard {

	private class DemonfireSpell extends Spell {

		private final Spell damageSpell;
		private final Spell buffSpell;

		public DemonfireSpell() {
			damageSpell = new DamageSpell(2);
			damageSpell.setApplySpellpower(true);
			buffSpell = new BuffSpell(2, 2);
		}

		private boolean isFriendlyMinion(Player player, Entity actor) {
			Minion minion = (Minion) actor;
			return minion.getOwner() == player.getId() && minion.getEntityType() == EntityType.MINION
					&& minion.getRace() == Race.DEMON;
		}

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Spell spellToCast =isFriendlyMinion(player, target) ? buffSpell : damageSpell;
			spellToCast.setTarget(target.getReference());
			context.getLogic().castSpell(player.getId(), spellToCast);
		}

	}

	public Demonfire() {
		super("Demonfire", Rarity.COMMON, HeroClass.WARLOCK, 2);
		setDescription("Deal $2 damage to a minion. If it’s a friendly Demon, give it +2/+2 instead.");
		setSpell(new DemonfireSpell());
		setTargetRequirement(TargetSelection.MINIONS);
	}

}

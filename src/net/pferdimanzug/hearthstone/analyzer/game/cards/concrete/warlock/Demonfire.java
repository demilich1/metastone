package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Demonfire extends SpellCard {

	public Demonfire(String name, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, rarity, classRestriction, manaCost);
		setSpell(new DemonfireSpell());
		setTargetRequirement(TargetSelection.MINIONS);
	}

	private class DemonfireSpell extends Spell {

		private final Spell damageSpell;
		private final Spell buffSpell;

		public DemonfireSpell() {
			damageSpell = new DamageSpell(2);
			damageSpell.setApplySpellpower(true);
			buffSpell = new BuffSpell(2, 2);
		}

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Spell spellToCast =isFriendlyMinion(player, target) ? buffSpell : damageSpell;
			spellToCast.setTarget(target.getReference());
			context.getLogic().castSpell(player.getId(), spellToCast);
		}

		private boolean isFriendlyMinion(Player player, Entity entity) {
			return entity.getOwner() == player.getId() && entity.getEntityType() == EntityType.MINION
					&& entity.getRace() == Race.DEMON;
		}

	}

}

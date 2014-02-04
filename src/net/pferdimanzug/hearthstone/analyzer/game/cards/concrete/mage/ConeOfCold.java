package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ConeOfCold extends SpellCard {

	public ConeOfCold() {
		super("ConeOfCold", Rarity.COMMON, HeroClass.MAGE, 4);
		setSpell(new ConeOfColdSpell());
		setTargetRequirement(TargetSelection.MINIONS);
	}
	
	private class ConeOfColdSpell extends Spell {
		
		private final Spell damage = new DamageSpell(1);
		private final Spell freeze = new ApplyTagSpell(GameTag.FROZEN);
		
		public ConeOfColdSpell() {
			damage.setApplySpellpower(true);
		}

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Minion targetMinion = (Minion) target;
			List<Minion> affected = context.getAdjacentMinions(player, targetMinion);
			affected.add(targetMinion);
			
			for (Minion minion : affected) {
				damage.setTarget(minion.getReference());
				context.getLogic().castSpell(player.getId(), damage);
				freeze.setTarget(minion.getReference());
				context.getLogic().castSpell(player.getId(), freeze);
			}
		}
		
	}

}

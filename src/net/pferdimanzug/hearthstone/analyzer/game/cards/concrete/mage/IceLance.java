package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;


public class IceLance extends SpellCard {

	public IceLance() {
		super("Ice Lance", Rarity.COMMON, HeroClass.MAGE, 1);
		setSpell(new IceLanceSpell());
		setTargetRequirement(TargetSelection.ANY);
	}
	
	private class IceLanceSpell extends Spell {
		
		private final Spell freezeSpell;
		private final Spell damageSpell;
		
		public IceLanceSpell() {
			freezeSpell = new ApplyTagSpell(GameTag.FROZEN);
			damageSpell = new DamageSpell(4);
			damageSpell.setApplySpellpower(true);
		}

		@Override
		protected void onCast(GameContext context, Player player, Actor target) {
			Spell spell = target.hasTag(GameTag.FROZEN) ? damageSpell : freezeSpell;
			spell.setTarget(target.getReference());
			context.getLogic().castSpell(player.getId(), spell);
		}
		
	}

}

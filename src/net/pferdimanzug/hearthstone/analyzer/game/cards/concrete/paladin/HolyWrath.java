package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class HolyWrath extends SpellCard {

	private class HolyWrathSpell extends DamageSpell {

		public HolyWrathSpell() {
			super(0);
		}

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Card drawedCard = context.getLogic().drawCard(player.getId());
			if (drawedCard == null) {
				return;
			}
			setDamage(drawedCard.getBaseManaCost());
			super.onCast(context, player, target);
		}

	}

	public HolyWrath() {
		super("Holy Wrath", Rarity.RARE, HeroClass.PALADIN, 5);
		setDescription("Draw a card and deal damage equal to its cost.");
		
		setSpell(new HolyWrathSpell());
		setTargetRequirement(TargetSelection.ANY);
	}

}

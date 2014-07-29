package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class FarSight extends SpellCard {

	public FarSight() {
		super("Far Sight", Rarity.EPIC, HeroClass.SHAMAN, 3);
		setDescription("Draw a card. That card costs (3) less.");
		
		setSpell(new FarSightSpell());
		setTargetRequirement(TargetSelection.NONE);
		
	}

	@Override
	public int getTypeId() {
		return 317;
	}



	private class FarSightSpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Card drawnCard = context.getLogic().drawCard(player.getId());
			if (drawnCard == null) {
				return;
			}
			drawnCard.setTag(GameTag.MANA_COST_MODIFIER, -3);
		}

	}
}

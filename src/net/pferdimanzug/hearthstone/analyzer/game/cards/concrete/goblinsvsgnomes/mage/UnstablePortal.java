package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCatalogue;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardAndDoSomethingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class UnstablePortal extends SpellCard {

	public UnstablePortal() {
		super("Unstable Portal", Rarity.RARE, HeroClass.MAGE, 2);
		setDescription("Add a random minion to your hand. It costs (3) less.");

		SpellDesc desc = DrawCardAndDoSomethingSpell.create(
				(context, player) -> CardCatalogue.query(CardType.MINION).getRandom(), 
				(context, player, card) ->  {
					card.setTag(GameTag.MANA_COST_MODIFIER, -3);
					context.getLogic().receiveCard(player.getId(), card);
				});
		setSpell(desc);
		setTargetRequirement(TargetSelection.NONE);
	}

}

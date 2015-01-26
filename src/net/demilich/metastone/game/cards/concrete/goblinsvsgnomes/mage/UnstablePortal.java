package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.mage;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DrawCardAndDoSomethingSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

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



	@Override
	public int getTypeId() {
		return 498;
	}
}

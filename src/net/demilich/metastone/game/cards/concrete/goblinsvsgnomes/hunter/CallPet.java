package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.hunter;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DrawCardAndDoSomethingSpell;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class CallPet extends SpellCard {

	public CallPet() {
		super("Call Pet", Rarity.RARE, HeroClass.HUNTER, 2);
		setDescription("Draw a card, if it's a Beast it costs (4) less.");

		SpellDesc desc = DrawCardAndDoSomethingSpell.create(SpellUtils::drawFromDeck, this::reduceBeastManaCost);
		setSpell(desc);
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 484;
	}



	private void reduceBeastManaCost(GameContext context, Player player, Card card) {
		if (card.getTag(GameTag.RACE) == Race.BEAST) {
			card.setTag(GameTag.MANA_COST_MODIFIER, -4);
		}
	}
}

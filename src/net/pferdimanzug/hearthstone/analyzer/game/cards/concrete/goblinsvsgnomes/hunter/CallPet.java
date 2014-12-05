package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardAndDoSomethingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SpellUtils;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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

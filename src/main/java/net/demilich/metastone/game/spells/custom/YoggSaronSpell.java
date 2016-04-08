package net.demilich.metastone.game.spells.custom;

import java.util.List;
import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.ChooseOneCard;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.statistics.Statistic;
import net.demilich.metastone.game.targeting.TargetSelection;

public class YoggSaronSpell extends Spell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(YoggSaronSpell.class);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		// This spell is crazy.
		CardCollection spellCards = CardCatalogue.query(context.getDeckFormat(), CardType.SPELL);
		
		// Straight up insane.
		for (int i = 0; i <= (int) player.getStatistics().getLong(Statistic.SPELLS_CAST); i++) {
				Card randomCard = spellCards.getRandom();
				if (randomCard instanceof ChooseOneCard) {
					ChooseOneCard chooseOneCard = (ChooseOneCard) randomCard;
					Card[] cards = chooseOneCard.getChoiceCards();
					randomCard = cards[context.getLogic().random(cards.length)];
				}
				SpellCard spellCard = (SpellCard) randomCard;
				if (spellCard.canBeCast(context, player)) {
					SpellCard copyCard = spellCard.clone();
					context.getLogic().drawSetAsideCard(player.getId(), copyCard);
					
					BattlecryAction battlecry = BattlecryAction.createBattlecry(copyCard.getSpell(), copyCard.getTargetRequirement());
					battlecry.setSource(copyCard.getReference());
					if (battlecry.getTargetRequirement() != TargetSelection.NONE) {
						List<Entity> validTargets = context.getLogic().getValidTargets(player.getId(), battlecry);
						if (validTargets.isEmpty()) {
							continue;
						}
						
						battlecry.setTarget(validTargets.get(context.getLogic().random(validTargets.size())));
					}
					context.getLogic().performGameAction(player.getId(), battlecry);
				}
		}
	}

}
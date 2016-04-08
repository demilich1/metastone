package net.demilich.metastone.game.spells.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.behaviour.IBehaviour;
import net.demilich.metastone.game.behaviour.PlayRandomBehaviour;
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
	
	Logger logger = LoggerFactory.getLogger(YoggSaronSpell.class);

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(YoggSaronSpell.class);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		// This spell is crazy.
		CardCollection spellCards = CardCatalogue.query(context.getDeckFormat(), CardType.SPELL);
		// Straight up insane.
		
		// Set behavior to random. Because we're already insane.
		// This allows Discover effects and targeting to actually be random.
		IBehaviour currentBehaviour = player.getBehaviour();
		player.setBehaviour(new PlayRandomBehaviour());
		// HAHAHAHAHAHAHAHAHAHA!
		for (int i = 0; i <= (int) player.getStatistics().getLong(Statistic.SPELLS_CAST); i++) {
			Card randomCard = spellCards.getRandom();
			logger.debug("Yogg-Saron chooses to play " + randomCard.getName());
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
				GameAction battlecryAction = null;
				battlecry.setSource(copyCard.getReference());
				if (battlecry.getTargetRequirement() != TargetSelection.NONE) {
					List<Entity> validTargets = context.getLogic().getValidTargets(player.getId(), battlecry);
					if (validTargets.isEmpty()) {
						continue;
					}

					List<GameAction> battlecryActions = new ArrayList<>();
					for (Entity validTarget : validTargets) {
						GameAction targetedBattlecry = battlecry.clone();
						targetedBattlecry.setTarget(validTarget);
						battlecryActions.add(targetedBattlecry);
					}

					battlecryAction = player.getBehaviour().requestAction(context, player, battlecryActions);
				} else {
					battlecryAction = battlecry;
				}
				context.getLogic().performGameAction(player.getId(), battlecryAction);
			}
		}
		
		player.setBehaviour(currentBehaviour);
		// *ahem* Back to normal.
	}

}
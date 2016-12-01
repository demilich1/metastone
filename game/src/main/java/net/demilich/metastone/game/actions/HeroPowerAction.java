package net.demilich.metastone.game.actions;

import co.paralleluniverse.fibers.Suspendable;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class HeroPowerAction extends PlaySpellCardAction {
	private HeroPowerAction() {
		setActionType(ActionType.HERO_POWER);
	}

	public HeroPowerAction(SpellDesc spell, Card card, TargetSelection targetSelection) {
		super(spell, card, targetSelection);
		setActionType(ActionType.HERO_POWER);
	}

	@Override
	@Suspendable
	public void execute(GameContext context, int playerId) {
		play(context, playerId);
		context.getLogic().useHeroPower(playerId);
	}

	@Override
	public String getPromptText() {
		return "[Use hero power]";
	}

	@Override
	@Suspendable
	public void play(GameContext context, int playerId) {
		context.getLogic().castSpell(playerId, getSpell(), cardReference, getTargetKey(), false);
	}

}

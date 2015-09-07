package net.demilich.metastone.game.actions;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class PlaySpellCardAction extends PlayCardAction {

	protected final SpellDesc spell;
	protected final EntityReference cardReference;

	public PlaySpellCardAction(SpellDesc spell, Card card, TargetSelection targetSelection) {
		super(card.getCardReference());
		setActionType(ActionType.SPELL);
		setTargetRequirement(targetSelection);
		this.spell = spell;
		this.cardReference = card.getReference();
	}

	@Override
	public void play(GameContext context, int playerId) {
		context.getLogic().castSpell(playerId, spell, cardReference, getTargetKey(), false);
	}

}

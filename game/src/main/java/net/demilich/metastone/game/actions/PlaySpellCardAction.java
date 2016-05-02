package net.demilich.metastone.game.actions;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class PlaySpellCardAction extends PlayCardAction {

	private SpellDesc spell;
	protected final EntityReference cardReference;

	public PlaySpellCardAction(SpellDesc spell, Card card, TargetSelection targetSelection) {
		super(card.getCardReference());
		setActionType(ActionType.SPELL);
		setTargetRequirement(targetSelection);
		this.setSpell(spell);
		this.cardReference = card.getReference();
	}

	@Override
	public void play(GameContext context, int playerId) {
		context.getLogic().castSpell(playerId, spell, cardReference, getTargetKey(), false);
	}

	public SpellDesc getSpell() {
		return spell;
	}

	public void setSpell(SpellDesc spell) {
		this.spell = spell;
	}


}

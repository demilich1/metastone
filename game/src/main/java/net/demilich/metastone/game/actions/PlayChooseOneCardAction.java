package net.demilich.metastone.game.actions;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class PlayChooseOneCardAction extends PlayCardAction {

	private SpellDesc spell;
	protected final EntityReference cardReference;
	protected final String chosenCard;

	public PlayChooseOneCardAction(SpellDesc spell, Card chooseOneCard, String chosenCard, TargetSelection targetSelection) {
		super(chooseOneCard.getCardReference());
		setActionType(ActionType.SPELL);
		setTargetRequirement(targetSelection);
		this.setSpell(spell);
		this.cardReference = chooseOneCard.getReference();
		this.chosenCard = chosenCard;
	}

	@Override
	public void play(GameContext context, int playerId) {
		context.getLogic().castChooseOneSpell(playerId, spell, cardReference, getTargetKey(), chosenCard);
	}

	public SpellDesc getSpell() {
		return spell;
	}

	public void setSpell(SpellDesc spell) {
		this.spell = spell;
	}


}

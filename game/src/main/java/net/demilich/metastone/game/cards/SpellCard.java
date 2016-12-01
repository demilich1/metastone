package net.demilich.metastone.game.cards;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.actions.PlaySpellCardAction;
import net.demilich.metastone.game.cards.desc.SpellCardDesc;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.condition.Condition;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;
import net.demilich.metastone.game.targeting.TargetSelection;

public class SpellCard extends Card {

	private SpellDesc spell;
	private TargetSelection targetRequirement;
	private Condition condition;

	public SpellCard(SpellCardDesc desc) {
		super(desc);
		setTargetRequirement(desc.targetSelection);
		setSpell(desc.spell);
		if (desc.condition != null) {
			condition = desc.condition.create();
		}
	}

	public boolean canBeCast(GameContext context, Player player) {
		Player opponent = context.getOpponent(player);
		switch (targetRequirement) {
		case ENEMY_MINIONS:
			return context.getMinionCount(opponent) > 0;
		case FRIENDLY_MINIONS:
			return context.getMinionCount(player) > 0;
		case MINIONS:
			return context.getTotalMinionCount() > 0;
		default:
			break;
		}
		if (condition != null) {
			return condition.isFulfilled(context, player, null, null);
		}
		return true;
	}

	public boolean canBeCastOn(GameContext context, Player player, Entity target) {
		EntityFilter filter = spell.getEntityFilter();
		if (filter == null) {
			return true;
		}
		return filter.matches(context, player, target);
	}

	@Override
	public SpellCard clone() {
		SpellCard clone = (SpellCard) super.clone();
		if (spell == null) {
			throw new RuntimeException("Spell is NULL for SpellCard " + getName());
		}
		clone.spell = spell.clone();
		clone.condition = condition;
		return clone;
	}

	public SpellDesc getSpell() {
		return spell;
	}

	public TargetSelection getTargetRequirement() {
		return targetRequirement;
	}

	@Override
	public PlayCardAction play() {
		return new PlaySpellCardAction(getSpell(), this, getTargetRequirement());
	}

	public void setSpell(SpellDesc spell) {
		this.spell = spell;
	}

	public void setTargetRequirement(TargetSelection targetRequirement) {
		this.targetRequirement = targetRequirement;
	}

}

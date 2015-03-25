package net.demilich.metastone.game.cards;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.actions.PlaySpellCardAction;
import net.demilich.metastone.game.cards.desc.SpellCardDesc;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class SpellCard extends Card {

	private SpellDesc spell;
	private TargetSelection targetRequirement;

	protected SpellCard(String name, CardType cardType, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, cardType, rarity, classRestriction, manaCost);
	}

	public SpellCard(String name, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, CardType.SPELL, rarity, classRestriction, manaCost);
	}
	
	public SpellCard(SpellCardDesc desc) {
		super(desc);
		setTargetRequirement(desc.targetSelection);
		setSpell(desc.spell);
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
		return true;
	}

	public boolean canBeCastOn(Entity target) {
		return true;
	}

	@Override
	public SpellCard clone() {
		SpellCard clone = (SpellCard) super.clone();
		clone.spell = spell.clone();
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

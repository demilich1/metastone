package net.pferdimanzug.hearthstone.analyzer.game.cards;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;

public abstract class ChooseOneCard extends SpellCard {

	private SpellCard spellCard1;
	private SpellCard spellCard2;

	public ChooseOneCard(String name, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, rarity, classRestriction, manaCost);
		setTag(GameTag.CHOOSE_ONE);
	}

	public SpellCard getSpellCard1() {
		return spellCard1;
	}

	public void setSpellCard1(SpellCard spellCard1) {
		this.spellCard1 = spellCard1;
	}

	public SpellCard getSpellCard2() {
		return spellCard2;
	}

	public void setSpellCard2(SpellCard spellCard2) {
		this.spellCard2 = spellCard2;
	}

	private PlayCardAction playCard(final SpellCard spellCard) {
		return new PlayCardAction(spellCard) {
			{
				setTargetRequirement(spellCard.getTargetRequirement());
				setActionType(ActionType.SPELL);
			}

			@Override
			protected void play(GameContext context, int playerId) {
				Spell spell = spellCard.getSpell();
				if (!spell.hasPredefinedTarget()) {
					spell.setTarget(getTargetKey());
				}

				context.getLogic().castSpell(playerId, spell);
			}
		};
	}

	public PlayCardAction playCard1() {
		return playCard(spellCard1);
	}

	public PlayCardAction playCard2() {
		return playCard(spellCard2);
	}

	@Override
	public void setId(int id) {
		super.setId(id);
		spellCard1.setId(id);
		spellCard2.setId(id);
	}

	@Override
	public Card clone() {
		ChooseOneCard clone = (ChooseOneCard) super.clone();
		clone.spellCard1 = (SpellCard) spellCard1.clone();
		clone.spellCard2 = (SpellCard) spellCard2.clone();
		return clone;
	}

}

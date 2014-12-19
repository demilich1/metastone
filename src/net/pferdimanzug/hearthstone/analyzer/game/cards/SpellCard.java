package net.pferdimanzug.hearthstone.analyzer.game.cards;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public abstract class SpellCard extends Card {

	private SpellDesc spell;
	private TargetSelection targetRequirement;

	protected SpellCard(String name, CardType cardType, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, cardType, rarity, classRestriction, manaCost);
	}

	public SpellCard(String name, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, CardType.SPELL, rarity, classRestriction, manaCost);
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
		return new PlayCardAction(getCardReference()) {
			{
				setTargetRequirement(targetRequirement);
				setActionType(ActionType.SPELL);
			}

			@Override
			protected void play(GameContext context, int playerId) {
				getSpell().setSourceEntity(SpellCard.this.getReference());
				if (!spell.hasPredefinedTarget()) {
					spell.setTarget(getTargetKey());
				}

				context.getLogic().castSpell(playerId, spell);
			}
		};
	}

	protected void setPredefinedTarget(EntityReference entityReference) {
		spell.setTarget(entityReference);
	}

	public void setSpell(SpellDesc spell) {
		this.spell = spell;
	}

	public void setTargetRequirement(TargetSelection targetRequirement) {
		this.targetRequirement = targetRequirement;
	}

}

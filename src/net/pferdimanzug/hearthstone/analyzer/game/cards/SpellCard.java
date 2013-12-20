package net.pferdimanzug.hearthstone.analyzer.game.cards;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ISpell;

public abstract class SpellCard extends Card {

	private ISpell spell;
	private TargetSelection targetRequirement;
	private EffectHint effectHint;

	public SpellCard(String name, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, CardType.SPELL, rarity, classRestriction, manaCost);
	}

	public ISpell getSpell() {
		return spell;
	}

	public void setSpell(ISpell spell) {
		this.spell = spell;
	}

	@Override
	public PlayCardAction play() {
		return new PlayCardAction(this) {
			{
				setTargetRequirement(getTargetRequirement());
				setEffectHint(getEffectHint());
				setActionType(ActionType.SPELL);
			}

			@Override
			protected void cast(GameContext context, Player player) {
				context.getLogic().castSpell(player, spell, getTarget());
			}
		};
	}

	public TargetSelection getTargetRequirement() {
		return targetRequirement;
	}

	public void setTargetRequirement(TargetSelection targetRequirement) {
		this.targetRequirement = targetRequirement;
	}

	public EffectHint getEffectHint() {
		return effectHint;
	}

	public void setEffectHint(EffectHint effectHint) {
		this.effectHint = effectHint;
	}
	
	public boolean canBeCast(GameContext context, Player player) {
		return true;
	}
	
	public boolean canBeCastOn(Entity target) {
		return true;
	}

}

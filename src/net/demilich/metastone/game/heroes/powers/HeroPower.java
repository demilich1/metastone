package net.demilich.metastone.game.heroes.powers;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.actions.HeroPowerAction;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.targeting.TargetSelection;

public abstract class HeroPower extends SpellCard {

	private boolean used;

	public HeroPower(String name, HeroClass heroClass) {
		super(name, CardType.HERO_POWER, Rarity.FREE, heroClass, 2);
	}

	public boolean hasBeenUsed() {
		return used;
	}

	public void onWillUse(GameContext context, Player player) {

	}

	@Override
	public PlayCardAction play() {
		return new HeroPowerAction(getOwner(), getId()) {
			{
				setTargetRequirement(HeroPower.this.getTargetRequirement());
				setActionType(ActionType.HERO_POWER);
			}

			@Override
			public String getPromptText() {
				return "[Use hero power]";
			}

			@Override
			protected void play(GameContext context, int playerId) {
				getSpell().setSourceEntity(HeroPower.this.getReference());
				if (getTargetRequirement() != TargetSelection.NONE) {
					getSpell().setTarget(getTargetKey());
				}
				context.getLogic().castSpell(playerId, getSpell());
			}
		};
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

}

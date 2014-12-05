package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.HeroPowerAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellSource;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
				if (getTargetRequirement() != TargetSelection.NONE) {
					getSpell().setTarget(getTargetKey());
				}
				context.getLogic().castSpell(playerId, getSpell());
			}
		};
	}

	@Override
	public void setSpell(SpellDesc spell) {
		super.setSpell(spell);
		spell.setSource(SpellSource.HERO_POWER);
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

}

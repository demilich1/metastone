package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.HeroPowerAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class Reinforce extends HeroPower {

	public Reinforce() {
		super("Reinforce");
	}

	@Override
	public PlayCardAction play() {
		return new HeroPowerAction(this) {
			{
				setTargetRequirement(TargetRequirement.NONE);
				setEffectHint(EffectHint.POSITIVE);
			}

			@Override
			protected void cast(GameContext context, Player player) {
				MinionCard silverHandRecruitCard = new SilverHandRecruit();
				context.getLogic().summon(player, silverHandRecruitCard.summon(), null);
			}

		};

	}

	private class SilverHandRecruit extends MinionCard {

		public SilverHandRecruit() {
			super("SilverHand Recruit", Rarity.FREE, HeroClass.PALADIN, 1);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion(1, 1);
		}

	}

}

package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;

public class MirrorImage extends SpellCard {

	private class MirrorImageMinionCard extends MinionCard {

		public MirrorImageMinionCard() {
			super("Mirror Image", Rarity.FREE, HeroClass.MAGE, 0);
		}

		@Override
		public Minion summon() {
			return createMinion(0, 2, GameTag.TAUNT);
		}
		
	}
	
	public MirrorImage() {
		super("Mirror Image", Rarity.FREE, HeroClass.MAGE, 1);
		setSpell(new SummonSpell(new MirrorImageMinionCard(), new MirrorImageMinionCard()));
		setTargetRequirement(TargetSelection.NONE);
	}

}

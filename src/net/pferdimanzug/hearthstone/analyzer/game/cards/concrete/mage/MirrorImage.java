package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class MirrorImage extends SpellCard {

	public MirrorImage() {
		super("Mirror Image", Rarity.FREE, HeroClass.MAGE, 1);
		setDescription("Summon two 0/2 minions with Taunt.");
		setSpell(new SummonSpell(new MirrorImageMinionCard(), new MirrorImageMinionCard()));
		setTargetRequirement(TargetSelection.NONE);
	}
	
	@Override
	public int getTypeId() {
		return 69;
	}



	private class MirrorImageMinionCard extends MinionCard {

		public MirrorImageMinionCard() {
			super("Mirror Image", 0, 2, Rarity.FREE, HeroClass.MAGE, 0);
			setDescription("Taunt.");
		}

		@Override
		public Minion summon() {
			return createMinion(GameTag.TAUNT);
		}
		
	}
}

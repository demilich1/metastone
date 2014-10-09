package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.mage.MirrorImageToken;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class MirrorImage extends SpellCard {

	public MirrorImage() {
		super("Mirror Image", Rarity.FREE, HeroClass.MAGE, 1);
		setDescription("Summon two 0/2 minions with Taunt.");
		setSpell(SummonSpell.create(new MirrorImageToken(), new MirrorImageToken()));
		setTargetRequirement(TargetSelection.NONE);
	}
	
	@Override
	public int getTypeId() {
		return 69;
	}
}

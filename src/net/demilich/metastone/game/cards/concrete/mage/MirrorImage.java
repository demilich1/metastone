package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.cards.concrete.tokens.mage.MirrorImageToken;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

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

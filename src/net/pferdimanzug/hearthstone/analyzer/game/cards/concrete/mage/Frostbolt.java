package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetDamageSpell;

public class Frostbolt extends SpellCard {

	public Frostbolt() {
		super("Frostbolt", Rarity.FREE, HeroClass.MAGE, 2);
		setSpell(new MetaSpell(new SingleTargetDamageSpell(3), new ApplyTagSpell(GameTag.FROZEN)));
		setTargetRequirement(TargetSelection.ANY);
	}
	

}

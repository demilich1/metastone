package net.demilich.metastone.tests;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;


public class TestSpellCard extends SpellCard {

	public TestSpellCard(SpellDesc spell) {
		super("Unit Test Spell", Rarity.FREE, HeroClass.ANY, 0);
		setDescription("This spell can have various effects and should only be used in the context of unit tests.");
		setCollectible(false);
		
		setSpell(spell);
		setTargetRequirement(TargetSelection.NONE);
	}

}

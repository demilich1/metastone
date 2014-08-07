import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;


public class TestSpellCard extends SpellCard {

	public TestSpellCard(SpellDesc spell) {
		super("Unit Test Spell", Rarity.FREE, HeroClass.ANY, 0);
		setDescription("This spell can have various effects and should only be used in the context of unit tests.");
		setCollectible(false);
		
		setSpell(spell);
	}

}

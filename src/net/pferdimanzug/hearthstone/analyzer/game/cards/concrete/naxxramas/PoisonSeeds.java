package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.PoisonSeedsSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class PoisonSeeds extends SpellCard {

	/***
	 * QUESTION (regarding Sabotage):
	 * So if this was played against a Paladin who had a lone Tirion on the
	 * board, would it destroy the Ashbringer?
	 * 
	 * ANSWER:
	 * It does not. Things happen one after the other, but the one thing that
	 * never happens during a card resolution or trigger is minions dying. For
	 * example, if you have 3 Wild Pyromancers, and play a spell, they ALL get
	 * to trigger (even though they are all at 0 Health after the 2nd one
	 * triggers). We always wait for all effects to finish firing before
	 * cleaning up minions who are "dead". In this case, Sabotage kills Tirion,
	 * but he doesn't actually die until the entire effect finishes, at which
	 * point his Deathrattle will fire.
	 * 
	 * The only exception to this rule is Poison Seeds, which kills things
	 * during its resolution in order to make room for the treants. I hope
	 * that's helpful - we're always trying to find ways to make the underlying
	 * rules more clear, but this is a case where it isn't super intuitive.
	 */

	public PoisonSeeds() {
		super("Poison Seeds", Rarity.COMMON, HeroClass.DRUID, 4);
		setDescription("Destroy all minions and summon 2/2 treants to replace them");

		setSpell(PoisonSeedsSpell.create());
		setTargetRequirement(TargetSelection.NONE);
		
	}

	@Override
	public int getTypeId() {
		return 395;
	}

}

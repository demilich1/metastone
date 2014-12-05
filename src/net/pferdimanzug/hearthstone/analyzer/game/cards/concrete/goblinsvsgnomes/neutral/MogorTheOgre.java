package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.aura.AuraApplyTag;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class MogorTheOgre extends MinionCard {

	public MogorTheOgre() {
		super("Mogor the Ogre", 7, 6, Rarity.LEGENDARY, HeroClass.ANY, 6);
		setDescription("All minions have a 50% chance to attack the wrong enemy.");
	}

	@Override
	public Minion summon() {
		Minion mogorTheOgre = createMinion(GameTag.FUMBLE);
		AuraApplyTag aura = new AuraApplyTag(GameTag.FUMBLE, EntityReference.ALL_MINIONS);
		mogorTheOgre.setSpellTrigger(aura);
		return mogorTheOgre;
	}

}

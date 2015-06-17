package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class MogorTheOgre extends MinionCard {

	public MogorTheOgre() {
		super("Mogor the Ogre", 7, 6, Rarity.LEGENDARY, HeroClass.ANY, 6);
		setDescription("All minions have a 50% chance to attack the wrong enemy.");
	}

	@Override
	public int getTypeId() {
		return 536;
	}

	@Override
	public Minion summon() {
		Minion mogorTheOgre = createMinion(GameTag.FUMBLE);
		//AuraApplyTag aura = new AuraApplyTag(GameTag.FUMBLE, EntityReference.ALL_MINIONS);
		//mogorTheOgre.setSpellTrigger(aura);
		return mogorTheOgre;
	}
}

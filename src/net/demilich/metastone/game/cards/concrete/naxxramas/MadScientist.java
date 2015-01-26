package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.custom.PlayRandomSecretSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class MadScientist extends MinionCard {

	public MadScientist() {
		super("Mad Scientist", 2, 2, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Deathrattle: Put a Secret from your deck into the battlefield.");
	}

	@Override
	public int getTypeId() {
		return 392;
	}
	
	@Override
	public Minion summon() {
		Minion madScientist = createMinion();
		SpellDesc deathrattle = PlayRandomSecretSpell.create();
		deathrattle.setTarget(EntityReference.NONE);
		madScientist.addDeathrattle(deathrattle);
		return madScientist;
	}

	
}

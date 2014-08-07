package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.PlayRandomSecretSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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

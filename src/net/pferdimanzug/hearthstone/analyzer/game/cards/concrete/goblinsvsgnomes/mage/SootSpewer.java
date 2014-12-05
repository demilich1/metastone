package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class SootSpewer extends MinionCard {

	public SootSpewer() {
		super("Soot Spewer", 3, 3, Rarity.RARE, HeroClass.MAGE, 3);
		setDescription("Spell Damage +1");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 497;
	}



	@Override
	public Minion summon() {
		Minion sootSpewer = createMinion();
		sootSpewer.setTag(GameTag.SPELL_POWER, +1);
		return sootSpewer;
	}
}

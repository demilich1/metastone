package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.mage;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

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
		sootSpewer.setTag(GameTag.SPELL_DAMAGE, +1);
		return sootSpewer;
	}
}

package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class MiniMage extends MinionCard {

	public MiniMage() {
		super("Mini-Mage", 4, 1, Rarity.EPIC, HeroClass.ANY, 4);
		setDescription("Stealth. Spell Damage +1");
	}

	@Override
	public int getTypeId() {
		return 535;
	}



	@Override
	public Minion summon() {
		Minion miniMage = createMinion(GameTag.STEALTHED);
		miniMage.setTag(GameTag.SPELL_POWER, +1);
		return miniMage;
	}
}

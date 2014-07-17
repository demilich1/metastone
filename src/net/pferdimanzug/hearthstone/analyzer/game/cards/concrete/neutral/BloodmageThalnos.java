package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;

public class BloodmageThalnos extends MinionCard {

	public BloodmageThalnos() {
		super("Bloodmage Thalnos", 1, 1, Rarity.LEGENDARY, HeroClass.ANY, 2);
		setDescription("Spell Damage +1. Deathrattle: Draw a card.");
	}

	@Override
	public int getTypeId() {
		return 96;
	}



	@Override
	public Minion summon() {
		Minion bloodmageThalnos = createMinion();
		bloodmageThalnos.setTag(GameTag.SPELL_POWER, +1);
		bloodmageThalnos.addDeathrattle(new DrawCardSpell());
		return bloodmageThalnos;
	}
}

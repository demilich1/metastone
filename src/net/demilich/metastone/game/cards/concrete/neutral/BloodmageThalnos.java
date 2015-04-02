package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DrawCardSpell;

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
		bloodmageThalnos.setTag(GameTag.SPELL_DAMAGE, +1);
		bloodmageThalnos.addDeathrattle(DrawCardSpell.create());
		return bloodmageThalnos;
	}
}
